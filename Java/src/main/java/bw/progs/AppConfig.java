package bw.progs;

import java.util.ArrayList;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Using annotation to create beans for CurrencyDao, Manager and QueryRunner
 * class
 */

@Configuration
public class AppConfig {
	private SessionFactory sessionFactory;

	@Bean
	public CurrencyDao currencyDao() {
		CurrencyDao currencyDao = new CurrencyDao();
		currencyDao.setSessionFactory(sessionFactory());
		return currencyDao;
	}

	@Bean
	public QueryRunner queryRunner() {
		QueryRunner queryRunner = new QueryRunner();
		queryRunner.setProductsDao(currencyDao());

		return queryRunner;

	}

	@Bean
	public ScraperManager scraperManager() {
		ScraperManager manager = new ScraperManager();
		ArrayList<Scraper> scraperList = new ArrayList<Scraper>();
		scraperList.add(scrapeRemitly());
		scraperList.add(scrapePaysend());
		scraperList.add(scrapeLyca());

		manager.setScraperList(scraperList);

		return manager;

	}

	@Bean
	public ScrapeRemitly scrapeRemitly() {
		ScrapeRemitly scrapeRemitly = new ScrapeRemitly();
		scrapeRemitly.setCurrencyDao(currencyDao());
		scrapeRemitly.setRunner(queryRunner());
		return scrapeRemitly;
	}

	@Bean
	public ScrapePaysend scrapePaysend() {
		ScrapePaysend scrapePaysend = new ScrapePaysend();
		scrapePaysend.setCurrencyDao(currencyDao());
		scrapePaysend.setRunner(queryRunner());
		return scrapePaysend;
	}

	@Bean
	public ScrapeLyca scrapeLyca() {
		ScrapeLyca scrapeLyca = new ScrapeLyca();
		scrapeLyca.setCurrencyDao(currencyDao());
		scrapeLyca.setRunner(queryRunner());
		return scrapeLyca;
	}

	@Bean
	public SessionFactory sessionFactory() {
		if (sessionFactory != null)
			return sessionFactory;
		try {
			// Create a builder for the standard service registry
			StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

			// Load configuration from hibernate configuration file.
			// Here we are using a configuration file that specifies Java annotations.
			standardServiceRegistryBuilder.configure("resources/hibernate.cfg.xml");

			// Create the registry that will be used to build the session factory
			StandardServiceRegistry registry = standardServiceRegistryBuilder.build();
			try {
				// Create the session factory - this is the goal of the init method.
				sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			} catch (Exception e) {
				/*
				 * The registry would be destroyed by the SessionFactory, but we had trouble
				 * building the SessionFactory, so destroy it manually
				 */
				System.err.println("Session Factory build failed.");
				e.printStackTrace();
				StandardServiceRegistryBuilder.destroy(registry);
			}

			// Ouput result
			System.out.println("Session factory built.");
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("SessionFactory creation failed." + ex);
		}
		return sessionFactory;
	}

}

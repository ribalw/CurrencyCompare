package bw.progs;

import org.springframework.context.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main method to create a scraperManager class object that contain all scraping
 * and data handling methods
 */

public class Main extends Thread {
	private static ApplicationContext context;

	public static void main(String[] args) {
		
		context = new AnnotationConfigApplicationContext(AppConfig.class);
		ScraperManager scraperManager = (ScraperManager) context.getBean("scraperManager");
		scraperManager.scrapeWebsites();
		
	}
}

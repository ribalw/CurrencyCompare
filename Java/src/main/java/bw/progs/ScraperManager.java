package bw.progs;

import java.util.ArrayList;

/** Models the management of all scrapers and database functions */

public class ScraperManager extends Thread {

	
	QueryRunner queryRunner;
	CurrencyDao currencyDao;
	Scraper scraper;
	CurrencyScraper scrapeCurrency;
	public ArrayList<Scraper> scraperList;
	
	/** Get the list of scrapers */
	public ArrayList<Scraper> getScraperList() {
		return scraperList;
	}

	/** Set the list of scrapers */
	public void setScraperList(ArrayList<Scraper> scraperList) {
		this.scraperList = scraperList;
	}

	/** Start scraping for that list of scrapers */
	public void scrapeWebsites() {

		for (Scraper scraper : scraperList) {
			
			scraper.start();
		}
	}

}

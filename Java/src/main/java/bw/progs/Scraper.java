package bw.progs;

import java.util.ArrayList;
import java.util.List;

/** Super class to provide structure to all scraping classes */
public class Scraper extends Thread {

	// List of beans mapped to currency table.
	List<Currency> countryBeanList;
	List<Currency> urlBeanList;

	// Global variables used by each scraper individually.
	public String url;
	public double rate;
	boolean runThread;

	String data;
	CurrencyDao currencyDao;
	QueryRunner queryRunner;
	Broker broker;
	Comparison comparison;

	/** Empty Constructor */
	Scraper() {

	}
	/** method to stop thread from running.*/
	public void stopThread() {
		runThread = false;
	}

	/**
	 * To load all countries from database in order to search and update rate from
	 * different webs.
	 */
	public int loadCountries() {
		countryBeanList = currencyDao.findAllCurrency();
		return countryBeanList.size();
	}

	/** Method to add currencies in database scraped from third-party website */
	public void addCountries() {

		CurrencyScraper scrapeCurrency = new CurrencyScraper();
		scrapeCurrency.scrapeAllCurrencies();
		List<String> availableCountryList = scrapeCurrency.countryList;
		List<String> availableCurrencyList = scrapeCurrency.currencyList;

		for (int i = 0; i < availableCurrencyList.size(); i++) {

			queryRunner.addProduct(availableCurrencyList.get(i), availableCountryList.get(i));

		}
	}

	public List<Currency> getCountryBeanList() {
		return countryBeanList;
	}

	public void setCountryBeanList(ArrayList<Currency> countryBeanList) {
		this.countryBeanList = countryBeanList;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public boolean isRunThread() {
		return runThread;
	}

	public void setRunThread(boolean runThread) {
		this.runThread = runThread;
	}

	public CurrencyDao getCurrencyDao() {
		return currencyDao;
	}

	public void setCurrencyDao(CurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}

	public QueryRunner getRunner() {
		return queryRunner;
	}

	public void setRunner(QueryRunner queryRunner) {
		this.queryRunner = queryRunner;
	}

}
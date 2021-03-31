package bw.progs;

/**
 * QueryRunner class with methods representing each task to be done with
 * database
 */
public class QueryRunner {
	CurrencyDao currencyDao;

	/**
	 * Method to create beans of currency and send it to currencyDao to save in
	 * database
	 */

	public void addProduct(String currencyName, String country) {

		Currency currency = new Currency();
		currency.setName(currencyName);
		currency.setCountry(country);
		currencyDao.saveProduct(currency);
	}

	/**
	 * Method to create beans of currency and comparison class and send it to
	 * currencyDao to save or update in database
	 */

	public void addComparison(Currency currency, Broker broker, String url, double rate) {

		if (currency != null && broker != null) {
			Comparison comparison = new Comparison();
			comparison.setBroker(broker);
			comparison.setCurrency(currency);
			comparison.setUrl(url);
			comparison.setRate(rate);
			currencyDao.saveOrUpdateComparison(comparison);
		}

	}

	/**
	 * Method to create beans of comparison and send it to currencyDao to update in
	 * database
	 */

	public void updatePrice(Comparison comparison, double rate) {

		if (comparison != null) {
			comparison.setRate(rate);
			currencyDao.updateComparison(comparison);
		}
	}

	/**
	 * Method to create beans of comparison and send it to currencyDao to delete
	 * from database
	 */

	public void deletePrice() {
		// submitting with example data.
		Comparison comparison = currencyDao.findComparison("https://www.worldremit.com/en/Albania");
		if (comparison != null) {
			currencyDao.deleteComparison(comparison);
		}
	}

	/**
	 * Method to create beans of broker class and send it to currencyDao to save in
	 * database
	 */

	public void addBroker() {
		Broker broker = new Broker();
		broker.setName("Pagofx");
		broker.setUrl("https://pagofx.com/");
		broker.setLogo("logo/logo1.png");
		currencyDao.saveBroker(broker);

	}

	/** Method to shutDown currencyDao */

	public void shutDown() {
		// Shut down Hibernate
		currencyDao.shutDown();
	}

	public CurrencyDao getProductsDao() {
		return currencyDao;
	}

	public void setProductsDao(CurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}

}

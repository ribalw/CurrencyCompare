package bw.progs;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Represents a Remitly.com. Scrape all available currency rates from specified
 * web
 */
public class ScrapeRemitly extends Scraper {

	public void run() {
		loadCountries();
		broker = currencyDao.findBroker("Remitly");
		runThread = true;
		
		while (runThread) {
			for (Currency currency : countryBeanList) {
				scrapeRemitly(currency);
				try {
					sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}

			}
		}
	}

	/** Method to arrange scraped un-arranged data from Remitly.com */
	public double splitDataForRemitly(String notAvailable, String data) {
		if (!notAvailable.isEmpty() || data.isEmpty()) {
			rate = 0.0;
			data = "0.0";

		} else {
			data = data.replaceAll("[^\\d.]", "");

			rate = Double.parseDouble(data);// to break string and get data in double.

		}
		return rate;
	}

	void scrapeRemitly(Currency currency) {
		try {

			String countryName = currency.getCountry();

			// if country's name have space then it should be replaced by -
			if (countryName.contains(" ")) {
				countryName = countryName.replaceAll(" ", "-");
			}
			String data;
			String notAvailable = "";
			url = "https://www.remitly.com/gb/en/" + countryName + "/pricing";

			// to check if comparison already exists or not.
			comparison = currencyDao.findComparison(url);

			// Download HTML document from website
			Document doc = Jsoup.connect(url).get();
			// Get all classes of div
			Elements unAvailableCountry = doc.select("h1.rm-col.f194iq1b");

			notAvailable = unAvailableCountry.text();

			// Get all classes of div
			Elements mainClass = doc.select("div.forex-bar-product_f1ur8h45");

			Elements rateClass = mainClass.select("b.forex-bar-rate-bold_f347vxz");

			data = rateClass.text().toString();
			// TO check if this website doesn't have rate for this country.
			rate = splitDataForRemitly(notAvailable, data);
			try {
				if (comparison != null) {// if already available in database then update.
					queryRunner.updatePrice(comparison, rate);

				} else {

					queryRunner.addComparison(currency, broker, url, rate);
				}

			} catch (NullPointerException ex) {
				System.out.println(ex.getMessage());
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

	}
}

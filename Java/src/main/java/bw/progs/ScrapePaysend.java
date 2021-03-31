package bw.progs;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Represents a PaySend.com. Scrape all available currency rates from specified
 * web
 */

public class ScrapePaysend extends Scraper {
	public void run() {
		loadCountries();
		broker = currencyDao.findBroker("PaySend");
		runThread = true;

		while (runThread) {
			for (Currency currency : countryBeanList) {
				scrapePaysend(currency);
				try {
					sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}

		}
	}

	/** Method to arrange scraped unarranged data from PaySend.com */

	public double splitDataForPaysend(String data) {
		String[] allData = data.split("=");
		if (allData.length > 0)
			data = allData[1];
		else
			data = allData[0];
		data = data.replaceAll("[^\\d.]", "");
		rate = Double.parseDouble(data);
		return rate;
	}

	/** Scrapes currency data from the PaySend website */
	double scrapePaysend(Currency currency) {
		try {
			String countryName = currency.getCountry();
			countryName = countryName.toLowerCase();
			// if country's name have space then it should be replaced by -
			if (countryName.contains(" ")) {
				countryName = countryName.replaceAll(" ", "-");
			}

			url = "https://paysend.com/en-gb/send-money/from-united-kingdom-to-" + countryName
					+ "?fromCurrId=826&toCurrId=586";

			// to check if comparison already exists
			comparison = currencyDao.findComparison(url);

			// Download HTML document from website
			Document doc = Jsoup.connect(url).get();

			// Get all classes of div
			Elements rateClass = doc.select(".js_rate");
			// converting element into string
			data = rateClass.text().toString();

			// to break string and get data in double.

			rate = splitDataForPaysend(data);

			/** To add scrapped rates in database */

			try {
				if (comparison != null) {

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
		return rate;
	}
}

package bw.progs;

import java.io.IOException;
/** Represents a LycaRemit.com.
Scrape all available currency rates from specified web */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
/**
 * Scrape a Remitly.com. Scrape all available currency rates from specified
 * web
 */
public class ScrapeLyca extends Scraper {

	public void run() {
		loadCountries();
		broker = currencyDao.findBroker("LycaRemit");
		runThread = true;

		while (runThread) {
			for (Currency currency : countryBeanList) {
				scrapeLyca(currency);

				try {
					sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}

			}

		}
	}

	/** Method to arrange scraped un-arranged data from LycaRemitly.com */

	public double splitDataForLyca(String data) {

		if (data.isEmpty()) {
			rate = 0.0;
			data = "1GBP = 0.0";

		}
		// to break string and get data in double.
		String[] allData = data.split("=");
		if (allData.length > 0)
			data = allData[1];
		else// to ignore when no rates are provided.
			data = allData[0];
		data = data.replaceAll("[^\\d.]", "");
		rate = Double.parseDouble(data);
		return rate;
	}

	public void scrapeLyca(Currency currency) {
		try {

			// Name of currency that we want to scrape
			String country = currency.getCountry();
			country = country.toLowerCase();

			// if country's name have space then it should be replaced by -
			if (country.contains(" ")) {
				country = country.replaceAll(" ", "-");
			}
			url = "https://www.lycaremit.co.uk/send-money-to-" + country;

			comparison = currencyDao.findComparison(url);

			// Download HTML document from website
			Document doc = Jsoup.connect(url).get();

			// Get all classes of div
			Elements description = doc.select("h3.topHdwt");

			Elements rateClass = description.select("#ContentPlaceHolder1_lblexchange");
			data = rateClass.text().toString();

			rate = splitDataForLyca(data);

			// Running queryRunner to add or update comparison
			try {
				if (comparison != null) {// if same comparison is available then update it.

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
package bw.progs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Class to scrape all countries along with their currencies and add them in two
 * separate lists.
 */
public class CurrencyScraper extends Scraper {
	public List<String> currencyList = new ArrayList<String>();
	public List<String> countryList = new ArrayList<String>();
	QueryRunner queryRunner;

	/** Constructor */
	public CurrencyScraper() {
		try {
			scrapeAllCurrencies();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	/** Scrapes currency data for desired countries */

	public void scrapeAllCurrencies() {

		try {
			sleep(1000);

			Document docAll = Jsoup.connect(
					"https://paysend.com/en-gb?utm_source=google&utm_medium=cpc&utm_campaign=11065353024,g&utm_content=462596749881&utm_term=paysand&gclid=Cj0KCQiAhs79BRD0ARIsAC6XpaUntRVsGd4VP3AvxX99vl366YhRWikgczKnoKVuf57h-HG82gQiwPUaAo0GEALw_wcB")
					.get();

			Elements allCountries = docAll.select("div.flex-wrapper");

			for (int i = 48; i < allCountries.size(); i++) {

				Elements availableCountry = allCountries.get(i).select("span.value");
				Elements availableCurrency = allCountries.get(i).select("span.country_currency");

				String countryData = availableCountry.text().toString();
				String currencyData = availableCurrency.text().toString();

				// to ignore dual currencies.
				if (!currencyData.contains(" ")) {
					countryList.add(countryData);
					currencyList.add(currencyData);

				}

			}

		} catch (InterruptedException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

	}

}

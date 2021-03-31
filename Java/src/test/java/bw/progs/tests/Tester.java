package bw.progs.tests;


//JUnit 5 imports
import java.time.Clock;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import bw.progs.*;

@DisplayName("Tests")
class Tester {
private static ApplicationContext context  = new AnnotationConfigApplicationContext(AppConfig.class);


	ScraperManager scraperManager = (ScraperManager) context.getBean("scraperManager");
	CurrencyScraper country= new CurrencyScraper();
	ScrapeLyca scraperLyca= new ScrapeLyca();	
	ScrapePaysend scraperPaysend= new ScrapePaysend();
	ScrapeRemitly scraperRemitly= new ScrapeRemitly();
	
	
    @Test
    @DisplayName("Test1: Total Countries ")
    void  testFindCurrency() {
    	
        assertEquals(79, country.countryList.size());
    }
    @Test
    @DisplayName("Test2: Total Scrappers ")
    void  totalScrappers() {
    	
        assertEquals(3, scraperManager.scraperList.size());
    }
    
    @Test
    @DisplayName("Test3: Arrangement of data for ScrapLyca class ")
    void testLycaSplitData() {
    	double rate = scraperLyca.splitDataForLyca("1 GBP = 1.10 EUR");
        assertEquals(rate, 1.1);
    }
    @Test
    @DisplayName("Test4: Arrangement of data for ScrapPaysend class ")
    void testPaySendSplitData() {
    	double rate = scraperPaysend.splitDataForPaysend("1.00 GBP = 1.0910 EUR");
        assertEquals(rate, 1.0910);
    }
    @Test
    @DisplayName("Test5: Arrangement pf data for ScrapRemitly class ")
    void testRemitlySplitData() {
    	double rate = scraperRemitly.splitDataForRemitly("Send money to Belgium","");
        assertEquals(rate, 0.0);
    }

}

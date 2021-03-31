# CurrencyCompare
Currency Comparision web to compare currency exchange rates from different third party websites and compare them.

Access URL: https://currency-compare.s3.amazonaws.com/Code/WebApp/public/index.html

(NOTE: Database features not be available with an access with this URL because of less resources and db cannot be hosted on cloud.)

**Concept**
   A currency compare is a comparison website that compares the currency rate from three different third-party websites against GBP. Combination of various technologies has been      used to get the core functionalities done. Those technologies are summarised below.
   
**Technologies**
   •	Front End: HTML, CSS, jQuery, and Bootstrap.
   •	Web Scrapping: Maven, Spring, Hibernate, Threads, JSoup.
   •	Website server: Node.js, JavaScript, RESTful web services.
   •	Database: MySQL, HeidiSql.
   •	Testing: Junit 5, Mocha & Chai. 
   
 **Scrapping**
   Maven has been used to build, run, and test a java project. A manager class manages scrapers that scrape data from three different websites using JSoup. Hibernate query          language (HQL) has been used precisely to add, update, and retrieve data from a database, whereas spring beans represent each table from a database.  Threads have been used      precisely for each scraper individually, to delay request for each currency to the third-party website by a few seconds.
   Initially, the list of available countries from website PaySend has been scraped along with their currencies to fill the Currency table with relevant data. Then the list of      available countries has been extracted from a database and parsed to each scraper. If current currency will not be existing in a Comparison table, then new scraped rates and    URL will be added for this country. Otherwise, if the same currency is already available for specific scrapper then instead of adding, it will be updated with new rates but      the same URL. The same process will be repeated for all three scrapers and persisted until the user stops scraper voluntarily.
   A rate will be recorded as zero if the particular third-party website is not providing rates and showing a message of “Coming Soon” for any specific country for now. This        feature has been included on purpose so whenever that country will be available in future then rates for that country’s currency will be updated from zero to the current          rate. 
   
 **RESTful Web Services**
   After creating and updating data successfully next task was to extract this data from database in useful format and parse it to the front end of website. Node server has been    used to create a connection pool with MySQL database and provide localhost to run web services. RESTful services have been used to expose data internally in JSON format. The    multiple HTTP status code have been used to read response in result of GET requests against each API. Promises have been used to sequence asynchronous operations in specific    sequence.


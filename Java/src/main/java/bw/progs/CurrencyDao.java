package bw.progs;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Class that uses annotations to specify mapping between Currency, Broker,
 * Comparison object and currency,broker and comparison table in the
 * compare_currency database
 */

public class CurrencyDao {
	SessionFactory sessionFactory;

	/** Method to save single currency in currency table. */
	public void saveProduct(Currency currency) {

		// Get a new Session instance from the session factory

		Session session = sessionFactory.getCurrentSession();

		// Start transaction
		session.beginTransaction();

		session.save(currency);

		// Commit transaction to save it to database
		session.getTransaction().commit();

		// Close the session and release database connection
		session.close();
		System.out.println("Product added to database with ID: " + currency.getId());

	}

	/** Method to update values in comparison table if already exist. */

	public void saveOrUpdateComparison(Comparison comparison) {
		// Get a new Session instance from the session factory
		Session session = sessionFactory.getCurrentSession();

		
		// Start transaction
		if (!session.getTransaction().isActive())
			session.beginTransaction();

		// Add to database - will not be stored until we commit the transaction
		session.saveOrUpdate(comparison);
		System.out.println("Comparison added to database with ID: " + comparison.getId());

		// Commit transaction to save it to database
		session.getTransaction().commit();

		// Close the session and release database connection
		session.close();

	}

	/** Method to update rates in comparison table. */
	public void updateComparison(Comparison comparison) {
		// Get a new Session instance from the session factory
		Session session = sessionFactory.getCurrentSession();
		System.out.println(comparison.getBroker().name + " has updated rates to " + comparison.getCurrency().getName()
				+ " " + comparison.getRate() + " for " + comparison.getCurrency().getCountry());
		// Start transaction
		session.beginTransaction();

		// Add to database - will not be stored until we commit the transaction
		session.update(comparison);
		// Commit transaction to save it to database
		session.getTransaction().commit();

		// Close the session and release database connection
		session.close();
	}

	/** Method to save broker in broker table. */

	public void saveBroker(Broker broker) {

		// Get a new Session instance from the session factory

		Session session = sessionFactory.getCurrentSession();

		// Start transaction
		session.beginTransaction();

		session.save(broker);

		// Commit transaction to save it to database
		session.getTransaction().commit();

		// Close the session and release database connection
		session.close();
		System.out.println("Broker added to database with ID: " + broker.getId());
	}

	/** Method to find currency from currency table. */

	public Currency findCurrency(String country) {
		// Get a new Session instance from the session factory and start transaction

		Session session = sessionFactory.getCurrentSession();

		// Get class
		session.beginTransaction();

		List<Currency> productList = session.createQuery("from Currency where country='" + country + "'").getResultList();

		session.close();
		return productList.get(0);

	}

	/** Method to find all currencies from currency table. */

	public List<Currency> findAllCurrency() {
		// Get a new Session instance from the session factory and start transaction

		Session session = sessionFactory.getCurrentSession();

		// Get class
		session.beginTransaction();

		List<Currency> productList = session.createQuery("from Currency ").getResultList();
		session.close();
		return productList;

	}

	/** Method to find broker from broker table. */

	public Broker findBroker(String name) {
		// Get a new Session instance from the session factory and start transaction

		Session session = sessionFactory.getCurrentSession();
		// if(!session.isJoinedToTransaction())
		session.beginTransaction();

		// Get class

		List<Broker> productList = session.createQuery("from Broker where name='" + name + "'").getResultList();
		if (productList.size() != 1)// Should be a single class if query is successful
			return null;
		session.close();
		return productList.get(0);
	}

	/** Method to find comparison from comparison table. */

	public Comparison findComparison(String url) {
		// Get a new Session instance from the session factory and start transaction
		Session session = sessionFactory.getCurrentSession();
		if (!session.getTransaction().isActive())
			session.beginTransaction();

		// Get class
		List<Comparison> comparisonList = session.createQuery("from Comparison where url='" + url + "'").getResultList();
		if (comparisonList.size() != 1)// Should be a single class if query is successful
			return null;
		session.close();
		return comparisonList.get(0);
	}

	/** Method to delete comparison from comparison table. */

	public void deleteComparison(Comparison comparison) {
		// Get a new Session instance from the session factory and start transaction
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(comparison);

		// Commit transaction to save it to database
		session.getTransaction().commit();
		session.close();
	}

	public void shutDown() {
		sessionFactory.close();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}

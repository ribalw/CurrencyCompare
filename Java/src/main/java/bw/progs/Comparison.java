package bw.progs;

import javax.persistence.*;

/**
 * Represents a Comparison table. Java annotation is used for the mapping.
 */

@Entity
@Table(name = "comparison")
public class Comparison {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	/** To create Many to one foreign key relation between broker and comparison*/
	@ManyToOne
	@JoinColumn(name = "broker_Id", nullable = false)
	private Broker broker;
	
	/** To create one to one foreign key relation between currency and comparison*/
	@OneToOne
	@JoinColumn(name = "currency_Id", nullable = false)
	private Currency currency;

	@Column(name = "rate")
	private double rate;

	@Column(name = "url")
	private String url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Broker getBroker() {
		return broker;
	}

	public void setBroker(Broker broker) {
		this.broker = broker;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
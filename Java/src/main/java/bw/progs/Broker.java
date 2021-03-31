package bw.progs;

import javax.persistence.*;

/**
 * Represents a Broker table. Java annotation is used for the mapping.
 */

@Entity
@Table(name = "broker")

public class Broker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	String name;

	@Column(name = "url")
	String url;

	@Column(name = "logo")
	String logo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

}

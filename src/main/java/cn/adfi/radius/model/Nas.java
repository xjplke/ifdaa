package cn.adfi.radius.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the nas database table.
 * 
 */
@Entity
@Table(name="nas")
public class Nas implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3041725898125025255L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=50)
	private String community;

	@Column(length=200)
	private String description;

	@Column(nullable=false, length=128)
	private String nasname;

	private int ports;

	@Column(nullable=false, length=60)
	private String secret;

	@Column(length=64)
	private String server;

	@Column(length=32)
	private String shortname;

	@Column(length=30)
	private String type;

	public Nas() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCommunity() {
		return this.community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNasname() {
		return this.nasname;
	}

	public void setNasname(String nasname) {
		this.nasname = nasname;
	}

	public int getPorts() {
		return this.ports;
	}

	public void setPorts(int ports) {
		this.ports = ports;
	}

	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getServer() {
		return this.server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
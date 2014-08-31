package cn.adfi.radius.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_terminal")
public class Terminal {
	
	public Terminal(){}
	
	public Terminal(String mac,String type,String os,String osver,String browser){
		this.mac = mac;
		this.type = type;
		this.os = os;
		this.osver = osver;
		this.browser = browser;
	}
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true,length = 18, nullable = false)
	private String mac;
	
	@Column(length = 18, nullable = false)
	private String type;
	
	@Column(length = 32, nullable = false)
	String os;
	
	@Column(length = 32)
	String osver;
	
	@Column(length = 32)
	String browser;
	
	Date createAt;
	Date lastUpdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsver() {
		return osver;
	}

	public void setOsver(String osver) {
		this.osver = osver;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	
}

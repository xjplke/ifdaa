package cn.adfi.radius.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name="radreply",indexes = {
		@Index(columnList = "username",name="username")
})
public class Radreply implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4297613716226202997L;
	
	public Radreply(String username,String attribute,String op,String value){
		this.username = username;
		this.attribute = attribute;
		this.op = op;
		this.value = value;
	}

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 64, nullable = false)
	private String username;
	
	@Column(length = 64, nullable = false)
	private String attribute;
	
	@Column(length = 2, nullable = false)
	private String op;
	
	@Column(length = 253, nullable = false)
	private String value;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	
	/**
	 * @return the attribute
	 */
	public String getAttribute() {
		return attribute;
	}
	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	/**
	 * @return the op
	 */
	public String getOp() {
		return op;
	}
	/**
	 * @param op the op to set
	 */
	public void setOp(String op) {
		this.op = op;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
}

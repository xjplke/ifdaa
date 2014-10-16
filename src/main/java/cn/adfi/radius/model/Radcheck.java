/**
 * 
 */
package cn.adfi.radius.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author shaojunwu  --sjw
 * @date 2014-5-5
 */
@Entity
@Table(name="radcheck",indexes = {
		@Index(columnList = "username",name="username")
})
public class Radcheck implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4297613716226202997L;
	public Radcheck(String username,String attribute,String op,String value){
		this.username = username;
		this.attribute = attribute;
		this.op = op;
		this.value = value;
	}

	@Id
	@GeneratedValue
	private Long id;
	
	//TODO to set username in radcheck as key for select! not primary key, not unique!
	@Column(length = 64, nullable = false)
	private String username;
//	@ManyToOne
//	@JoinColumn(name="username",referencedColumnName="username")
//	private Raduser raduser;
	
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
	
//	/**
//	 * @return the raduser
//	 */
//	public Raduser getRaduser() {
//		return raduser;
//	}
//	/**
//	 * @param raduser the raduser to set
//	 */
//	public void setRaduser(Raduser raduser) {
//		this.raduser = raduser;
//	}
	
}

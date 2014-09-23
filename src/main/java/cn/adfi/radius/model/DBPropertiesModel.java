package cn.adfi.radius.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dbpropertiesmodel")
public class DBPropertiesModel {
	
	@Id
	@Column(name="keyprop",unique=true, nullable=false)
	private String key;
	
	@Column(nullable=true)
	private String value;
	
	public DBPropertiesModel(){
		
	}
	
	public DBPropertiesModel(String key,String value){
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}

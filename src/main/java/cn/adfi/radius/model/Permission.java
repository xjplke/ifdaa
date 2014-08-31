package cn.adfi.radius.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="tbl_permission")
public class Permission {
	public Permission(){
		
	}
	
	public Permission(String permission,String description){
		this.permission = permission;
		this.description = description;
		this.available = Boolean.TRUE;
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 32, nullable = false)
	private String permission;
	
	@Column(length = 128, nullable = false)
	private String description;
	
	@Column(nullable = false)
	private Boolean available = Boolean.FALSE;
	
//	@Cascade(value={CascadeType.SAVE_UPDATE}) 
//	@ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY) 
//	private Set<Role> roles;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean enable) {
		this.available = enable;
	} 
	
	
}

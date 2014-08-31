package cn.adfi.radius.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="tbl_role")
public class Role {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 32, nullable = false)
	private String role;
	
	@Column(length = 128, nullable = false)
	private String description;

	
	@Column(nullable = false)
	private Boolean available;
	
	@Cascade(value = CascadeType.SAVE_UPDATE)
	@ManyToMany(fetch = FetchType.LAZY) 
	@JoinTable(name="tbl_role_perminissions",joinColumns={@JoinColumn(name="roleId")},inverseJoinColumns={@JoinColumn(name="perminissionId")})
	private Set<Permission> permissions;

//	@Cascade(value={CascadeType.SAVE_UPDATE}) 
//	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY) 
//	private Set<User> users;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	
}

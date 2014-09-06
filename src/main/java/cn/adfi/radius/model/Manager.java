package cn.adfi.radius.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="tbl_user")
@DiscriminatorValue("Manager")
public class Manager extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7159052574149250770L;

}

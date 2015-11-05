package net.atos.si.ma.mt.astreinte.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="aga")
public class Jf implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7325547140798022024L;
	
	@Id
	@Temporal(TemporalType.DATE)
	private Date dateJour;

	public Date getDateJour() {
		return dateJour;
	}

	public void setDateJour(Date dateJour) {
		this.dateJour = dateJour;
	}
	
	
}
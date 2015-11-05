package net.atos.si.ma.mt.astreinte.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="aga")
public class Parameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7752044163210559131L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String keyp;
	@Column(nullable = true)
	private Integer version;
	@Column(nullable = true)
	private Long longValue;
	@Column(nullable = true)
	private String stringValue;
	@Temporal(javax.persistence.TemporalType.DATE)
	@Column(nullable = true)
	private Date dateValue;
	@Column(nullable = true)
	private Double doubleValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyp() {
		return keyp;
	}

	public void setKeyp(String keyp) {
		this.keyp = keyp;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getLongValue() {
		return longValue;
	}

	public void setLongValue(Long longValue) {
		this.longValue = longValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Date getDateValue() {
		return dateValue;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}

	public Double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}

	@Override
	public String toString() {
		return "Parameter [id=" + id + ", key=" + keyp + ", version=" + version
				+ ", longValue=" + longValue + ", stringValue=" + stringValue
				+ ", dateValue=" + dateValue + ", doubleValue=" + doubleValue
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Parameter) {
			Parameter parameter = (Parameter) obj;
			return id.equals(parameter.getId())
					&& keyp.equals(parameter.getKeyp());

		}
		return false;
	}

}

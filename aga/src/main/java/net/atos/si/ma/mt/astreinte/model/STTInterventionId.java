package net.atos.si.ma.mt.astreinte.model;

// Generated 11 nov. 2015 15:29:02 by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * SttinterventionId generated by hbm2java
 */
@Embeddable
public class STTInterventionId implements java.io.Serializable {

	private Integer utilisateurId;
	private long id;
	private Long astreinteId;
	private String ref;
	private Long equipeId;
	private String ji;
	private Date di;
	private Date fi;
	private Date intervald;
	private Date intervalf;
	private Double rate;
	private Long typeid;
	private String typelib;
	private Date sintd;
	private Date sintf;
	private Date dureesi;
	private BigDecimal hdureesi;
	private Double chargesi;

	public STTInterventionId() {
	}

	public STTInterventionId(long id) {
		this.id = id;
	}

	public STTInterventionId(Integer utilisateurId, long id, Long astreinteId,
			String ref, Long equipeId, String ji, Date di, Date fi,
			Date intervald, Date intervalf, Double rate, Long typeid,
			String typelib, Date sintd, Date sintf, Date dureesi,
			BigDecimal hdureesi, Double chargesi) {
		this.utilisateurId = utilisateurId;
		this.id = id;
		this.astreinteId = astreinteId;
		this.ref = ref;
		this.equipeId = equipeId;
		this.ji = ji;
		this.di = di;
		this.fi = fi;
		this.intervald = intervald;
		this.intervalf = intervalf;
		this.rate = rate;
		this.typeid = typeid;
		this.typelib = typelib;
		this.sintd = sintd;
		this.sintf = sintf;
		this.dureesi = dureesi;
		this.hdureesi = hdureesi;
		this.chargesi = chargesi;
	}

	@Column(name = "utilisateur_id")
	public Integer getUtilisateurId() {
		return this.utilisateurId;
	}

	public void setUtilisateurId(Integer utilisateurId) {
		this.utilisateurId = utilisateurId;
	}

	@Column(name = "id", nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "astreinte_id")
	public Long getAstreinteId() {
		return this.astreinteId;
	}

	public void setAstreinteId(Long astreinteId) {
		this.astreinteId = astreinteId;
	}

	@Column(name = "ref")
	public String getRef() {
		return this.ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	@Column(name = "equipe_id")
	public Long getEquipeId() {
		return this.equipeId;
	}

	public void setEquipeId(Long equipeId) {
		this.equipeId = equipeId;
	}

	@Column(name = "ji", length = 10)
	public String getJi() {
		return this.ji;
	}

	public void setJi(String ji) {
		this.ji = ji;
	}

	@Column(name = "di")
	public Date getDi() {
		return this.di;
	}

	public void setDi(Date di) {
		this.di = di;
	}

	@Column(name = "fi")
	public Date getFi() {
		return this.fi;
	}

	public void setFi(Date fi) {
		this.fi = fi;
	}

	@Column(name = "intervald")
	public Date getIntervald() {
		return this.intervald;
	}

	public void setIntervald(Date intervald) {
		this.intervald = intervald;
	}

	@Column(name = "intervalf")
	public Date getIntervalf() {
		return this.intervalf;
	}

	public void setIntervalf(Date intervalf) {
		this.intervalf = intervalf;
	}

	@Column(name = "rate", precision = 22, scale = 0)
	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "typeid")
	public Long getTypeid() {
		return this.typeid;
	}

	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}

	@Column(name = "typelib")
	public String getTypelib() {
		return this.typelib;
	}

	public void setTypelib(String typelib) {
		this.typelib = typelib;
	}

	@Column(name = "sintd" )
	public Date getSintd() {
		return this.sintd;
	}

	public void setSintd(Date sintd) {
		this.sintd = sintd;
	}

	@Column(name = "sintf" )
	public Date getSintf() {
		return this.sintf;
	}

	public void setSintf(Date sintf) {
		this.sintf = sintf;
	}

	@Column(name = "dureesi")
	public Date getDureesi() {
		return this.dureesi;
	}

	public void setDureesi(Date dureesi) {
		this.dureesi = dureesi;
	}

	@Column(name = "hdureesi", precision = 13, scale = 4)
	public BigDecimal getHdureesi() {
		return this.hdureesi;
	}

	public void setHdureesi(BigDecimal hdureesi) {
		this.hdureesi = hdureesi;
	}

	@Column(name = "chargesi", precision = 22, scale = 0)
	public Double getChargesi() {
		return this.chargesi;
	}

	public void setChargesi(Double chargesi) {
		this.chargesi = chargesi;
	}

	@Override
	public String toString() {
		return "STTInterventionId [utilisateurId=" + utilisateurId + ", id="
				+ id + ", astreinteId=" + astreinteId + ", ref=" + ref
				+ ", equipeId=" + equipeId + ", ji=" + ji + ", di=" + di
				+ ", fi=" + fi + ", intervald=" + intervald + ", intervalf="
				+ intervalf + ", rate=" + rate + ", typeid=" + typeid
				+ ", typelib=" + typelib + ", sintd=" + sintd + ", sintf="
				+ sintf + ", dureesi=" + dureesi + ", hdureesi=" + hdureesi
				+ ", chargesi=" + chargesi + "]";
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof STTInterventionId))
			return false;
		STTInterventionId castOther = (STTInterventionId) other;

		return ((this.getUtilisateurId() == castOther.getUtilisateurId()) || (this
				.getUtilisateurId() != null
				&& castOther.getUtilisateurId() != null && this
				.getUtilisateurId().equals(castOther.getUtilisateurId())))
				&& (this.getId() == castOther.getId())
				&& ((this.getAstreinteId() == castOther.getAstreinteId()) || (this
						.getAstreinteId() != null
						&& castOther.getAstreinteId() != null && this
						.getAstreinteId().equals(castOther.getAstreinteId())))
				&& ((this.getRef() == castOther.getRef()) || (this.getRef() != null
						&& castOther.getRef() != null && this.getRef().equals(
						castOther.getRef())))
				&& ((this.getEquipeId() == castOther.getEquipeId()) || (this
						.getEquipeId() != null
						&& castOther.getEquipeId() != null && this
						.getEquipeId().equals(castOther.getEquipeId())))
				&& ((this.getJi() == castOther.getJi()) || (this.getJi() != null
						&& castOther.getJi() != null && this.getJi().equals(
						castOther.getJi())))
				&& ((this.getDi() == castOther.getDi()) || (this.getDi() != null
						&& castOther.getDi() != null && this.getDi().equals(
						castOther.getDi())))
				&& ((this.getFi() == castOther.getFi()) || (this.getFi() != null
						&& castOther.getFi() != null && this.getFi().equals(
						castOther.getFi())))
				&& ((this.getIntervald() == castOther.getIntervald()) || (this
						.getIntervald() != null
						&& castOther.getIntervald() != null && this
						.getIntervald().equals(castOther.getIntervald())))
				&& ((this.getIntervalf() == castOther.getIntervalf()) || (this
						.getIntervalf() != null
						&& castOther.getIntervalf() != null && this
						.getIntervalf().equals(castOther.getIntervalf())))
				&& ((this.getRate() == castOther.getRate()) || (this.getRate() != null
						&& castOther.getRate() != null && this.getRate()
						.equals(castOther.getRate())))
				&& ((this.getTypeid() == castOther.getTypeid()) || (this
						.getTypeid() != null && castOther.getTypeid() != null && this
						.getTypeid().equals(castOther.getTypeid())))
				&& ((this.getTypelib() == castOther.getTypelib()) || (this
						.getTypelib() != null && castOther.getTypelib() != null && this
						.getTypelib().equals(castOther.getTypelib())))
				&& ((this.getSintd() == castOther.getSintd()) || (this
						.getSintd() != null && castOther.getSintd() != null && this
						.getSintd().equals(castOther.getSintd())))
				&& ((this.getSintf() == castOther.getSintf()) || (this
						.getSintf() != null && castOther.getSintf() != null && this
						.getSintf().equals(castOther.getSintf())))
				&& ((this.getDureesi() == castOther.getDureesi()) || (this
						.getDureesi() != null && castOther.getDureesi() != null && this
						.getDureesi().equals(castOther.getDureesi())))
				&& ((this.getHdureesi() == castOther.getHdureesi()) || (this
						.getHdureesi() != null
						&& castOther.getHdureesi() != null && this
						.getHdureesi().equals(castOther.getHdureesi())))
				&& ((this.getChargesi() == castOther.getChargesi()) || (this
						.getChargesi() != null
						&& castOther.getChargesi() != null && this
						.getChargesi().equals(castOther.getChargesi())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getUtilisateurId() == null ? 0 : this.getUtilisateurId()
						.hashCode());
		result = 37 * result + (int) this.getId();
		result = 37
				* result
				+ (getAstreinteId() == null ? 0 : this.getAstreinteId()
						.hashCode());
		result = 37 * result
				+ (getRef() == null ? 0 : this.getRef().hashCode());
		result = 37 * result
				+ (getEquipeId() == null ? 0 : this.getEquipeId().hashCode());
		result = 37 * result + (getJi() == null ? 0 : this.getJi().hashCode());
		result = 37 * result + (getDi() == null ? 0 : this.getDi().hashCode());
		result = 37 * result + (getFi() == null ? 0 : this.getFi().hashCode());
		result = 37 * result
				+ (getIntervald() == null ? 0 : this.getIntervald().hashCode());
		result = 37 * result
				+ (getIntervalf() == null ? 0 : this.getIntervalf().hashCode());
		result = 37 * result
				+ (getRate() == null ? 0 : this.getRate().hashCode());
		result = 37 * result
				+ (getTypeid() == null ? 0 : this.getTypeid().hashCode());
		result = 37 * result
				+ (getTypelib() == null ? 0 : this.getTypelib().hashCode());
		result = 37 * result
				+ (getSintd() == null ? 0 : this.getSintd().hashCode());
		result = 37 * result
				+ (getSintf() == null ? 0 : this.getSintf().hashCode());
		result = 37 * result
				+ (getDureesi() == null ? 0 : this.getDureesi().hashCode());
		result = 37 * result
				+ (getHdureesi() == null ? 0 : this.getHdureesi().hashCode());
		result = 37 * result
				+ (getChargesi() == null ? 0 : this.getChargesi().hashCode());
		return result;
	}

}

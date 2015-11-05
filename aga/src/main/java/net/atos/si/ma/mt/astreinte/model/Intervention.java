package net.atos.si.ma.mt.astreinte.model;

// Generated 26 mars 2015 17:45:30 by Hibernate Tools 4.3.1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

/**
 * Intervention generated by hbm2java
 */
/**
 * @author a134368
 *
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="aga")
public class Intervention implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	//@SequenceGenerator(name = "generator_Intervention", sequenceName = "Intervention_SEQ")
	private Long id;
	@ManyToOne
	private Utilisateur utilisateur;
	private Date dateD;
	private Date dateF;
	@Column(length=250)
	private String motif;
	@ManyToOne
	private EtatAstreinte etatAstreinte;

	@ManyToOne
	private Astreinte astreinte;

	@ManyToOne
	private TypeIntervention typeIntervention;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateD() {
		return this.dateD;
	}

	public void setDateD(Date dateD) {
		this.dateD = dateD;
	}

	public Date getDateF() {
		return this.dateF;
	}

	public void setDateF(Date dateF) {
		this.dateF = dateF;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Astreinte getAstreinte() {
		return astreinte;
	}

	public void setAstreinte(Astreinte astreinte) {
		this.astreinte = astreinte;
	}

	public TypeIntervention getTypeIntervention() {
		return typeIntervention;
	}

	public void setTypeIntervention(TypeIntervention typeIntervention) {
		this.typeIntervention = typeIntervention;
	}

	public EtatAstreinte getEtatAstreinte() {
		return etatAstreinte;
	}

	public void setEtatAstreinte(EtatAstreinte etatAstreinte) {
		this.etatAstreinte = etatAstreinte;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	@Transient
	public double getCharge() {

		double k = 1;
		if (typeIntervention != null
				&& typeIntervention.getDoubleValue() != null)
			k = typeIntervention.getDoubleValue().doubleValue();
		return getDuration() * k / 8;
	}

	@Transient
	public double getDuration() {
		DateTime d = new DateTime(dateD);
		DateTime f = new DateTime(dateF);
		double m = Minutes.minutesBetween(d, f).getMinutes();

		return m / 60;
	}

	@Override
	public String toString() {
		return "Intervention [id=" + id + ", utilisateur=" + utilisateur
				+ ", dateD=" + dateD + ", dateF=" + dateF + ", etatAstreinte="
				+ etatAstreinte + ", astreinte=" + astreinte
				+ ", typeIntervention=" + typeIntervention + "]";
	}
	
	
}

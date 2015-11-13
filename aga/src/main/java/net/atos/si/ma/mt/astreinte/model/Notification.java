package net.atos.si.ma.mt.astreinte.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "aga")
public class Notification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8147956082140230535L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.TABLE, generator = "notification_gen")
	//@TableGenerator(name = "notification_gen", table = "GENERATOR_TABLE", pkColumnName = "key", valueColumnName = "next", pkColumnValue = "course", allocationSize = 30)
	private Long id;

	private String destinataires;

	private String subject;
	@Column(length = 500)
	private String content;
	private Integer occurence = 0;

	@ManyToOne
	private Astreinte astreinte;

	@ManyToOne
	private Intervention intervention;

	@ManyToOne
	private EtatAstreinte etatAstreinte;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDestinataires() {
		return destinataires;
	}

	public void setDestinataires(String destinataires) {
		this.destinataires = destinataires;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getOccurence() {
		return occurence;
	}

	public void setOccurence(Integer occurence) {
		this.occurence = occurence;
	}

	public Astreinte getAstreinte() {
		return astreinte;
	}

	public void setAstreinte(Astreinte astreinte) {
		this.astreinte = astreinte;
	}

	public EtatAstreinte getEtatAstreinte() {
		return etatAstreinte;
	}

	public void setEtatAstreinte(EtatAstreinte etatAstreinte) {
		this.etatAstreinte = etatAstreinte;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", occurence=" + occurence
				+ ", astreinte=" + astreinte + ", intervention=" + intervention
				+ ", etatAstreinte=" + etatAstreinte.getKeyp() + "]";
	}

	public void incrimentOccurence() {
		this.occurence += 1;
	}

}

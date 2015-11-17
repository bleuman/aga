package net.atos.si.ma.mt.astreinte.model;

// Generated 11 nov. 2015 15:29:02 by Hibernate Tools 4.3.1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@NamedQuery(name = "sttintervention-by-user-astreinte", query = "from STTIntervention where id.utilisateurId=:user and id.astreinteId=:astreinte order by id.ji,id.sintd")
@Entity
@Table(name = "sttintervention")
public class STTIntervention implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5039244281336636745L;
	private STTInterventionId id;

	public STTIntervention() {
	}

	public STTIntervention(STTInterventionId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "utilisateurId", column = @Column(name = "utilisateur_id")),
			@AttributeOverride(name = "id", column = @Column(name = "id", nullable = false)),
			@AttributeOverride(name = "astreinteId", column = @Column(name = "astreinte_id")),
			@AttributeOverride(name = "ref", column = @Column(name = "ref")),
			@AttributeOverride(name = "equipeId", column = @Column(name = "equipe_id")),
			@AttributeOverride(name = "ji", column = @Column(name = "ji", length = 10)),
			@AttributeOverride(name = "di", column = @Column(name = "di", length = 8)),
			@AttributeOverride(name = "fi", column = @Column(name = "fi", length = 8)),
			@AttributeOverride(name = "intervald", column = @Column(name = "intervald", length = 8)),
			@AttributeOverride(name = "intervalf", column = @Column(name = "intervalf", length = 8)),
			@AttributeOverride(name = "rate", column = @Column(name = "rate", precision = 22, scale = 0)),
			@AttributeOverride(name = "typeid", column = @Column(name = "typeid")),
			@AttributeOverride(name = "typelib", column = @Column(name = "typelib")),
			@AttributeOverride(name = "sintd", column = @Column(name = "sintd", length = 8)),
			@AttributeOverride(name = "sintf", column = @Column(name = "sintf", length = 8)),
			@AttributeOverride(name = "dureesi", column = @Column(name = "dureesi", length = 8)),
			@AttributeOverride(name = "hdureesi", column = @Column(name = "hdureesi", precision = 13, scale = 4)),
			@AttributeOverride(name = "chargesi", column = @Column(name = "chargesi", precision = 22, scale = 0)) })
	public STTInterventionId getId() {
		return this.id;
	}

	public void setId(STTInterventionId id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "STTIntervention [id=" + id + "]";
	}

}

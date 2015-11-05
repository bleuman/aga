package net.atos.si.ma.mt.astreinte.model;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class TypeIntervention extends Parameter {

	/**
	 * 
	 */
	public static final long serialVersionUID = 1135614144776057986L;
	public static final String DIMANCHE = "Dimanche";
	public static final String JOUR = "Jour";
	public static final String JOUR_FERIE = "Jour ferié";
	public static final String SAMEDI = "Samedi";
	public static final String SOIR = "Soir";
	public static final String TELEPHONE = "Téléphone";

	@Transient
	public Double getSst() {
		return getLongValue().doubleValue() / 100;
	}

}
package net.atos.si.ma.mt.astreinte.model;

import javax.persistence.Entity;

@Entity
public class EtatAstreinte extends Parameter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1135614144776057986L;
	public static final String EN_COURS = "En cours";
	public static final String TERMINEE = "Terminée";
	public static final String VALIDEE = "Validée";
	public static final String A_VALIDER = "A Valider";
	public static final String REJETEE = "Rejetée";
	public static final long ID_EN_COURS = 9L;
	public static final long ID_TERMINEE = 10L;
	public static final long ID_VALIDEE = 11L;
	public static final long ID_A_VALIDER = 12L;
	public static final long ID_REJETEE = 13L;
}
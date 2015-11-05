package net.atos.si.ma.mt.config.web;

public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7396880029610795750L;
	private String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ValidationException(String message) {
		super();
		this.message = message;
	}
	
	

}

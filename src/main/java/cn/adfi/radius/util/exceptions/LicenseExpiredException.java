package cn.adfi.radius.util.exceptions;

public class LicenseExpiredException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3836510793528202481L;
	
	public LicenseExpiredException(String message){
		super(message);
	}
}

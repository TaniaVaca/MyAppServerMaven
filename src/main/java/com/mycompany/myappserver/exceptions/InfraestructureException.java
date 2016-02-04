package com.mycompany.myappserver.exceptions;

import java.io.Serializable;
/**
 *
 * @author Alexandra
 */
@SuppressWarnings("serial")
public class InfraestructureException extends CoreException implements Serializable{

	/**
	 * Instantiates a new habitat exception.
	 *
	 * @param msg the msg
	 */
	public InfraestructureException(String msg) {
		super(msg);
	}

	/**
	 * Instantiates a new habitat exception.
	 *
	 * @param t the t
	 */
	public InfraestructureException(Throwable t) {
		super(t);
	}

	/**
	 * Instantiates a new habitat exception.
	 *
	 * @param msg the msg
	 * @param t the t
	 */
	public InfraestructureException(String msg, Throwable t) {
		super(msg, t);
	}
	
}

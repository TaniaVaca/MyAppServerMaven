package com.mycompany.myappserver.exceptions;

import java.io.Serializable;

/**
 *
 * @author Alexandra
 */
@SuppressWarnings("serial")
public class JPAException extends Exception implements Serializable{
    
    /** Creates a new instance. */
    public JPAException(String msg) {
        super(msg);
    }
    
    public JPAException(String msg, Throwable t) {
        super(msg, t);
    }
}
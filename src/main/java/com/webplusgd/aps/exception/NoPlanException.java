package com.webplusgd.aps.exception;

/**
 * @author Rollingegg
 * @create_time 11/11/2020 3:18 PM
 */
public class NoPlanException extends Exception{
    private static final long serialVersionUID = -994962710559017255L;

    public NoPlanException(String message) {
        super(message);
    }
}

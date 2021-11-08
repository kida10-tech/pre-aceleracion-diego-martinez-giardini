package com.alkemy.disney.disney.exception;

@SuppressWarnings("serial")
public class ParamNotFound extends RuntimeException{
    public ParamNotFound(String msg) {
        super(msg);
    }
}


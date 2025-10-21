package com.crio.rent_read.exception;


public class EmailExistException extends RuntimeException {

    public EmailExistException(String msg){
        super(msg);
    }
}

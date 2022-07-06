package com.tw.vapsi.biblioteca.exception;

public class ServiceException extends Exception{

    public ServiceException(String message){
        super(message);
    }

    public ServiceException(){
        super();
    }


}

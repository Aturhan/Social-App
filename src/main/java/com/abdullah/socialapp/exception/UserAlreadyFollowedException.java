package com.abdullah.socialapp.exception;

public class UserAlreadyFollowedException extends Exception{
    public UserAlreadyFollowedException(String message){
        super(message);
    }
}

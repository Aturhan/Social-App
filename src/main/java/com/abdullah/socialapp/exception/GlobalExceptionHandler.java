package com.abdullah.socialapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> UserNotFoundEx(UserNotFoundException e){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("Message: ",e.getMessage());
        errorMap.put("Status: ", HttpStatus.NOT_FOUND.toString());
        return errorMap;
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> PostNotFoundEx(PostNotFoundException e){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("Message: ",e.getMessage());
        errorMap.put("Status: ", HttpStatus.NOT_FOUND.toString());
        return errorMap;
    }

    @ExceptionHandler(UserAlreadyFollowedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> UserAlreadyFollowedEx(UserAlreadyFollowedException e){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("Message: ",e.getMessage());
        errorMap.put("Status: ", HttpStatus.BAD_REQUEST.toString());
        return errorMap;
    }

}

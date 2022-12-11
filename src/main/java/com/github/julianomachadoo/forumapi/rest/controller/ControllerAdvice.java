package com.github.julianomachadoo.forumapi.rest.controller;

import com.github.julianomachadoo.forumapi.exceptions.DadosNaoEncontrados;
import com.github.julianomachadoo.forumapi.rest.ApiErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(DadosNaoEncontrados.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleDataNotFoundException (DadosNaoEncontrados ex){
        return new ApiErrors(ex.getMessage());
    }

}

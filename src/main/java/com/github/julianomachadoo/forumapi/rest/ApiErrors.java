package com.github.julianomachadoo.forumapi.rest;

import java.util.Collections;
import java.util.List;

public class ApiErrors {

    private final List<String> errors;

    public ApiErrors(String mensagemErro) {
        this.errors = Collections.singletonList(mensagemErro);
    }

    public List<String> getErrors() {
        return errors;
    }
}

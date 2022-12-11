package com.github.julianomachadoo.forumapi.rest.dto;

public class TokenDTO {

    private final String token;
    private final String tipo;

    public TokenDTO(String token, String tipo) {
        this.token = token;
        this.tipo = tipo;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
}

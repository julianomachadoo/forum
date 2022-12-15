package com.github.julianomachadoo.forumapi.rest.dto;

public class ErroDeFormularioDTO {

    private final String campo;
    private final String erro;

    public ErroDeFormularioDTO(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}

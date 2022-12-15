package com.github.julianomachadoo.forumapi.rest.form;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class AtualizacaoRespostaForm {

    @NotBlank @Length(min = 10)
    private String mensagem;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}

package com.github.julianomachadoo.forumapi.rest.dto;

import com.github.julianomachadoo.forumapi.modelo.Resposta;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class RespostaDTO {

    private final Long id;
    private final String mensagem;
    private final LocalDateTime dataCriacao;
    private final String nomeAutor;

    public RespostaDTO (Resposta resposta) {
        this.id = resposta.getId();
        this.mensagem = resposta.getMensagem();
        this.dataCriacao = resposta.getDataCriacao().truncatedTo(ChronoUnit.SECONDS);
        this.nomeAutor = resposta.getAutor().getNome();
    }

    public Long getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }
}

package com.github.julianomachadoo.forumapi.controller.dto;

import com.github.julianomachadoo.forumapi.modelo.Topico;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Objects;

public class TopicoDTO {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;

    public TopicoDTO(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicoDTO topicoDTO = (TopicoDTO) o;
        return Objects.equals(id, topicoDTO.id) && Objects.equals(titulo, topicoDTO.titulo) && Objects.equals(mensagem, topicoDTO.mensagem) && Objects.equals(dataCriacao, topicoDTO.dataCriacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, mensagem, dataCriacao);
    }

    public static Page<TopicoDTO> converter(Page<Topico> topicos) {
        return topicos.map(TopicoDTO::new);
    }
}

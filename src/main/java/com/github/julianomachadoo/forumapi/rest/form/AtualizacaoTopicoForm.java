package com.github.julianomachadoo.forumapi.rest.form;

import com.github.julianomachadoo.forumapi.modelo.Topico;
import com.github.julianomachadoo.forumapi.repository.TopicoRepository;

public class AtualizacaoTopicoForm {

    private String titulo;

    private String mensagem;

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

    public Topico atualizar(Long id, TopicoRepository topicoRepository) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);
        return topico;
    }
}

package com.github.julianomachadoo.forumapi.util.builder;

import com.github.julianomachadoo.forumapi.modelo.Curso;
import com.github.julianomachadoo.forumapi.modelo.Topico;
import com.github.julianomachadoo.forumapi.modelo.Usuario;

public class TopicoBuilder {
    private String titulo;
    private String mensagem;
    private Curso curso;
    private Usuario usuario;

    public TopicoBuilder comTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public TopicoBuilder comMensagem(String mensagem) {
        this.mensagem = mensagem;
        return this;
    }

    public TopicoBuilder comCurso(Curso curso) {
        this.curso = curso;
        return this;
    }

    public TopicoBuilder comUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public Topico build() {
        return new Topico(titulo, mensagem, usuario, curso);
    }
}

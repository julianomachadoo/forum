package com.github.julianomachadoo.forumapi.util.builder;

import com.github.julianomachadoo.forumapi.modelo.Curso;

public class CursoBuilder extends Curso {

    private String nome;

    private String categoria;

    public CursoBuilder comNome(String nome)  {
        this.nome = nome;
        return this;
    }

    public CursoBuilder comCategoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public Curso build() {
        return new Curso(nome, categoria);
    }

}

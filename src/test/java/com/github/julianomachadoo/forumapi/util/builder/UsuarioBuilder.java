package com.github.julianomachadoo.forumapi.util.builder;

import com.github.julianomachadoo.forumapi.modelo.Usuario;

public class UsuarioBuilder {

    private String nome;
    private String email;
    private String senha;

    public UsuarioBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public UsuarioBuilder comEmail(String email) {
        this.email = email;
        return this;
    }

    public UsuarioBuilder comSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public Usuario build() {
        return new Usuario(nome, email, senha);
    }
}

package com.github.julianomachadoo.forumapi.util.builder;

import com.github.julianomachadoo.forumapi.modelo.Resposta;
import com.github.julianomachadoo.forumapi.modelo.Topico;
import com.github.julianomachadoo.forumapi.modelo.Usuario;

public class RespostaBuilder {
    private String mensagem;
    private Topico topico;
    private Usuario usuario;

    public RespostaBuilder comMensagem(String mensagem) {
        this.mensagem = mensagem;
        return this;
    }

    public RespostaBuilder comTopico(Topico topico) {
        this.topico = topico;
        return this;
    }

    public RespostaBuilder comUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public Resposta build() {
        return new Resposta(mensagem, topico, usuario);
    }
}

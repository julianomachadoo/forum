package com.github.julianomachadoo.forumapi.controller.form;

import com.github.julianomachadoo.forumapi.modelo.Curso;
import com.github.julianomachadoo.forumapi.modelo.Topico;
import com.github.julianomachadoo.forumapi.repository.CursoRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class TopicoForm {


    @NotBlank @Length(min = 5)
    private String titulo;
    @NotBlank @Length(min = 10)
    private String mensagem;
    @NotBlank
    private String nomeCurso;

    private String emailUsuario;


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

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public Topico converter(CursoRepository cursoRepository) {
        Optional<Curso> curso = cursoRepository.findByNome(this.nomeCurso);
        return new Topico(this.titulo, this.mensagem, curso.get());
    }
}

package com.github.julianomachadoo.forumapi.service;

import com.github.julianomachadoo.forumapi.controller.dto.DetalhesDoTopicoDTO;
import com.github.julianomachadoo.forumapi.controller.dto.TopicoDTO;
import com.github.julianomachadoo.forumapi.controller.form.TopicoForm;
import com.github.julianomachadoo.forumapi.exceptions.DadosInvalidosException;
import com.github.julianomachadoo.forumapi.modelo.Curso;
import com.github.julianomachadoo.forumapi.modelo.Topico;
import com.github.julianomachadoo.forumapi.modelo.Usuario;
import com.github.julianomachadoo.forumapi.repository.CursoRepository;
import com.github.julianomachadoo.forumapi.repository.TopicoRepository;
import com.github.julianomachadoo.forumapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private TopicoRepository topicoRepository;

    public TopicoDTO cadastrarAmbienteDev(TopicoForm topicoForm) {
        Topico topico = new Topico(
                topicoForm.getTitulo(),
                topicoForm.getMensagem(),
                obterUsuario(topicoForm),
                obterCurso(topicoForm)
        );

        Topico topicoSalvo = topicoRepository.save(topico);
        return new TopicoDTO(topicoSalvo);
    }

    public DetalhesDoTopicoDTO detalharTopico(Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isEmpty()) throw new DadosInvalidosException("Topico nao encontrado");
        return new DetalhesDoTopicoDTO(optionalTopico.get());
    }

    private Curso obterCurso(TopicoForm topicoForm) {
        String nomeCurso = topicoForm.getNomeCurso();
        Optional<Curso> optionalCurso = cursoRepository.findByNome(nomeCurso);
        if (optionalCurso.isEmpty()) throw new DadosInvalidosException("Curso nao encontrado");
        return optionalCurso.get();
    }

    private Usuario obterUsuario(TopicoForm topicoForm) {
        String emailUsuario = topicoForm.getEmailUsuario();
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(emailUsuario);

        if (optionalUsuario.isEmpty()) throw new DadosInvalidosException("Usuario nao encontrado");
        return  optionalUsuario.get();
    }
}

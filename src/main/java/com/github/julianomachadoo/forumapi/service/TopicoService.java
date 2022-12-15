package com.github.julianomachadoo.forumapi.service;

import com.github.julianomachadoo.forumapi.exceptions.DadosInvalidosException;
import com.github.julianomachadoo.forumapi.rest.dto.DetalhesDoTopicoDTO;
import com.github.julianomachadoo.forumapi.rest.dto.TopicoDTO;
import com.github.julianomachadoo.forumapi.rest.form.AtualizacaoTopicoForm;
import com.github.julianomachadoo.forumapi.rest.form.TopicoForm;
import com.github.julianomachadoo.forumapi.exceptions.DadosNaoEncontradosException;
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


    public TopicoDTO cadastrar(TopicoForm topicoForm, Usuario usuario) {
        Topico topico = new Topico(
                topicoForm.getTitulo(),
                topicoForm.getMensagem(),
                usuario,
                obterCurso(topicoForm)
        );

        Topico topicoSalvo = topicoRepository.save(topico);
        return new TopicoDTO(topicoSalvo);
    }

    public DetalhesDoTopicoDTO detalharTopico(Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isEmpty()) throw new DadosNaoEncontradosException("Topico nao encontrado");
        return new DetalhesDoTopicoDTO(optionalTopico.get());
    }

    private Curso obterCurso(TopicoForm topicoForm) {
        String nomeCurso = topicoForm.getNomeCurso();
        Optional<Curso> optionalCurso = cursoRepository.findByNome(nomeCurso);
        if (optionalCurso.isEmpty()) throw new DadosNaoEncontradosException("Curso nao encontrado");
        return optionalCurso.get();
    }

    private Usuario obterUsuario(TopicoForm topicoForm) {
        String emailUsuario = topicoForm.getEmailUsuario();
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(emailUsuario);

        if (optionalUsuario.isEmpty()) throw new DadosNaoEncontradosException("Usuario nao encontrado");
        return optionalUsuario.get();
    }

    public TopicoDTO atualizar(AtualizacaoTopicoForm form, Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isEmpty()) throw new DadosNaoEncontradosException("Topico não encontrado");

        if (form.getTitulo() != null && !form.getTitulo().isEmpty() && !form.getTitulo().isBlank()) {
            if (form.getTitulo().length() < 5) throw new DadosInvalidosException("Titulo muito pequeno");
            topico.get().setTitulo(form.getTitulo());
        }

        if (form.getMensagem() != null && !form.getMensagem().isEmpty() && !form.getMensagem().isBlank()) {
            if (form.getMensagem().length() < 10) throw new DadosInvalidosException("Mensagem muito pequena");
            topico.get().setMensagem(form.getMensagem());
        }
        return new TopicoDTO(topico.get());
    }
}

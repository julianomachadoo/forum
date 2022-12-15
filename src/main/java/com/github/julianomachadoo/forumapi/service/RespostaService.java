package com.github.julianomachadoo.forumapi.service;

import com.github.julianomachadoo.forumapi.exceptions.DadosNaoEncontradosException;
import com.github.julianomachadoo.forumapi.modelo.Resposta;
import com.github.julianomachadoo.forumapi.modelo.Topico;
import com.github.julianomachadoo.forumapi.modelo.Usuario;
import com.github.julianomachadoo.forumapi.repository.RespostaRepository;
import com.github.julianomachadoo.forumapi.repository.TopicoRepository;
import com.github.julianomachadoo.forumapi.rest.dto.RespostaDTO;
import com.github.julianomachadoo.forumapi.rest.form.RespostaForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class RespostaService {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    ServiceUtils serviceUtils;

    public List<Resposta> listarPorTopicoId(Long id) {
        return respostaRepository.findByTopicoId(id);
    }

    public RespostaDTO cadastrarAmbienteDev(RespostaForm respostaForm) {
        Optional<Topico> topico = topicoRepository.findById(respostaForm.getTopicoId());

        if (topico.isEmpty()) throw new DadosNaoEncontradosException("Topico não encontrado");

        Resposta resposta = new Resposta(
                respostaForm.getMensagem(),
                topico.get(),
                serviceUtils.obterUsuario(respostaForm.getEmailUsuario()));
        Resposta respostaSalva = respostaRepository.save(resposta);
        return new RespostaDTO(respostaSalva);
    }

    public RespostaDTO cadastrar(@Valid RespostaForm respostaForm, Usuario usuario) {
        Optional<Topico> topico = topicoRepository.findById(respostaForm.getTopicoId());

        if (topico.isEmpty()) throw new DadosNaoEncontradosException("Topico não encontrado");

        Resposta resposta = new Resposta(
                respostaForm.getMensagem(),
                topico.get(),
                usuario);
        Resposta respostaSalva = respostaRepository.save(resposta);

        return new RespostaDTO(respostaSalva);
    }
}

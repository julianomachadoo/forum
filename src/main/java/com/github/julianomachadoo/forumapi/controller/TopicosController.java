package com.github.julianomachadoo.forumapi.controller;

import com.github.julianomachadoo.forumapi.controller.dto.DetalhesDoTopicoDTO;
import com.github.julianomachadoo.forumapi.controller.dto.TopicoDTO;
import com.github.julianomachadoo.forumapi.controller.form.AtualizacaoTopicoForm;
import com.github.julianomachadoo.forumapi.controller.form.TopicoForm;
import com.github.julianomachadoo.forumapi.modelo.Topico;
import com.github.julianomachadoo.forumapi.repository.CursoRepository;
import com.github.julianomachadoo.forumapi.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("topicos")
public class TopicosController {

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoDTO> lista(String nomeCurso) {

        List<Topico> topicos;
        if (nomeCurso == null) {
            topicos = topicoRepository.findAll();
        } else {
            topicos = topicoRepository.findByCurso_Nome(nomeCurso);
        }
        return TopicoDTO.converter(topicos);
    }

    @GetMapping("/{id}")
    public DetalhesDoTopicoDTO detalhar(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        return new DetalhesDoTopicoDTO(topico);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
        Topico topico = topicoForm.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDTO(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
        Topico topico = form.atualizar(id, topicoRepository);
        return ResponseEntity.ok(new TopicoDTO(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        topicoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
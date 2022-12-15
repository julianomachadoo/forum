package com.github.julianomachadoo.forumapi.rest.controller;

import com.github.julianomachadoo.forumapi.config.security.TokenService;
import com.github.julianomachadoo.forumapi.modelo.Topico;
import com.github.julianomachadoo.forumapi.modelo.Usuario;
import com.github.julianomachadoo.forumapi.repository.CursoRepository;
import com.github.julianomachadoo.forumapi.repository.UsuarioRepository;
import com.github.julianomachadoo.forumapi.rest.dto.DetalhesDoTopicoDTO;
import com.github.julianomachadoo.forumapi.rest.dto.TopicoDTO;
import com.github.julianomachadoo.forumapi.rest.form.AtualizacaoTopicoForm;
import com.github.julianomachadoo.forumapi.rest.form.TopicoForm;
import com.github.julianomachadoo.forumapi.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("topicos")
public class TopicosController {

    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    TokenService tokenService;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoService topicoService;

    public TopicosController(CursoRepository cursoRepository, UsuarioRepository usuarioRepository) {
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @Cacheable(value = "listaDeTopicos")
    public Page<TopicoDTO> lista(@RequestParam(required = false) String nomeCurso, @PageableDefault Pageable paginacao) {
        Page<Topico> topicos;
        if (nomeCurso == null) {
            topicos = topicoService.listarTodos(paginacao);
        } else {
            topicos = topicoService.listarPorCursoNome(nomeCurso, paginacao);
        }
        return TopicoDTO.converter(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTopicoDTO> detalhar(@PathVariable Long id) {
        DetalhesDoTopicoDTO detalhesDoTopicoDTO = topicoService.detalharTopico(id);
        return ResponseEntity.ok().body(detalhesDoTopicoDTO);
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {

        String authorization = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getHeader("Authorization");
        if (authorization == null) {
            TopicoDTO topicoDTO = topicoService.cadastrarAmbienteDev(topicoForm);
            URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topicoDTO.getId()).toUri();
            return ResponseEntity.created(uri).body(topicoDTO);
        }

        Usuario user = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TopicoDTO topicoDTO = topicoService.cadastrar(topicoForm, user);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topicoDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(topicoDTO);
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody AtualizacaoTopicoForm
            form) {
        TopicoDTO topicoAtualizado = topicoService.atualizar(form, id);
        return ResponseEntity.ok(topicoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<?> remover(@PathVariable Long id) {
        topicoService.removerTopico(id);
        return ResponseEntity.ok().build();
    }
}
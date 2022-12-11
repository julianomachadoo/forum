//package com.github.julianomachadoo.forumapi.controller;
//
//import com.github.julianomachadoo.forumapi.controller.dto.RespostaDTO;
//import com.github.julianomachadoo.forumapi.controller.dto.TopicoDTO;
//import com.github.julianomachadoo.forumapi.controller.form.AtualizacaoTopicoForm;
//import com.github.julianomachadoo.forumapi.controller.form.TopicoForm;
//import com.github.julianomachadoo.forumapi.modelo.Topico;
//import com.github.julianomachadoo.forumapi.repository.RespostaRepository;
//import com.github.julianomachadoo.forumapi.repository.TopicoRepository;
//import com.github.julianomachadoo.forumapi.repository.UsuarioRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import javax.validation.Valid;
//import java.net.URI;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("resposta")
//public class RespostaController {
//
//    @Autowired
//    TopicoRepository topicoRepository;
//
//    @Autowired
//    RespostaRepository respostaRepository;
//
//    @Autowired
//    UsuarioRepository usuarioRepository;
//
//    public RespostaController(TopicoRepository topicoRepository, RespostaRepository respostaRepository, UsuarioRepository usuarioRepository) {
//        this.topicoRepository = topicoRepository;
//        this.respostaRepository = respostaRepository;
//        this.usuarioRepository = usuarioRepository;
//    }
//
//    @PostMapping
//    @Transactional
//    @CacheEvict(value = "listaDeTopicos", allEntries = true)
//    public ResponseEntity<RespostaDTO> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
//        Resposta resposta topico = topicoForm.converter(cursoRepository);
//        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
//        return ResponseEntity.created(uri).body(new TopicoDTO(topico));
//    }
//
//    @PutMapping("/{id}")
//    @Transactional
//    @CacheEvict(value = "listaDeTopicos", allEntries = true)
//    public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
//        Optional<Topico> optional = topicoRepository.findById(id);
//        if (optional.isPresent()) {
//            Topico topico = form.atualizar(id, topicoRepository);
//            return ResponseEntity.ok(new TopicoDTO(topico));
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping("/{id}")
//    @Transactional
//    @CacheEvict(value = "listaDeTopicos", allEntries = true)
//    public ResponseEntity<?> remover(@PathVariable Long id) {
//        Optional<Topico> optional = topicoRepository.findById(id);
//        if (optional.isPresent()) {
//            topicoRepository.deleteById(id);
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.notFound().build();
//    }
//}
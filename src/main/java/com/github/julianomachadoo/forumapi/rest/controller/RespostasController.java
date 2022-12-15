package com.github.julianomachadoo.forumapi.rest.controller;

import com.github.julianomachadoo.forumapi.modelo.Resposta;
import com.github.julianomachadoo.forumapi.modelo.Usuario;
import com.github.julianomachadoo.forumapi.rest.dto.RespostaDTO;
import com.github.julianomachadoo.forumapi.rest.form.AtualizacaoRespostaForm;
import com.github.julianomachadoo.forumapi.rest.form.RespostaForm;
import com.github.julianomachadoo.forumapi.service.RespostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("respostas")
public class RespostasController {

    @Autowired
    private RespostaService respostaService;

    @GetMapping("/{topicoId}")
    public List<RespostaDTO> listarRespostasPorTopicoId(@PathVariable Long topicoId) {
        List<Resposta> respostaPage = respostaService.listarPorTopicoId(topicoId);
        return respostaPage.stream().map(RespostaDTO::new).toList();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<RespostaDTO> cadastrarResposta(
            @RequestBody @Valid RespostaForm respostaForm, UriComponentsBuilder uriBuilder) {

        String authorization = ((ServletRequestAttributes) Objects.
                requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getHeader("Authorization");
        if (authorization == null) {
            RespostaDTO respostaDTO = respostaService.cadastrarAmbienteDev(respostaForm);
            URI uri = uriBuilder.path("/respostas/{id}").buildAndExpand(respostaDTO.getId()).toUri();
            return ResponseEntity.created(uri).body(respostaDTO);
        }

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RespostaDTO respostaDTO = respostaService.cadastrar(respostaForm, usuario);
        URI uri = uriBuilder.path("/respostas/{id}").buildAndExpand(respostaDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(respostaDTO);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RespostaDTO> atualizar
            (@PathVariable Long id, @RequestBody @Valid AtualizacaoRespostaForm atualizacaoRespostaForm) {
        RespostaDTO respostaDTO = respostaService.atualizar(id, atualizacaoRespostaForm);
        return ResponseEntity.ok(respostaDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        respostaService.remover(id);
        return ResponseEntity.ok().build();
    }
}

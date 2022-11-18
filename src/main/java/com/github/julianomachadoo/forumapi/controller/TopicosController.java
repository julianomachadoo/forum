package com.github.julianomachadoo.forumapi.controller;

import com.github.julianomachadoo.forumapi.controller.dto.TopicoDTO;
import com.github.julianomachadoo.forumapi.modelo.Curso;
import com.github.julianomachadoo.forumapi.modelo.Topico;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("topicos")
public class TopicosController {

    @GetMapping
    public List<TopicoDTO> lista() {
        Topico topico = new Topico(
                "Duvida 1",
                "Mensagem da duvida",
                new Curso("Spring", "Programação")
        );
        return TopicoDTO.converter(Arrays.asList(topico, topico, topico));
    }
}

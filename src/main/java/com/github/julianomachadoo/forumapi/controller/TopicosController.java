package com.github.julianomachadoo.forumapi.controller;

import com.github.julianomachadoo.forumapi.controller.dto.TopicoDTO;
import com.github.julianomachadoo.forumapi.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.github.julianomachadoo.forumapi.controller.dto.TopicoDTO.converter;

@RestController
@RequestMapping("topicos")
public class TopicosController {

    @Autowired
    TopicoRepository repository;

    @GetMapping
    public List<TopicoDTO> lista() {

        return converter(repository.findAll());
    }
}

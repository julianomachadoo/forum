package com.github.julianomachadoo.forumapi;

import com.github.julianomachadoo.forumapi.modelo.*;
import com.github.julianomachadoo.forumapi.repository.CursoRepository;
import com.github.julianomachadoo.forumapi.repository.PerfilRepository;
import com.github.julianomachadoo.forumapi.repository.TopicoRepository;
import com.github.julianomachadoo.forumapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
class ForumApiApplicationTests implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    public void run(String... args) {
        Perfil perfilModerador = new Perfil("ROLE_MODERADOR");
        Perfil perfilAluno = new Perfil("ROLE_ALUNO");

        Usuario usuarioModerador = new Usuario("Moderador", "moderador@email.com", "$2a$12$q6wE1wvNzQOpzvwGzkvofeHFhvqiTkq6bGB7MfGz/OjRpOd9xj28S");
        Usuario usuarioAluno = new Usuario("Aluno", "aluno@email.com", "$2a$12$q6wE1wvNzQOpzvwGzkvofeHFhvqiTkq6bGB7MfGz/OjRpOd9xj28S");

        usuarioModerador.getPerfis().add(perfilModerador);
        usuarioAluno.getPerfis().add(perfilAluno);

        perfilRepository.saveAll(Arrays.asList(perfilModerador, perfilAluno));
        usuarioRepository.saveAll(Arrays.asList(usuarioModerador, usuarioAluno));
    }
}



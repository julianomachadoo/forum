package com.github.julianomachadoo.forumapi.repository;

import com.github.julianomachadoo.forumapi.modelo.Curso;
import com.github.julianomachadoo.forumapi.util.builder.CursoBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CursoRepositoryTest {
    @Autowired
    private CursoRepository repository;
    @Autowired
    private TestEntityManager em;

    private static final String CURSO_NOME_SPRING = "Spring Boot";
    private static final String CURSO_CATEGORIA_SPRING = "Programação";

    @Test
    public void deveriaCarregarUmCursoAoBuscarPeloSeuNome() {
        Curso spring = new CursoBuilder()
                .comNome(CURSO_NOME_SPRING)
                .comCategoria(CURSO_CATEGORIA_SPRING)
                .build();
        em.persist(spring);

        Curso curso = repository.findByNome(CURSO_NOME_SPRING);
        assertNotNull(curso);
        assertEquals(CURSO_NOME_SPRING, curso.getNome());
        assertEquals(CURSO_CATEGORIA_SPRING, curso.getCategoria());
    }

    @Test
    public void naoDeveriaCarregarUmCursoCujoNomeNaoEstejaCadastrado() {
        String nomeCurso = "JPA";
        Curso curso = repository.findByNome(nomeCurso);
        assertNull(curso);
    }
}
package com.github.julianomachadoo.forumapi.repository;

import com.github.julianomachadoo.forumapi.modelo.Curso;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CursoRepositoryTest {
    @Autowired
    private CursoRepository repository;
    @Autowired
    private TestEntityManager em;

    private static final String NOVA_CATEGORIA = "nova categoria";
    private static final String NOME_ATUALIZADO = "Nome atualizado";
    private static final String CURSO_NOME_SPRING = "Spring Boot";
    private static final String CURSO_CATEGORIA_SPRING = "Programação";
    private static final String CURSO_NOME_HTML5 = "HTML 5";
    private static final String CURSO_CATEGORIA_HTML5 = "Front end";
    private final Curso spring = new Curso(CURSO_NOME_SPRING, CURSO_CATEGORIA_SPRING);
    private final Curso html5 = new Curso(CURSO_NOME_HTML5, CURSO_CATEGORIA_HTML5);

    @Test
    public void deveriaSalvarUmCurso() {
        Curso curso = repository.save(spring);

        assertNotNull(curso);
        assertEquals(CURSO_NOME_SPRING, curso.getNome());
        assertEquals(CURSO_CATEGORIA_SPRING, curso.getCategoria());
    }

    @Test
    public void deveriaSalvarVariosCursos() {
        List<Curso> cursos = repository.saveAll(Arrays.asList(spring, html5));

        assertNotNull(cursos);
        assertEquals(2, cursos.size());
        assertEquals(CURSO_NOME_SPRING, cursos.get(0).getNome());
        assertEquals(CURSO_CATEGORIA_SPRING, cursos.get(0).getCategoria());
        assertEquals(CURSO_NOME_HTML5, cursos.get(1).getNome());
        assertEquals(CURSO_CATEGORIA_HTML5, cursos.get(1).getCategoria());
    }

    @Test
    public void deveriaCarregarTodosOsCursos() {
        Curso springPersist = em.persist(this.spring);
        Curso html5Persis = em.persist(this.html5);

        List<Curso> cursos = repository.findAll();

        assertNotNull(cursos);
        assertEquals(2, cursos.size());
        assertEquals(springPersist.getId(), cursos.get(0).getId());
        assertEquals(CURSO_NOME_SPRING, cursos.get(0).getNome());
        assertEquals(CURSO_CATEGORIA_SPRING, cursos.get(0).getCategoria());
        assertEquals(html5Persis.getId(), cursos.get(1).getId());
        assertEquals(CURSO_NOME_HTML5, cursos.get(1).getNome());
        assertEquals(CURSO_CATEGORIA_HTML5, cursos.get(1).getCategoria());
    }

    @Test
    public void deveriaCarregarVazioCasoNaoHajaNenhumCadastrado() {
        List<Curso> cursos = repository.findAll();

        assertThat(cursos).isEmpty();
    }

    @Test
    public void deveriaEncontrarUmCursoPeloSeuId() {
        Curso springPersist = em.persist(spring);

        Optional<Curso> curso = repository.findById(springPersist.getId());

        if (curso.isEmpty()) fail();

        assertEquals(CURSO_NOME_SPRING, curso.get().getNome());
        assertEquals(CURSO_CATEGORIA_SPRING, curso.get().getCategoria());
    }

    @Test
    public void naoDeveriaEncontrarPeloIdUmCursoNaoCadastrado() {
        Optional<Curso> curso = repository.findById(1L);

        if (curso.isPresent()) fail();

        assertThat(curso).isEmpty();
    }

     @Test
    public void deveriaCarregarUmCursoAoBuscarPeloSeuNome() {
        String nomeCurso = "HTML 6";

        Curso html6 = new Curso(nomeCurso, "Programacao");
        em.persist(html6);

        Curso curso = repository.findByNome(nomeCurso);
        assertNotNull(curso);
        assertEquals(nomeCurso, curso.getNome());
    }

    @Test
    public void naoDeveriaCarregarUmCursoCujoNomeNaoEstejaCadastrado() {
        String nomeCurso = "JPA";
        Curso curso = repository.findByNome(nomeCurso);
        assertNull(curso);
    }

    @Test
    public void deveriaAtualizarOCadastroDeUmCurso() {
        em.persist(spring);

        Curso cursoAtualizado = new Curso(NOME_ATUALIZADO, NOVA_CATEGORIA);

        Curso curso = repository.findByNome(CURSO_NOME_SPRING);
        curso.setNome(cursoAtualizado.getNome());
        curso.setCategoria(cursoAtualizado.getCategoria());
        repository.save(curso);

        Curso checagem = repository.getReferenceById(curso.getId());
        assertNotNull(checagem);
        assertEquals(NOME_ATUALIZADO, checagem.getNome());
        assertEquals(NOVA_CATEGORIA, checagem.getCategoria());
    }

    @Test
    public void deveriaDeletarUmCursoPorId() {
        Curso curso = em.persist(spring);
        em.persist(html5);

        repository.deleteById(curso.getId());
        List<Curso> cursos = repository.findAll();

        assertNotNull(cursos);
        assertEquals(1, cursos.size());
    }

    @Test
    public void naoDeveriaDeletarUmCursoNaoCadastrado() {
        try {
            repository.deleteById(1L);
        } catch (Exception e) {
            assertEquals(EmptyResultDataAccessException.class, e.getClass());
        }
    }
}
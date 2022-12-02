package com.github.julianomachadoo.forumapi.repository;

import com.github.julianomachadoo.forumapi.modelo.Curso;
import com.github.julianomachadoo.forumapi.modelo.Topico;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TopicoRepositoryTest {

    public static final String CURSO_NOME = "Spring Boot";
    public static final String CURSO_CATEGORIA = "Programação";
    public static final String T1_TITULO = "Dúvida";
    public static final String T1_MENSAGEM = "Erro ao criar projeto";
    public static final String T2_TITULO = "Dúvida2";
    public static final String T3_TITULO = "Dúvida3";
    public static final String T2_MENSAGEM = "Projeto não compila";
    public static final String T3_MENSAGEM = "Projeto não compila de jeito nenhum";
    @Autowired
    private TopicoRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void deveriaCarregarUmaPaginacaoDeTopicosPorCursoNome() {

        Curso spring = new Curso(CURSO_NOME, CURSO_CATEGORIA);
        Topico t1 = new Topico(T1_TITULO, T1_MENSAGEM, spring);
        Topico t2 = new Topico(T2_TITULO, T2_MENSAGEM, spring);
        Topico t3 = new Topico(T3_TITULO, T3_MENSAGEM, spring);
        LocalDateTime data = LocalDateTime.now();
        PageRequest pageRequest = PageRequest.of(0, 10);

        em.persist(spring);
        em.persist(t1);
        em.persist(t2);
        em.persist(t3);

        Page<Topico> topicos = repository.findByCurso_Nome(CURSO_NOME, pageRequest);
        List<Topico> listaTopicos = topicos.stream().toList();

        assertNotNull(topicos);
        assertEquals(3, listaTopicos.size());
        assertEquals(CURSO_NOME, listaTopicos.get(0).getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, listaTopicos.get(0).getCurso().getCategoria());
        assertEquals(T1_TITULO, listaTopicos.get(0).getTitulo());
        assertEquals(T1_MENSAGEM, listaTopicos.get(0).getMensagem());
        assertEquals(data, listaTopicos.get(0).getDataCriacao());

        assertEquals(CURSO_NOME, listaTopicos.get(1).getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, listaTopicos.get(1).getCurso().getCategoria());
        assertEquals(T2_TITULO, listaTopicos.get(1).getTitulo());
        assertEquals(T2_MENSAGEM, listaTopicos.get(1).getMensagem());
        assertEquals(data, listaTopicos.get(1).getDataCriacao());

        assertEquals(CURSO_NOME, listaTopicos.get(2).getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, listaTopicos.get(2).getCurso().getCategoria());
        assertEquals(T3_TITULO, listaTopicos.get(2).getTitulo());
        assertEquals(T3_MENSAGEM, listaTopicos.get(2).getMensagem());
        assertEquals(data, listaTopicos.get(2).getDataCriacao());
    }

    @Test
    public void naoDeveriaCarregarTopicosNaoCadastrados() {

        Curso spring = new Curso(CURSO_NOME, CURSO_CATEGORIA);
        PageRequest pageRequest = PageRequest.of(0, 10);

        em.persist(spring);

        Page<Topico> topicos = repository.findByCurso_Nome(CURSO_NOME, pageRequest);

        assertEquals(0, topicos.stream().toList().size());
    }
}
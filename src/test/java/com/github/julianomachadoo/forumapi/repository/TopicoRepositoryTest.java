package com.github.julianomachadoo.forumapi.repository;

import com.github.julianomachadoo.forumapi.modelo.Curso;
import com.github.julianomachadoo.forumapi.modelo.Topico;
import com.github.julianomachadoo.forumapi.modelo.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TopicoRepositoryTest {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TestEntityManager em;

    private static final String MENSAGEM_ATUALIZADA = "Mensagem atualizada";
    private static final String TITULO_ATUALIZADO = "Titulo atualizado";
    private static final String CURSO_NOME = "Spring Boot";
    private static final String CURSO_CATEGORIA = "Programação";
    private static final String TITULO1 = "Dúvida";
    private static final String TITULO2 = "Dúvida2";
    private static final String MENSAGEM1 = "Erro ao criar projeto";
    private static final String MENSAGEM2 = "Projeto não compila";
    private static final String NOME_DE_USUARIO_DE_EXEMPLO = "Nome de usuario de exemplo";
    private static final String EMAIL_DE_EXEMPLO = "exemplo1@email.com";
    private static final String SENHA_DE_EXEMPLO_1 = "senhaDeExemplo1";
    private final Usuario usuario1 = new Usuario(NOME_DE_USUARIO_DE_EXEMPLO, EMAIL_DE_EXEMPLO, SENHA_DE_EXEMPLO_1);
    private final Curso spring = new Curso(CURSO_NOME, CURSO_CATEGORIA);
    private final Topico t1 = new Topico(TITULO1, MENSAGEM1, usuario1, spring);
    private final Topico t2 = new Topico(TITULO2, MENSAGEM2, usuario1, spring);

    @BeforeEach
    public void inicializar() {
        em.persist(usuario1);
        em.persist(spring);
    }

    @Test
    public void deveriaSalvarUmTopico() {
        Topico topico = topicoRepository.save(t1);

        assertNotNull(topico);
        assertEquals(TITULO1, topico.getTitulo());
        assertEquals(MENSAGEM1, topico.getMensagem());
        assertEquals(CURSO_NOME, topico.getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, topico.getCurso().getCategoria());
        assertEquals(usuario1, topico.getAutor());
    }

    @Test
    public void deveriaSalvarVariosTopico() {
        List<Topico> topicos = topicoRepository.saveAll(Arrays.asList(t1, t2));

        assertNotNull(topicos);
        assertEquals(TITULO1, topicos.get(0).getTitulo());
        assertEquals(MENSAGEM1, topicos.get(0).getMensagem());
        assertEquals(CURSO_NOME, topicos.get(0).getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, topicos.get(0).getCurso().getCategoria());
        assertEquals(usuario1, topicos.get(0).getAutor());

        assertEquals(TITULO2, topicos.get(1).getTitulo());
        assertEquals(MENSAGEM2, topicos.get(1).getMensagem());
        assertEquals(CURSO_NOME, topicos.get(1).getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, topicos.get(1).getCurso().getCategoria());
        assertEquals(usuario1, topicos.get(1).getAutor());
    }

    @Test
    public void deveriaCarregarUmaPaginacaoDeTopicos() {
        em.persist(t1);
        em.persist(t2);

        Page<Topico> topicos = topicoRepository.findAll(PageRequest.of(0, 10));

        assertNotNull(topicos);
        assertEquals(TITULO1, topicos.get().toList().get(0).getTitulo());
        assertEquals(MENSAGEM1, topicos.get().toList().get(0).getMensagem());
        assertEquals(CURSO_NOME, topicos.get().toList().get(0).getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, topicos.get().toList().get(0).getCurso().getCategoria());
        assertEquals(usuario1, topicos.get().toList().get(0).getAutor());

        assertEquals(TITULO2, topicos.get().toList().get(1).getTitulo());
        assertEquals(MENSAGEM2, topicos.get().toList().get(1).getMensagem());
        assertEquals(CURSO_NOME, topicos.get().toList().get(1).getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, topicos.get().toList().get(1).getCurso().getCategoria());
        assertEquals(usuario1, topicos.get().toList().get(1).getAutor());
    }

    @Test
    public void naoDeveriaCarregarTopicosSeNaoTiverNenhumCadastrado() {
        Page<Topico> topicos = topicoRepository.findAll(PageRequest.of(0, 10));

        assertEquals(0, topicos.stream().toList().size());
        assertThat(topicos).isEmpty();
    }

    @Test
    public void deveriaCarregarUmaPaginacaoDeTopicosPorCursoNome() {
        em.persist(t1);
        em.persist(t2);

        Page<Topico> topicos = topicoRepository.findByCurso_Nome(CURSO_NOME, PageRequest.of(0, 10));
        List<Topico> listaTopicos = topicos.stream().toList();

        assertNotNull(topicos);
        assertEquals(2, listaTopicos.size());
        assertEquals(CURSO_NOME, listaTopicos.get(0).getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, listaTopicos.get(0).getCurso().getCategoria());
        assertEquals(TITULO1, listaTopicos.get(0).getTitulo());
        assertEquals(MENSAGEM1, listaTopicos.get(0).getMensagem());
        assertEquals(usuario1, listaTopicos.get(0).getAutor());

        assertEquals(CURSO_NOME, listaTopicos.get(1).getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, listaTopicos.get(1).getCurso().getCategoria());
        assertEquals(TITULO2, listaTopicos.get(1).getTitulo());
        assertEquals(MENSAGEM2, listaTopicos.get(1).getMensagem());
        assertEquals(usuario1, listaTopicos.get(1).getAutor());
    }

    @Test
    public void naoDeveriaCarregarPorCursoNomeTopicosNaoCadastrados() {
        em.persist(spring);

        Page<Topico> topicos = topicoRepository.findByCurso_Nome(CURSO_NOME, PageRequest.of(0, 10));

        assertEquals(0, topicos.stream().toList().size());
        assertThat(topicos).isEmpty();
    }

    @Test
    public void deveriaEncontrarTopicoPorId() {
        Topico topico1 = em.persist(t1);

        Optional<Topico> topico = topicoRepository.findById(topico1.getId());

        if (topico.isEmpty()) Assertions.fail();

        assertNotNull(topico.get());
        assertEquals(TITULO1, topico.get().getTitulo());
        assertEquals(MENSAGEM1, topico.get().getMensagem());
        assertEquals(TITULO1, topico.get().getTitulo());
        assertEquals(CURSO_NOME, topico.get().getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, topico.get().getCurso().getCategoria());
        assertEquals(usuario1, topico.get().getAutor());
    }

    @Test
    public void naoDeveriaEncontrarPorIdTopicoNaoCadastrado() {
        Optional<Topico> topico = topicoRepository.findById(1L);

        assertFalse(topico.isPresent());
        assertThat(topico).isEmpty();
    }

    @Test
    public void deveriaEncontrarReferenciaPorId() {
        Topico topico1 = em.persist(t1);

        Topico topico = topicoRepository.getReferenceById(topico1.getId());

        assertNotNull(topico);
        assertEquals(TITULO1, topico.getTitulo());
        assertEquals(MENSAGEM1, topico.getMensagem());
        assertEquals(CURSO_NOME, topico.getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, topico.getCurso().getCategoria());
        assertEquals(usuario1, topico.getAutor());
    }

    @Test
    public void naoDeveriaEncontrarReferenciaPorIdCasoNaoExistaTopicoCadastrado() {
        try {
            topicoRepository.getReferenceById(1L);
        } catch (Exception e) {
            assertEquals(EntityNotFoundException.class, e.getClass());
        }
    }

    @Test
    public void deveriaAtualizarUmTopico() {
        Topico topicoPersist = em.persist(t1);

        Topico topicoAtualizado = new Topico(TITULO_ATUALIZADO, MENSAGEM_ATUALIZADA,usuario1 , spring);

        Topico topico = topicoRepository.getReferenceById(topicoPersist.getId());
        topico.setTitulo(topicoAtualizado.getTitulo());
        topico.setMensagem(topicoAtualizado.getMensagem());
        topicoRepository.save(topico);

        Optional<Topico> checagem = topicoRepository.findById(topico.getId());

        if (checagem.isEmpty()) fail();

        assertNotNull(checagem.get());
        assertEquals(TITULO_ATUALIZADO, checagem.get().getTitulo());
        assertEquals(MENSAGEM_ATUALIZADA, checagem.get().getMensagem());
        assertEquals(topicoAtualizado.getAutor(), checagem.get().getAutor());
    }

    @Test
    public void deveriaDeletarUmTopicoPorId() {
        Topico persist = em.persist(t1);
        em.persist(t2);

        topicoRepository.deleteById(persist.getId());
        List<Topico> topicos = topicoRepository.findAll();

        assertNotNull(topicos);
        assertEquals(1, topicos.size());
    }

    @Test
    public void naoDeveriaDeletarUmTopicoPorId() {
        try {
            topicoRepository.deleteById(1L);
        } catch (Exception e) {
            assertEquals(EmptyResultDataAccessException.class, e.getClass());
        }
    }
}
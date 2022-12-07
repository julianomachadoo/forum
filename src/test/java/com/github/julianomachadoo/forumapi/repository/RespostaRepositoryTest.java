package com.github.julianomachadoo.forumapi.repository;

import com.github.julianomachadoo.forumapi.modelo.Curso;
import com.github.julianomachadoo.forumapi.modelo.Resposta;
import com.github.julianomachadoo.forumapi.modelo.Topico;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class RespostaRepositoryTest {

    // TODO: adicionar o autor a implementação;
    @Autowired
    private RespostaRepository respostaRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private TestEntityManager em;

    private static final String CURSO_NOME = "Spring";
    private static final String CURSO_CATEGORIA = "Programacao";
    private static final String TITULO_DE_TOPICO_DE_EXEMPLO = "Titulo de tópico de exemplo";
    private static final String MENSAGEM_DE_TOPICO_DE_EXEMPLO = "Mensagem exemplo";
    private static final String MENSAGEM_DE_RESPOSTA_DE_EXEMPLO = "Mensagem de resposta de exemplo";
    private static final String MENSAGEM_DE_RESPOSTA_DE_EXEMPLO_2 = "Mensagem de resposta de exemplo 2";
    public static final String MENSAGEM_DE_RESPOSTA_ATUALIZADA = "Mensagem de resposta atualizada";
    private final Curso spring = new Curso(CURSO_NOME, CURSO_CATEGORIA);
    private final Topico topico = new Topico(TITULO_DE_TOPICO_DE_EXEMPLO, MENSAGEM_DE_TOPICO_DE_EXEMPLO, spring);
    private final Resposta resposta = new Resposta(MENSAGEM_DE_RESPOSTA_DE_EXEMPLO, topico);
    private final Resposta resposta2 = new Resposta(MENSAGEM_DE_RESPOSTA_DE_EXEMPLO_2, topico);

    @Test
    public void deveriaCadastrarUmaNovaResposta() {
        topico.getRespostas().add(resposta);

        em.persist(spring);
        em.persist(topico);
        LocalDateTime data = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        Resposta respostaTeste = respostaRepository.save(resposta);

        assertNotNull(respostaTeste);
        assertEquals(respostaTeste, respostaTeste.getTopico().getRespostas().get(0));
        assertEquals(1, respostaTeste.getTopico().getRespostas().size());
        assertEquals(data, respostaTeste.getDataCriacao().truncatedTo(ChronoUnit.MINUTES));
        assertEquals(spring, respostaTeste.getTopico().getCurso());
        assertEquals(CURSO_NOME, respostaTeste.getTopico().getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, respostaTeste.getTopico().getCurso().getCategoria());
        assertEquals(topico, respostaTeste.getTopico());
        assertEquals(TITULO_DE_TOPICO_DE_EXEMPLO, respostaTeste.getTopico().getTitulo());
        assertEquals(MENSAGEM_DE_TOPICO_DE_EXEMPLO, respostaTeste.getTopico().getMensagem());
        assertEquals(MENSAGEM_DE_RESPOSTA_DE_EXEMPLO, respostaTeste.getMensagem());
    }

    @Test
    public void deveriaDevolverListaDeRespostasPorTopicoId() {
        topico.getRespostas().addAll(Arrays.asList(resposta, resposta2));
        em.persist(spring);
        Topico topicoPersist = em.persist(topico);
        em.persist(resposta);
        em.persist(resposta2);

        LocalDateTime data = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<Resposta> respostas = respostaRepository.findByTopicoId(topicoPersist.getId());

        assertNotNull(respostas);
        assertEquals(data, respostas.get(0).getDataCriacao().truncatedTo(ChronoUnit.MINUTES));
        assertEquals(2, respostas.size());

        assertEquals(respostas, respostas.get(0).getTopico().getRespostas());
        assertEquals(spring, respostas.get(0).getTopico().getCurso());
        assertEquals(CURSO_NOME, respostas.get(0).getTopico().getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, respostas.get(0).getTopico().getCurso().getCategoria());
        assertEquals(topico, respostas.get(0).getTopico());
        assertEquals(TITULO_DE_TOPICO_DE_EXEMPLO, respostas.get(0).getTopico().getTitulo());
        assertEquals(MENSAGEM_DE_TOPICO_DE_EXEMPLO, respostas.get(0).getTopico().getMensagem());
        assertEquals(MENSAGEM_DE_RESPOSTA_DE_EXEMPLO, respostas.get(0).getMensagem());

        assertEquals(respostas, respostas.get(1).getTopico().getRespostas());
        assertEquals(data, respostas.get(1).getDataCriacao().truncatedTo(ChronoUnit.MINUTES));
        assertEquals(spring, respostas.get(1).getTopico().getCurso());
        assertEquals(CURSO_NOME, respostas.get(1).getTopico().getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, respostas.get(1).getTopico().getCurso().getCategoria());
        assertEquals(topico, respostas.get(1).getTopico());
        assertEquals(TITULO_DE_TOPICO_DE_EXEMPLO, respostas.get(1).getTopico().getTitulo());
        assertEquals(MENSAGEM_DE_TOPICO_DE_EXEMPLO, respostas.get(1).getTopico().getMensagem());
        assertEquals(MENSAGEM_DE_RESPOSTA_DE_EXEMPLO_2, respostas.get(1).getMensagem());
    }

    @Test
    public void respostasPorTopicoIdNaoDeveriaCarregarRespostasNaoCadastradas() {
        em.persist(spring);
        em.persist(topico);

        List<Resposta> respostas = respostaRepository.findByTopicoId(1L);
        assertThat(respostas).isEmpty();
    }

    @Test
    public void deveriaAtualizarUmaResposta() {
        topico.getRespostas().add(resposta);

        em.persist(spring);
        em.persist(topico);
        Resposta respostaPersist = em.persist(resposta);
        Resposta respostaAtualizar = new Resposta(MENSAGEM_DE_RESPOSTA_ATUALIZADA, topico);
        Resposta respostaParaAtualizar = respostaRepository.getReferenceById(respostaPersist.getId());
        respostaParaAtualizar.setMensagem(respostaAtualizar.getMensagem());
        Resposta respostaAtualizada = respostaRepository.save(respostaParaAtualizar);

        assertNotNull(respostaAtualizada);
        assertEquals(MENSAGEM_DE_RESPOSTA_ATUALIZADA, respostaAtualizada.getMensagem());
    }

    @Test
    public void deveriaApagarUmaResposta() {
        em.persist(spring);
        em.persist(topico);
        Resposta respostaPersist = em.persist(resposta);
        em.persist(resposta2);

        respostaRepository.deleteById(respostaPersist.getId());
        List<Resposta> respostas = respostaRepository.findAll();

        assertNotNull(respostas);
        assertEquals(1, respostas.size());
    }

    @Test
    public void naoDeveriaApagarUmaRespostaNaoCadastrada() {
        try {
            respostaRepository.deleteById(1L);
        } catch (Exception e) {
            assertEquals(EmptyResultDataAccessException.class, e.getClass());
        }
    }


}
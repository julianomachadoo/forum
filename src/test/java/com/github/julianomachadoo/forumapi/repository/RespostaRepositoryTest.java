package com.github.julianomachadoo.forumapi.repository;

import com.github.julianomachadoo.forumapi.modelo.Curso;
import com.github.julianomachadoo.forumapi.modelo.Resposta;
import com.github.julianomachadoo.forumapi.modelo.Topico;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class RespostaRepositoryTest {

    public static final String TOPICO_EXEMPLO = "Topico exemplo";
    public static final String HTML_5 = "HTML 5";
    public static final String PROGRAMACAO = "Programacao";
    public static final String MENSAGEM_EXEMPLO = "Mensagem exemplo";
    public static final String MENSAGEM_DE_RESPOSTA_DE_EXEMPLO = "Mensagem de resposta de exemplo";
    public static final String MENSAGEM_DE_RESPOSTA_DE_EXEMPLO_2 = "Mensagem de resposta de exemplo 2";

    @Mock
    private RespostaRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void deveriaDevolverListaDeRespostasPorTopicoId() {
        Curso html5 = new Curso(HTML_5, PROGRAMACAO);
        Topico topico = new Topico(TOPICO_EXEMPLO, MENSAGEM_EXEMPLO, html5);
        Resposta resp1 = new Resposta(MENSAGEM_DE_RESPOSTA_DE_EXEMPLO, topico);
        Resposta resp2 = new Resposta(MENSAGEM_DE_RESPOSTA_DE_EXEMPLO_2, topico);
        LocalDateTime data = LocalDateTime.now();

        em.persist(html5);
        em.persist(topico);
        em.persist(resp1);
        em.persist(resp2);

        List<Resposta> respostas = repository.findByTopicoId(2L);
        assertNotNull(respostas);
        assertEquals(2, respostas.size());
        assertEquals(HTML_5, respostas.get(0).getTopico().getCurso().getNome());
        assertEquals(PROGRAMACAO, respostas.get(0).getTopico().getCurso().getCategoria());
        assertEquals(TOPICO_EXEMPLO, respostas.get(0).getTopico().getTitulo());
        assertEquals(MENSAGEM_EXEMPLO, respostas.get(0).getTopico().getMensagem());
        assertEquals(MENSAGEM_DE_RESPOSTA_DE_EXEMPLO, respostas.get(0).getMensagem());
        assertEquals(data, respostas.get(0).getDataCriacao());

        assertEquals(HTML_5, respostas.get(1).getTopico().getCurso().getNome());
        assertEquals(PROGRAMACAO, respostas.get(1).getTopico().getCurso().getCategoria());
        assertEquals(TOPICO_EXEMPLO, respostas.get(1).getTopico().getTitulo());
        assertEquals(MENSAGEM_EXEMPLO, respostas.get(1).getTopico().getMensagem());
        assertEquals(MENSAGEM_DE_RESPOSTA_DE_EXEMPLO_2, respostas.get(1).getMensagem());
        assertEquals(data, respostas.get(1).getDataCriacao());
    }

    @Test
    public void respostasPorTopicoIdNaoDeveriaCarregarRespostasNaoCadastradas() {

        Curso html5 = new Curso(HTML_5, PROGRAMACAO);
        Topico topico = new Topico(TOPICO_EXEMPLO, MENSAGEM_EXEMPLO, html5);
        em.persist(html5);
        em.persist(topico);

        List<Resposta> respostas = repository.findByTopicoId(1L);
        assertEquals(0, respostas.size());
    }


}
package com.github.julianomachadoo.forumapi.repository;

import com.github.julianomachadoo.forumapi.modelo.Curso;
import com.github.julianomachadoo.forumapi.modelo.Resposta;
import com.github.julianomachadoo.forumapi.modelo.Topico;
import com.github.julianomachadoo.forumapi.modelo.Usuario;
import com.github.julianomachadoo.forumapi.util.builder.CursoBuilder;
import com.github.julianomachadoo.forumapi.util.builder.RespostaBuilder;
import com.github.julianomachadoo.forumapi.util.builder.TopicoBuilder;
import com.github.julianomachadoo.forumapi.util.builder.UsuarioBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class RespostaRepositoryTest {

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
    private static final String NOME_DE_USUARIO_DE_EXEMPLO = "Nome de usuario de exemplo";
    private static final String EMAIL_DE_EXEMPLO = "exemplo1@email.com";
    private static final String SENHA_DE_EXEMPLO_1 = "senhaDeExemplo1";

    @Test
    public void deveriaDevolverListaDeRespostasPorTopicoId() {
        Curso spring = new CursoBuilder()
                .comNome(CURSO_NOME)
                .comCategoria(CURSO_CATEGORIA)
                .build();

        Usuario usuario = new UsuarioBuilder()
                .comNome(NOME_DE_USUARIO_DE_EXEMPLO)
                .comEmail(EMAIL_DE_EXEMPLO)
                .comSenha(SENHA_DE_EXEMPLO_1)
                .build();

        Topico topico = new TopicoBuilder()
                .comTitulo(TITULO_DE_TOPICO_DE_EXEMPLO)
                .comMensagem(MENSAGEM_DE_TOPICO_DE_EXEMPLO)
                .comCurso(spring)
                .comUsuario(usuario)
                .build();

        Resposta resposta = new RespostaBuilder()
                .comMensagem(MENSAGEM_DE_RESPOSTA_DE_EXEMPLO)
                .comTopico(topico)
                .comUsuario(usuario)
                .build();

        Resposta resposta2 = new RespostaBuilder()
                .comMensagem(MENSAGEM_DE_RESPOSTA_DE_EXEMPLO_2)
                .comTopico(topico)
                .comUsuario(usuario)
                .build();

        topico.getRespostas().addAll(Arrays.asList(resposta, resposta2));
        em.persist(spring);
        em.persist(usuario);
        Topico topicoPersist = em.persist(topico);
        em.persist(resposta);
        em.persist(resposta2);

        List<Resposta> respostas = respostaRepository.findByTopicoId(topicoPersist.getId());

        assertNotNull(respostas);
        assertEquals(2, respostas.size());

        assertEquals(respostas, respostas.get(0).getTopico().getRespostas());
        assertEquals(spring, respostas.get(0).getTopico().getCurso());
        assertEquals(CURSO_NOME, respostas.get(0).getTopico().getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, respostas.get(0).getTopico().getCurso().getCategoria());
        assertEquals(topico, respostas.get(0).getTopico());
        assertEquals(TITULO_DE_TOPICO_DE_EXEMPLO, respostas.get(0).getTopico().getTitulo());
        assertEquals(MENSAGEM_DE_TOPICO_DE_EXEMPLO, respostas.get(0).getTopico().getMensagem());
        assertEquals(MENSAGEM_DE_RESPOSTA_DE_EXEMPLO, respostas.get(0).getMensagem());
        assertEquals(usuario, respostas.get(0).getAutor());

        assertEquals(respostas, respostas.get(1).getTopico().getRespostas());
        assertEquals(spring, respostas.get(1).getTopico().getCurso());
        assertEquals(CURSO_NOME, respostas.get(1).getTopico().getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, respostas.get(1).getTopico().getCurso().getCategoria());
        assertEquals(topico, respostas.get(1).getTopico());
        assertEquals(TITULO_DE_TOPICO_DE_EXEMPLO, respostas.get(1).getTopico().getTitulo());
        assertEquals(MENSAGEM_DE_TOPICO_DE_EXEMPLO, respostas.get(1).getTopico().getMensagem());
        assertEquals(MENSAGEM_DE_RESPOSTA_DE_EXEMPLO_2, respostas.get(1).getMensagem());
        assertEquals(usuario, respostas.get(1).getAutor());
    }

    @Test
    public void respostasPorTopicoIdNaoDeveriaCarregarRespostasNaoCadastradas() {
        Curso spring = em.persist(new CursoBuilder()
                .comNome(CURSO_NOME)
                .comCategoria(CURSO_CATEGORIA)
                .build());

        Topico topico = new TopicoBuilder()
                .comTitulo(TITULO_DE_TOPICO_DE_EXEMPLO)
                .comMensagem(MENSAGEM_DE_TOPICO_DE_EXEMPLO)
                .comCurso(spring)
                .build();

        em.persist(topico);

        List<Resposta> respostas = respostaRepository.findByTopicoId(1L);
        assertThat(respostas).isEmpty();
    }
}
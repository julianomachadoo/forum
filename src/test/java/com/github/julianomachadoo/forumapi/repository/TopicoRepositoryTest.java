package com.github.julianomachadoo.forumapi.repository;

import com.github.julianomachadoo.forumapi.modelo.Curso;
import com.github.julianomachadoo.forumapi.modelo.Topico;
import com.github.julianomachadoo.forumapi.modelo.Usuario;
import com.github.julianomachadoo.forumapi.util.builder.CursoBuilder;
import com.github.julianomachadoo.forumapi.util.builder.TopicoBuilder;
import com.github.julianomachadoo.forumapi.util.builder.UsuarioBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    private static final String CURSO_NOME = "Spring Boot";
    private static final String CURSO_CATEGORIA = "Programação";
    private static final String TITULO1 = "Dúvida";
    private static final String TITULO2 = "Dúvida2";
    private static final String MENSAGEM1 = "Erro ao criar projeto";
    private static final String MENSAGEM2 = "Projeto não compila";
    private static final String NOME_DE_USUARIO_DE_EXEMPLO = "Nome de usuario de exemplo";
    private static final String EMAIL_DE_EXEMPLO = "exemplo1@email.com";
    private static final String SENHA_DE_EXEMPLO_1 = "senhaDeExemplo1";

    @Test
    public void deveriaCarregarUmaPaginacaoDeTopicosPorCursoNome() {
        Usuario usuario = new UsuarioBuilder()
                .comNome(NOME_DE_USUARIO_DE_EXEMPLO)
                .comEmail(EMAIL_DE_EXEMPLO)
                .comSenha(SENHA_DE_EXEMPLO_1)
                .build();

        Curso spring = new CursoBuilder()
                .comNome(CURSO_NOME)
                .comCategoria(CURSO_CATEGORIA)
                .build();

        Topico t1 = new TopicoBuilder()
                .comTitulo(TITULO1)
                .comMensagem(MENSAGEM1)
                .comUsuario(usuario)
                .comCurso(spring)
                .build();

        Topico t2 = new TopicoBuilder()
                .comTitulo(TITULO2)
                .comMensagem(MENSAGEM2)
                .comUsuario(usuario)
                .comCurso(spring)
                .build();

        em.persist(usuario);
        em.persist(spring);
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
        assertEquals(usuario, listaTopicos.get(0).getAutor());

        assertEquals(CURSO_NOME, listaTopicos.get(1).getCurso().getNome());
        assertEquals(CURSO_CATEGORIA, listaTopicos.get(1).getCurso().getCategoria());
        assertEquals(TITULO2, listaTopicos.get(1).getTitulo());
        assertEquals(MENSAGEM2, listaTopicos.get(1).getMensagem());
        assertEquals(usuario, listaTopicos.get(1).getAutor());
    }

    @Test
    public void naoDeveriaCarregarPorCursoNomeTopicosNaoCadastrados() {
        em.persist(new CursoBuilder()
                .comNome(CURSO_NOME)
                .comCategoria(CURSO_CATEGORIA)
                .build());

        Page<Topico> topicos = topicoRepository.findByCurso_Nome(CURSO_NOME, PageRequest.of(0, 10));

        assertEquals(0, topicos.stream().toList().size());
        assertThat(topicos).isEmpty();
    }
}
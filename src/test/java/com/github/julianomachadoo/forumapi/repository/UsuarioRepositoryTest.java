package com.github.julianomachadoo.forumapi.repository;

import com.github.julianomachadoo.forumapi.modelo.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private TestEntityManager em;

    private static final String NOME_DE_USUARIO_DE_EXEMPLO = "Nome de usuario de exemplo";
    private static final String NOME_DE_USUARIO_DE_EXEMPLO_2 = "Nome de usuario de exemplo 2";
    private static final String EMAIL_DE_EXEMPLO = "exemplo1@email.com";
    private static final String EMAIL_DE_EXEMPLO_2 = "exemplo2@email.com";
    private static final String SENHA_DE_EXEMPLO_1 = "senhaDeExemplo1";
    private static final String SENHA_DE_EXEMPLO_2 = "senhaDeExemplo2";
    private static final String SENHA_ATUALIZADA = "Senha atualizada";
    private final Usuario usuario1 = new Usuario(NOME_DE_USUARIO_DE_EXEMPLO, EMAIL_DE_EXEMPLO, SENHA_DE_EXEMPLO_1);
    private final Usuario usuario2 = new Usuario(NOME_DE_USUARIO_DE_EXEMPLO_2, EMAIL_DE_EXEMPLO_2, SENHA_DE_EXEMPLO_2);

    @BeforeEach
    public void limparBancoDeUsuarios() {
        repository.deleteAll();
    }

    @Test
    public void deveriaCriarUmNovoUsuario() {
        Usuario usuario = repository.save(usuario1);

        assertNotNull(usuario);
        assertEquals(NOME_DE_USUARIO_DE_EXEMPLO, usuario.getNome());
        assertEquals(EMAIL_DE_EXEMPLO, usuario.getEmail());
        assertEquals(SENHA_DE_EXEMPLO_1, usuario.getPassword());
    }

    @Test
    public void deveriaCarregarTodosOsUsuarios() {
        em.persist(usuario1);
        em.persist(usuario2);

        List<Usuario> usuarios = repository.findAll();

        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
        assertEquals(usuario1, usuarios.get(0));
        assertEquals(NOME_DE_USUARIO_DE_EXEMPLO, usuarios.get(0).getNome());
        assertEquals(EMAIL_DE_EXEMPLO, usuarios.get(0).getEmail());
        assertEquals(SENHA_DE_EXEMPLO_1, usuarios.get(0).getPassword());

        assertEquals(usuario2, usuarios.get(1));
        assertEquals(NOME_DE_USUARIO_DE_EXEMPLO_2, usuarios.get(1).getNome());
        assertEquals(EMAIL_DE_EXEMPLO_2, usuarios.get(1).getEmail());
        assertEquals(SENHA_DE_EXEMPLO_2, usuarios.get(1).getPassword());
    }

    @Test
    public void deveriaCarregarUmUsuarioPeloEmail () {
        Usuario usuarioPersist = em.persist(usuario1);

        Optional<Usuario> usuarioFindByEmail = repository.findByEmail(usuarioPersist.getEmail());

        if (usuarioFindByEmail.isEmpty()) fail();

        assertEquals(NOME_DE_USUARIO_DE_EXEMPLO, usuarioFindByEmail.get().getNome());
        assertEquals(EMAIL_DE_EXEMPLO, usuarioFindByEmail.get().getEmail());
        assertEquals(SENHA_DE_EXEMPLO_1, usuarioFindByEmail.get().getPassword());
    }

    @Test
    public void naoDeveriaCarregarPeloEmailUsuarioNaoCadastrado() {
        Optional<Usuario> usuarioFindByEmail = repository.findByEmail(usuario1.getEmail());

        if (usuarioFindByEmail.isPresent()) fail();

        Assertions.assertThat(usuarioFindByEmail).isEmpty();
    }

    @Test
    public void deveriaAtualizarSenhaDeUmUsuario() {
        em.persist(usuario1);

        Usuario usuarioComSenhaAtualizada = new Usuario(NOME_DE_USUARIO_DE_EXEMPLO, EMAIL_DE_EXEMPLO, SENHA_ATUALIZADA);
        Optional<Usuario> usuarioParaSerAtualizado = repository.findByEmail(EMAIL_DE_EXEMPLO);

        if (usuarioParaSerAtualizado.isEmpty()) fail();

        usuarioParaSerAtualizado.get().setSenha(usuarioComSenhaAtualizada.getSenha());
        Usuario usuarioAtualizado = repository.save(usuarioParaSerAtualizado.get());

        assertNotNull(usuarioAtualizado);
        assertEquals(usuarioComSenhaAtualizada.getSenha(), usuarioAtualizado.getPassword());
    }

    @Test
    public void deveriaDeletarUmUsuario() {
        Usuario usuarioPersist = em.persist(usuario1);
        em.persist(usuario2);

        repository.deleteById(usuarioPersist.getId());
        List<Usuario> usuarios = repository.findAll();

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
    }

    @Test
    public void naoDeveriaDeletarUmUsuarioNaoCadastrado() {
        try {
            repository.deleteById(1L);
        } catch (Exception e) {
            assertEquals(EmptyResultDataAccessException.class, e.getClass());
        }
    }

}
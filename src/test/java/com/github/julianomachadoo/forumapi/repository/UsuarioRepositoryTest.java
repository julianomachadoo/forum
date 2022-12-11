package com.github.julianomachadoo.forumapi.repository;

import com.github.julianomachadoo.forumapi.modelo.Usuario;
import com.github.julianomachadoo.forumapi.util.builder.UsuarioBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private TestEntityManager em;

    private static final String NOME_DE_USUARIO_DE_EXEMPLO = "Nome de usuario de exemplo";
    private static final String EMAIL_DE_EXEMPLO = "exemplo1@email.com";
    private static final String SENHA_DE_EXEMPLO_1 = "senhaDeExemplo1";

    @BeforeEach
    public void limparBancoDeUsuarios() {
        repository.deleteAll();
    }

    @Test
    public void deveriaCarregarUmUsuarioPeloEmail () {
        Usuario usuarioPersist = em.persist(new UsuarioBuilder()
                .comNome(NOME_DE_USUARIO_DE_EXEMPLO)
                .comEmail(EMAIL_DE_EXEMPLO)
                .comSenha(SENHA_DE_EXEMPLO_1)
                .build());

        Optional<Usuario> usuarioFindByEmail = repository.findByEmail(usuarioPersist.getEmail());

        if (usuarioFindByEmail.isEmpty()) fail();

        assertEquals(NOME_DE_USUARIO_DE_EXEMPLO, usuarioFindByEmail.get().getNome());
        assertEquals(EMAIL_DE_EXEMPLO, usuarioFindByEmail.get().getEmail());
        assertEquals(SENHA_DE_EXEMPLO_1, usuarioFindByEmail.get().getPassword());
    }

    @Test
    public void naoDeveriaCarregarPeloEmailUsuarioNaoCadastrado() {
        Usuario usuario = new UsuarioBuilder()
                .comNome(NOME_DE_USUARIO_DE_EXEMPLO)
                .comEmail(EMAIL_DE_EXEMPLO)
                .comSenha(SENHA_DE_EXEMPLO_1)
                .build();
        Optional<Usuario> usuarioFindByEmail = repository.findByEmail(usuario.getEmail());

        if (usuarioFindByEmail.isPresent()) fail();

        Assertions.assertThat(usuarioFindByEmail).isEmpty();
    }
}
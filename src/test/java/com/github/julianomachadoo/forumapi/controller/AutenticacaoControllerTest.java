package com.github.julianomachadoo.forumapi.controller;

import com.github.julianomachadoo.forumapi.modelo.Perfil;
import com.github.julianomachadoo.forumapi.modelo.Usuario;
import com.github.julianomachadoo.forumapi.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AutenticacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository usuarioRepository;

    private final URI uri = new URI("/auth");

    AutenticacaoControllerTest() throws URISyntaxException {
    }

    @Test
    public void deveriaDevolver400CasoDadosDeAutenticacaoEstejamIncorretos() throws Exception {
        String json = "{\"email\":\"invalido@email.com\", \"senha\":\"123456\"}";

        Mockito.when(usuarioRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(400)
                );
    }

    @Test
    public void deveriaDevolverUmBearerTokenCasoAutenticacaoAlunoAcontecaComSucesso() throws Exception {
        String jsonAluno = "{\"email\":\"aluno@email.com\", \"senha\":\"1234\"}";

        Usuario aluno = new Usuario(1L,"aluno", "aluno@email.com", "$2a$12$qFFh.aQ3ZKs7KERYgCUKTeRYt4w5CV8BpAuO7tOihZzhIFNUyK17K");
        aluno.getPerfis().add(new Perfil("ROLE_ALUNO"));

        Mockito.when(usuarioRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(aluno));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(jsonAluno)
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().is(200)
                );
        }

    @Test
    public void deveriaDevolverUmBearerTokenCasoAutenticacaoModeradorAcontecaComSucesso() throws Exception {
        String jsonModerador = "{\"email\":\"moderador@email.com\", \"senha\":\"1234\"}";

        Usuario moderador = new Usuario(1L,"moderador", "moderador@email.com", "$2a$12$qFFh.aQ3ZKs7KERYgCUKTeRYt4w5CV8BpAuO7tOihZzhIFNUyK17K");
        moderador.getPerfis().add(new Perfil("ROLE_MODERADOR"));

        Mockito.when(usuarioRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(moderador));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(jsonModerador)
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().is(200)
                );
    }
}
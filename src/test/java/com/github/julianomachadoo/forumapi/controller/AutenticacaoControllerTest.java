package com.github.julianomachadoo.forumapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AutenticacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveriaDevolver400CasoDadosDeAutenticacaoEstejamIncorretos() throws Exception {
        URI uri = new URI("/auth");
        String json = "{\"email\":\"invalido@email.com\", \"senha\":\"123456\"}";

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
    public void deveriaDevolverUmBearerTokenCasoAutenticacaoAcontecaComSucesso() throws Exception {

        URI uri = new URI("/auth");
        String jsonAluno = "{\"email\":\"aluno@email.com\", \"senha\":\"123456\"}";
        String jsonModerador = "{\"email\":\"moderador@email.com\", \"senha\":\"123456\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(jsonAluno)
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().is(200)
                );

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(jsonModerador)
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().is(200)
                );
    }

}
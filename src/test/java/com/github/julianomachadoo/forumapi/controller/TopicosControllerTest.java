package com.github.julianomachadoo.forumapi.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("prod")
class TopicosControllerProdTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void deveriaPermitirListarTopicosPublicamente() throws Exception {
        URI uri = new URI("/topicos");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers
                        .status().isOk());
    }

    @Test
    public void deveriaPermitirDetalharTopicosPublicamente() throws Exception {
        URI uri = new URI("/topicos/2");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers
                        .status().isOk());
    }

    @Test
    public void naoDeveriaPermitirUmPostNaoAutenticado() throws Exception  {
        URI uri = new URI("/topicos");
        String json = "{\"mensagem\":\"mensagem de teste\", " +
                "\"nomeCurso\":\"Spring Boot\", " +
                "\"titulo\":\"titulo de teste\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isForbidden());
    }

    @Test
    public void deveriaPermitirUmPostAutenticado() throws Exception  {
        URI uri = new URI("/auth");
        String jsonAluno = "{\"email\":\"aluno@email.com\", \"senha\":\"123456\"}";

        URI uriPost = new URI("/topicos");
        String jsonPost = "{\"mensagem\":\"mensagem de teste\", " +
                "\"nomeCurso\":\"Spring Boot\", " +
                "\"titulo\":\"titulo de teste\"}";

        MockHttpServletResponse responseAluno = mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(jsonAluno)
                .contentType(APPLICATION_JSON)).andReturn().getResponse();

        String contentAsString = responseAluno.getContentAsString();
        String token = contentAsString.substring(10, 177);
        String tipoToken = contentAsString.substring(187, 193);

        Assertions.assertEquals("Bearer", tipoToken);

        mockMvc.perform(MockMvcRequestBuilders
                .post(uriPost)
                .content(jsonPost)
                .contentType(APPLICATION_JSON)
                .header("authorization", tipoToken + " " + token))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }


    @Test
    public void naoDeveriaPermitirUmPutNaoAutenticado() throws Exception  {
        URI uri = new URI("/topicos/1");
        String json = "{\"mensagem\":\"mensagem de teste\", " +
                "\"nomeCurso\":\"Spring Boot\", " +
                "\"titulo\":\"titulo de teste\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .put(uri)
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isForbidden());
    }

    @Test
    public void deveriaPermitirUmPutAutenticado() throws Exception  {
        URI uri = new URI("/auth");
        String jsonAluno = "{\"email\":\"aluno@email.com\", \"senha\":\"123456\"}";

        URI uriPost = new URI("/topicos/1");
        String jsonPost = "{\"mensagem\":\"mensagem de teste\", " +
                "\"nomeCurso\":\"Spring Boot\", " +
                "\"titulo\":\"titulo de teste\"}";

        MockHttpServletResponse responseAluno = mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(jsonAluno)
                .contentType(APPLICATION_JSON)).andReturn().getResponse();

        String contentAsString = responseAluno.getContentAsString();
        String token = contentAsString.substring(10, 177);
        String tipoToken = contentAsString.substring(187, 193);

        Assertions.assertEquals("Bearer", tipoToken);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(uriPost)
                        .content(jsonPost)
                        .contentType(APPLICATION_JSON)
                        .header("authorization", tipoToken + " " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void naoDeveriaPermitirUmDeleteNaoAutenticado() throws Exception  {
        URI uri = new URI("/topicos/1");

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(uri))
                .andExpect(MockMvcResultMatchers
                        .status().isForbidden());
    }

    @Test
    public void naoDeveriaPermitirUmDeletePorAluno() throws Exception  {
        URI uri = new URI("/auth");
        String jsonAluno = "{\"email\":\"aluno@email.com\", \"senha\":\"123456\"}";

        URI uriPost = new URI("/topicos/1");

        MockHttpServletResponse responseAluno = mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(jsonAluno)
                .contentType(APPLICATION_JSON)).andReturn().getResponse();

        String contentAsString = responseAluno.getContentAsString();
        String token = contentAsString.substring(10, 177);
        String tipoToken = contentAsString.substring(187, 193);

        Assertions.assertEquals("Bearer", tipoToken);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(uriPost)
                        .header("authorization", tipoToken + " " + token))
                    .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void deveriaPermitirUmDeletePorModerador() throws Exception  {
        URI uri = new URI("/auth");
        String jsonAluno = "{\"email\":\"moderador@email.com\", \"senha\":\"123456\"}";

        URI uriPost = new URI("/topicos/1");

        MockHttpServletResponse responseAluno = mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(jsonAluno)
                .contentType(APPLICATION_JSON)).andReturn().getResponse();

        String contentAsString = responseAluno.getContentAsString();
        String token = contentAsString.substring(10, 177);
        String tipoToken = contentAsString.substring(187, 193);

        Assertions.assertEquals("Bearer", tipoToken);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(uriPost)
                        .header("authorization", tipoToken + " " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TopicosControllerTest {
}


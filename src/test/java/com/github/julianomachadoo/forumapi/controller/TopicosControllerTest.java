package com.github.julianomachadoo.forumapi.controller;

import com.github.julianomachadoo.forumapi.modelo.Curso;
import com.github.julianomachadoo.forumapi.modelo.Topico;
import com.github.julianomachadoo.forumapi.modelo.Usuario;
import com.github.julianomachadoo.forumapi.repository.TopicoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TopicosControllerAuthTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TopicoRepository topicoRepository;

    private final Topico topico = new Topico("Titulo", "Mensagem", new Usuario(), new Curso());
    private final URI uriTopicos = new URI("/topicos");
    private final URI uriTopicosUm = new URI("/topicos/1");
    private final URI uriAuth = new URI("/auth");
    private final String jsonTopico = "{\"mensagem\":\"mensagem de teste\", " +
            "\"nomeCurso\":\"Spring Boot\", " +
            "\"titulo\":\"titulo de teste\"}";
    private final String jsonAluno = "{\"email\":\"aluno@email.com\", \"senha\":\"123456\"}";
    private final String jsonModerador = "{\"email\":\"moderador@email.com\", \"senha\":\"123456\"}";

    TopicosControllerAuthTest() throws URISyntaxException {
    }

    @BeforeEach
    public void iniciar() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveriaPermitirListarTopicosPublicamente() throws Exception {
        Page<Topico> pageTopicos = new Page<>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super Topico, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Topico> getContent() {
                return null;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Topico> iterator() {
                return null;
            }
        };

        Mockito.when(topicoRepository.findAll(PageRequest.of(0, 10))).thenReturn(pageTopicos);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(uriTopicos))
                .andExpect(MockMvcResultMatchers
                        .status().isOk());
    }

    @Test
    public void deveriaPermitirDetalharTopicosPublicamente() throws Exception {
        Mockito.when(topicoRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(topico));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(uriTopicosUm))
                .andExpect(MockMvcResultMatchers
                        .status().isOk());
    }

    @Test
    public void naoDeveriaPermitirUmPostNaoAutenticado() throws Exception  {
        Mockito.when(topicoRepository.save(ArgumentMatchers.any(Topico.class))).thenReturn(topico);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uriTopicos)
                        .content(jsonTopico)
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isForbidden());
    }

    @Test
    public void deveriaPermitirUmPostAutenticado() throws Exception  {
        String token = authToken(jsonAluno);

        Mockito.when(topicoRepository.save(ArgumentMatchers.any(Topico.class))).thenReturn(topico);

        mockMvc.perform(MockMvcRequestBuilders
                .post(uriTopicos)
                .content(jsonTopico)
                .contentType(APPLICATION_JSON)
                .header("authorization", token))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void naoDeveriaPermitirUmPutNaoAutenticado() throws Exception  {
        Mockito.when(topicoRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(topico));
        Mockito.when(topicoRepository.getReferenceById(ArgumentMatchers.anyLong())).thenReturn(topico);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(uriTopicosUm)
                        .content(jsonTopico)
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isForbidden());
    }

    @Test
    public void deveriaPermitirUmPutAutenticado() throws Exception  {
        String token = authToken(jsonAluno);

        Mockito.when(topicoRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(topico));
        Mockito.when(topicoRepository.getReferenceById(ArgumentMatchers.anyLong())).thenReturn(topico);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(uriTopicosUm)
                        .content(jsonTopico)
                        .contentType(APPLICATION_JSON)
                        .header("authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void naoDeveriaPermitirUmDeleteNaoAutenticado() throws Exception  {
       Mockito.when(topicoRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(topico));
        Mockito.doNothing().when(topicoRepository).deleteById(ArgumentMatchers.anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(uriTopicosUm))
                .andExpect(MockMvcResultMatchers
                        .status().isForbidden());
    }

    @Test
    public void naoDeveriaPermitirUmDeletePorAluno() throws Exception  {
        String token = authToken(jsonAluno);

        Mockito.when(topicoRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(topico));
        Mockito.doNothing().when(topicoRepository).deleteById(ArgumentMatchers.anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(uriTopicosUm)
                        .header("authorization", token))
                    .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void deveriaPermitirUmDeletePorModerador() throws Exception  {
        String token = authToken(jsonModerador);

        Mockito.when(topicoRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(topico));
        Mockito.doNothing().when(topicoRepository).deleteById(ArgumentMatchers.anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(uriTopicosUm)
                        .header("authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String authToken(String json) throws Exception {
        MockHttpServletResponse responseAluno = mockMvc.perform(MockMvcRequestBuilders
                .post(uriAuth)
                .content(json)
                .contentType(APPLICATION_JSON)).andReturn().getResponse();

        String contentAsString = responseAluno.getContentAsString();
        String token = contentAsString.substring(10, 177);
        String tipoToken = contentAsString.substring(187, 193);

        Assertions.assertEquals("Bearer", tipoToken);
        return tipoToken + " " + token;
    }
}

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class TopicosControllerDevTest {
}


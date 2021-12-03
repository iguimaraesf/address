package com.petz.challenge.address.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petz.challenge.address.common.EnderecoVO;
import com.petz.challenge.address.exceptions.EnderecoExistenteException;
import com.petz.challenge.address.exceptions.EnderecoNaoEncontradoException;
import com.petz.challenge.address.fixture.EnderecoFixture;
import com.petz.challenge.address.service.EnderecoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest()
public class EnderecoControllerTest {
    public static final String ENDPOINT = "/endereco";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EnderecoService service;

    @Test
    void gravaUmEnderecoDoBrasil_sucesso() throws Exception {
        EnderecoVO vo = EnderecoFixture.enderecoBrasilComCoordenadas();
        Mockito.when(service.criar(Mockito.any())).thenReturn(vo);
        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(vo))
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void gravaEnderecoFaltaAlgumDado_validacao() throws Exception {
        EnderecoVO vo = EnderecoFixture.enderecoIncompleto();
        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(vo))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void gravaEnderecoNaoEncontrado_falha() throws Exception {
        EnderecoVO vo = EnderecoFixture.enderecoInvalido();
        Mockito.when(service.criar(Mockito.any())).thenThrow(new EnderecoNaoEncontradoException());
        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(vo))
        ).andExpect(MockMvcResultMatchers.status().isExpectationFailed());
    }

    @Test
    void gravaOMesmoEndereco_conflito() throws Exception {
        EnderecoVO vo = EnderecoFixture.enderecoBrasilComCoordenadas();
        Mockito.when(service.criar(Mockito.any())).thenThrow(new EnderecoExistenteException());
        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(vo))
        ).andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void alteraUmEnderecoDoBrasil_sucesso() throws Exception {
        EnderecoVO vo = EnderecoFixture.enderecoBrasilComCoordenadas();
        Mockito.when(service.alterar(Mockito.any())).thenReturn(vo);
        mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(vo))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void alteraEnderecoFaltaAlgumDado_validacao() throws Exception {
        EnderecoVO vo = EnderecoFixture.enderecoIncompleto();
        mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(vo))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void alteraEnderecoNaoEncontrado_falha() throws Exception {
        EnderecoVO vo = EnderecoFixture.enderecoInvalido();
        Mockito.when(service.alterar(Mockito.any())).thenThrow(new EnderecoNaoEncontradoException());
        mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(vo))
        ).andExpect(MockMvcResultMatchers.status().isExpectationFailed());
    }

    @Test
    void alteraComEnderecoQueJaExiste_conflito() throws Exception {
        EnderecoVO vo = EnderecoFixture.enderecoBrasilComCoordenadas();
        Mockito.when(service.alterar(Mockito.any())).thenThrow(new EnderecoExistenteException());
        mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(vo))
        ).andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void leEndereco_existe() throws Exception {
        Mockito.when(service.ler(1L)).thenReturn(EnderecoFixture.enderecoBrasil());
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void leEndereco_naoExiste() throws Exception {
        Mockito.when(service.ler(1L)).thenThrow(new EnderecoNaoEncontradoException());
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isExpectationFailed());
    }

    @Test
    void excluiEndereco_existe() throws Exception {
        Mockito.doNothing().when(service).excluir(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void excluiEndereco_naoExiste() throws Exception {
        Mockito.doThrow(new EnderecoNaoEncontradoException()).when(service).excluir(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT + "/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isExpectationFailed());
    }


}

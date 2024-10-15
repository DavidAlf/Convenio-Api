package com.proyecto.convenios.controller.v1;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.convenios.dto.ConvenioRtaDto;
import com.proyecto.convenios.dto.request.ConvenioRequest;
import com.proyecto.convenios.service.IConvenioService;

@WebMvcTest(ConvenioController.class)
class ConvenioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IConvenioService convenioService;

    private ObjectMapper objectMapper = new ObjectMapper();
    private ConvenioRequest convenioRequest;
    private ConvenioRtaDto convenioRtaDto;

    Integer pageNo = 0;
    Integer pageSize = 10;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        convenioRequest = new ConvenioRequest();
        convenioRequest.setNombreConvenio("Prueba");
        convenioRequest.setAlias("Alias");
        convenioRequest.setUrlRecaudos("http://");

        convenioRtaDto = new ConvenioRtaDto();
        convenioRtaDto.setIdConvenio(1);
        convenioRtaDto.setNombreConvenio("Prueba");
        convenioRtaDto.setAlias("Alias");
        convenioRtaDto.setUrlRecaudos("http://");
    }

    @Test
    void crearConvenioTest() throws Exception {
        // > Given
        given(convenioService.crear(convenioRequest)).willAnswer(invocation -> {
            return convenioRtaDto;
        });

        // > When
        ResultActions response = mockMvc.perform(post("/v1/convenios").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(convenioRtaDto)));

        // > Then
        response.andDo(print()).andExpect(status().isOk());
    }

    @Test
    void actualizarConvenioTest() throws Exception {
        // > Given
        Integer id = convenioRtaDto.getIdConvenio();
        given(convenioService.actualizar(id, convenioRequest)).willAnswer(invocation -> {
            return ResponseEntity.ok(convenioRtaDto);
        });

        // > When
        ResultActions response = mockMvc.perform(put("/v1/convenios/{id}", id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(convenioRtaDto)));

        // > Then
        response.andDo(print()).andExpect(status().isOk());
    }

    @Test
    void actualizarConvenioTest_NotFound() throws Exception {
        // > Given
        Integer id = 999; // ID que no existe
        given(convenioService.actualizar(id, convenioRequest)).willAnswer(invocation -> {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Convenio no encontrado");
        });

        // > When
        ResultActions response = mockMvc.perform(put("/v1/convenios/{id}", id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(convenioRequest)));

        // > Then
        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void consultarConveniosTest() throws Exception {
        // > Given
        List<ConvenioRtaDto> list = new ArrayList<ConvenioRtaDto>();
        list.add(convenioRtaDto);

        given(convenioService.consultar(pageNo, pageSize)).willAnswer(invocation -> {
            return ResponseEntity.ok(list);
        });

        // > When
        ResultActions response = mockMvc.perform(get("/v1/convenios"));

        // > Then
        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(list.size())));
    }

    @Test
    void eliminarConvenioTestTest() throws Exception {
        // > Given
        Integer id = convenioRtaDto.getIdConvenio();
        willDoNothing().given(convenioService).eliminar(id);

        // > When
        ResultActions response = mockMvc
                .perform(delete("/v1/convenios/{id}", id).contentType(MediaType.APPLICATION_JSON));

        // > Then
        response.andDo(print()).andExpect(status().isOk());
    }
}

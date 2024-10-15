package com.proyecto.convenios.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.proyecto.convenios.dto.ConvenioRtaDto;
import com.proyecto.convenios.dto.request.ConvenioRequest;
import com.proyecto.convenios.entity.ConvenioEntity;
import com.proyecto.convenios.repository.read.IConvenioReadRepository;
import com.proyecto.convenios.repository.write.IConvenioWriteRepository;
import com.proyecto.convenios.util.ModelMapperUtil;

import co.com.ath.commons.dto.api.AbstractResponseApi;
import co.com.ath.commons.dto.convenios.api.ConveniosResponse;
import co.com.ath.commons.dto.convenios.api.ResPostConvenio;
import co.com.ath.commons.dto.convenios.api.ResPutConvenio;

class ConvenioServiceImplTest {

    @Mock
    private IConvenioReadRepository convenioReadRepository;

    @Mock
    private IConvenioWriteRepository convenioWriteRepository;

    @Mock
    private ModelMapperUtil modelMapperUtil;

    @InjectMocks
    private ConvenioServiceImpl convenioService;

    ConvenioRequest convenioRequest;
    ConvenioEntity convenioEntity;
    ConvenioEntity updatedEntity;
    ConvenioRtaDto convenioRtaDto;
    ConveniosResponse conveniosResponse;
    ResPostConvenio resPostConvenio;
    ResPutConvenio resPutConvenio;

    Integer pageNo = 0;
    Integer pageSize = 10;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        convenioRequest = new ConvenioRequest();
        convenioRequest.setNombreConvenio("Prueba");

        convenioEntity = new ConvenioEntity();
        convenioEntity.setIdConvenio(1);
        convenioEntity.setNombreConvenio("Prueba");

        updatedEntity = new ConvenioEntity();
        convenioEntity.setIdConvenio(1);
        convenioEntity.setNombreConvenio("Prueba 2");

        convenioRtaDto = new ConvenioRtaDto();
        convenioRtaDto.setIdConvenio(1);
        convenioRtaDto.setNombreConvenio("Prueba");

        conveniosResponse = new ConveniosResponse();
        conveniosResponse.setIdConvenio(1);
        conveniosResponse.setNombreConvenio("Prueba");

        resPostConvenio = (ResPostConvenio) AbstractResponseApi.ok(ResPostConvenio.class);
        resPutConvenio = (ResPutConvenio) AbstractResponseApi.ok(ResPutConvenio.class);
    }

    @Test
    void testCrear_Success() {
        // Given
        given(modelMapperUtil.map(convenioRequest, ConvenioEntity.class)).willReturn(convenioEntity);
        given(convenioWriteRepository.save(convenioEntity)).willReturn(convenioEntity);
        given(modelMapperUtil.map(convenioEntity, ConveniosResponse.class)).willReturn(conveniosResponse);
        resPostConvenio.setConveniosResponse(conveniosResponse);

        // When
        ResponseEntity<AbstractResponseApi> responseEntity = convenioService.crear(convenioRequest);

        System.out.println("responseEntity.getBody() " + responseEntity.getBody());

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(convenioWriteRepository, times(1)).save(convenioEntity);
        verify(modelMapperUtil, times(1)).map(convenioRequest, ConvenioEntity.class);
        verify(modelMapperUtil, times(1)).map(convenioEntity, ConveniosResponse.class);
    }

    @Test
    void testActualizar_Success() {
        // Given
        Integer id = convenioEntity.getIdConvenio();

        given(modelMapperUtil.map(convenioRequest, ConvenioEntity.class)).willReturn(convenioEntity);
        given(convenioReadRepository.findById(id)).willReturn(Optional.of(convenioEntity));
        given(convenioWriteRepository.save(convenioEntity)).willReturn(updatedEntity);
        given(modelMapperUtil.map(convenioEntity, ConveniosResponse.class)).willReturn(conveniosResponse);
        resPutConvenio.setConveniosResponse(conveniosResponse);

        // When
        ResponseEntity<AbstractResponseApi> responseEntity = convenioService.actualizar(id, convenioRequest);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        verify(convenioWriteRepository, times(1)).save(convenioEntity);
        verify(modelMapperUtil, times(1)).map(convenioRequest, ConvenioEntity.class);
        verify(modelMapperUtil, times(1)).map(updatedEntity, ConveniosResponse.class);
    }

    @Test
    void testActualizar_NotFound() {
        // Given
        Integer id = convenioEntity.getIdConvenio();
        convenioRequest = new ConvenioRequest();
        convenioEntity = new ConvenioEntity();

        given(modelMapperUtil.map(convenioRequest, ConvenioEntity.class)).willReturn(convenioEntity);
        given(convenioReadRepository.findById(id)).willReturn(Optional.empty());

        // When
        ResponseEntity<AbstractResponseApi> responseEntity = convenioService.actualizar(id, convenioRequest);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(convenioWriteRepository, times(0)).save(convenioEntity);
        verify(modelMapperUtil, times(1)).map(convenioRequest, ConvenioEntity.class);
    }

    @Test
    void testConsultar_Success() {
        // Given
        List<ConvenioEntity> convenioEntities = new ArrayList<>();
        List<ConvenioRtaDto> convenioDtos = new ArrayList<>();
        convenioDtos.add(convenioRtaDto);

         Page<ConvenioEntity> page = new PageImpl<>(convenioEntities, PageRequest.of(pageNo, pageSize), convenioEntities.size());

        given(convenioReadRepository.findAll(any(Pageable.class))).willReturn(page);
        given(modelMapperUtil.map(convenioEntities, ConveniosResponse.class)).willReturn(conveniosResponse);
        resPutConvenio.setConveniosResponse(conveniosResponse);

        // When
        ResponseEntity<AbstractResponseApi> responseEntity = convenioService.consultar(pageNo, pageSize);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testEliminar_Success() {
        // Given
        Integer id = convenioEntity.getIdConvenio();
        convenioEntity = new ConvenioEntity(); // Asegúrate de inicializarlo según sea necesario

        given(convenioReadRepository.findById(id)).willReturn(Optional.of(convenioEntity));

        // When
        convenioService.eliminar(id);

        // Then
        verify(convenioWriteRepository).delete(convenioEntity);
    }

    @Test
    void testEliminar_NotFound() {
        // Given
        Integer id = convenioEntity.getIdConvenio();

        given(convenioReadRepository.findById(id)).willReturn(Optional.empty());

        // When
        convenioService.eliminar(id);

        // Then
        verify(convenioReadRepository).findById(id);
    }
}

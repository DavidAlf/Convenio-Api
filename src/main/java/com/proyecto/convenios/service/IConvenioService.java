package com.proyecto.convenios.service;

import org.springframework.http.ResponseEntity;

import com.proyecto.convenios.dto.request.ConvenioRequest;

import co.com.ath.commons.dto.api.AbstractResponseApi;

public interface IConvenioService {

    public ResponseEntity<AbstractResponseApi> crear(ConvenioRequest convenioRequest);

    public ResponseEntity<AbstractResponseApi> actualizar(Integer id, ConvenioRequest convenioRequest);

    public ResponseEntity<AbstractResponseApi> consultar(Integer pageNo, Integer pageSize);

    public void eliminar(Integer id);

}
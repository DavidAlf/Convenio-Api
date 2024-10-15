package com.proyecto.convenios.controller.v1;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.convenios.dto.request.ConvenioRequest;
import com.proyecto.convenios.service.IConvenioService;

import co.com.ath.commons.dto.api.AbstractResponseApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Control de Convenios")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/convenios")
public class ConvenioController {

    private final IConvenioService convenioService;

    public ConvenioController(IConvenioService convenioService) {
        this.convenioService = convenioService;
    }

    @PostMapping
    @Operation(summary = "Crea Convenio")
    public ResponseEntity<AbstractResponseApi> crear(@Valid @RequestBody ConvenioRequest convenioRequest) {

        return convenioService.crear(convenioRequest);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza Convenio")
    public ResponseEntity<AbstractResponseApi> actualizar(@PathVariable Integer id,
            @Valid @RequestBody ConvenioRequest convenioRequest) {
        return convenioService.actualizar(id, convenioRequest);
    }

    @GetMapping
    @Operation(summary = "Lista Convenios")
    public ResponseEntity<AbstractResponseApi> consultar(
            @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
            @RequestParam(defaultValue = "10", name = "pageSize", required = false) Integer pageSize) {

        return convenioService.consultar(pageNo, pageSize);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina Convenio")
    public void eliminar(@PathVariable Integer id) {
        convenioService.eliminar(id);
    }

}

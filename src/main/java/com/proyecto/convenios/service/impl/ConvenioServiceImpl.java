package com.proyecto.convenios.service.impl;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.convenios.dto.request.ConvenioRequest;
import com.proyecto.convenios.entity.ConvenioEntity;
import com.proyecto.convenios.repository.read.IConvenioReadRepository;
import com.proyecto.convenios.repository.write.IConvenioWriteRepository;
import com.proyecto.convenios.service.IConvenioService;
import com.proyecto.convenios.util.BillPayUtil;
import com.proyecto.convenios.util.EntityUtils;
import com.proyecto.convenios.util.ModelMapperUtil;

import co.com.ath.commons.dto.api.AbstractResponseApi;
import co.com.ath.commons.dto.convenios.api.ConveniosResponse;
import co.com.ath.commons.dto.convenios.api.ResGetConveniosList;
import co.com.ath.commons.dto.convenios.api.ResPostConvenio;
import co.com.ath.commons.dto.convenios.api.ResPutConvenio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConvenioServiceImpl implements IConvenioService {

    private final IConvenioReadRepository convenioReadRepository;

    private final IConvenioWriteRepository convenioWriteRepository;

    private final ModelMapperUtil modelMapperUtil;

    public ConvenioServiceImpl(IConvenioReadRepository convenioReadRepository,
            IConvenioWriteRepository convenioWriteRepository, ModelMapperUtil modelMapperUtil) {
        this.convenioReadRepository = convenioReadRepository;
        this.convenioWriteRepository = convenioWriteRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    @Override
    public ResponseEntity<AbstractResponseApi> crear(ConvenioRequest convenioRequest) {
        log.info("[ConvenioServiceImpl(crear)] -> Guardando ");

        var entity = modelMapperUtil.map(convenioRequest, ConvenioEntity.class);

        var entitySaved = convenioWriteRepository.save(entity);

        ResPostConvenio resPostConvenio = (ResPostConvenio) AbstractResponseApi.ok(ResPostConvenio.class);

        resPostConvenio.setConveniosResponse(modelMapperUtil.map(entitySaved, ConveniosResponse.class));

        return BillPayUtil.buildResponse(resPostConvenio, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AbstractResponseApi> actualizar(Integer id, ConvenioRequest convenioRequest) {
        log.info("[ConvenioServiceImpl(actualizar)] -> Actualizando ");

        var entity = modelMapperUtil.map(convenioRequest, ConvenioEntity.class);

        var entityUpdate = convenioReadRepository.findById(id).map(item -> {
            item.setIdConvenio(id);
            EntityUtils.updateIfNotNull(item::setNombreConvenio, entity.getNombreConvenio());
            EntityUtils.updateIfNotNull(item::setIdConvenioPayc, entity.getIdConvenioPayc());
            EntityUtils.updateIfNotNull(item::setIdTipoConvenio, entity.getIdTipoConvenio());
            EntityUtils.updateIfNotNull(item::setIdEmpresa, entity.getIdEmpresa());
            EntityUtils.updateIfNotNull(item::setIdCiudad, entity.getIdCiudad());
            EntityUtils.updateIfNotNull(item::setIdEstado, entity.getIdEstado());
            EntityUtils.updateIfNotNull(item::setIdCategoria, entity.getIdCategoria());
            EntityUtils.updateIfNotNull(item::setCodigoNura, entity.getCodigoNura());
            EntityUtils.updateIfNotNull(item::setFechaEstado, entity.getFechaEstado());
            EntityUtils.updateIfNotNull(item::setAlias, entity.getAlias());
            EntityUtils.updateIfNotNull(item::setUrlRecaudos, entity.getUrlRecaudos());
            EntityUtils.updateIfNotNull(item::setUrlConsulta, entity.getUrlConsulta());
            EntityUtils.updateIfNotNull(item::setUrlReverso, entity.getUrlReverso());
            EntityUtils.updateIfNotNull(item::setTipoBusqueda, entity.getTipoBusqueda());
            EntityUtils.updateIfNotNull(item::setCodigoEan, entity.getCodigoEan());
            EntityUtils.updateIfNotNull(item::setReintentable, entity.getReintentable());
            EntityUtils.updateIfNotNull(item::setPermiteFacturaVencida, entity.getPermiteFacturaVencida());
            EntityUtils.updateIfNotNull(item::setDiasPermanenciaFactura, entity.getDiasPermanenciaFactura());
            EntityUtils.updateIfNotNull(item::setDiasAntesVencimiento, entity.getDiasAntesVencimiento());
            EntityUtils.updateIfNotNull(item::setUbicacionFactura, entity.getUbicacionFactura());
            EntityUtils.updateIfNotNull(item::setUbicacionAlternaFactura, entity.getUbicacionAlternaFactura());
            EntityUtils.updateIfNotNull(item::setImagenApoyoConvenio, entity.getImagenApoyoConvenio());
            EntityUtils.updateIfNotNull(item::setConvenioPadre, entity.getConvenioPadre());
            EntityUtils.updateIfNotNull(item::setIdConvenioPadre, entity.getIdConvenioPadre());
            EntityUtils.updateIfNotNull(item::setPagoParcial, entity.getPagoParcial());
            EntityUtils.updateIfNotNull(item::setValidaMonto, entity.getValidaMonto());
            EntityUtils.updateIfNotNull(item::setIdImplementacion, entity.getIdImplementacion());
            EntityUtils.updateIfNotNull(item::setDiasCiclo, entity.getDiasCiclo());
            EntityUtils.updateIfNotNull(item::setMensajeriaIncuyeCentavos, entity.getMensajeriaIncuyeCentavos());
            EntityUtils.updateIfNotNull(item::setPermiteDomiciliacion, entity.getPermiteDomiciliacion());
            EntityUtils.updateIfNotNull(item::setPorcentajeAceptacion, entity.getPorcentajeAceptacion());
            EntityUtils.updateIfNotNull(item::setSubServicioPadre, entity.getSubServicioPadre());
            EntityUtils.updateIfNotNull(item::setNuraSubServicioPadre, entity.getNuraSubServicioPadre());
            EntityUtils.updateIfNotNull(item::setCodigoServicioAdicional, entity.getCodigoServicioAdicional());
            EntityUtils.updateIfNotNull(item::setFechaMigracion, entity.getFechaMigracion());
            EntityUtils.updateIfNotNull(item::setIdEstructuraArchivoRecaudo, entity.getIdEstructuraArchivoRecaudo());
            EntityUtils.updateIfNotNull(item::setIdFormato, entity.getIdFormato());
            EntityUtils.updateIfNotNull(item::setTipoRefresco, entity.getTipoRefresco());

            return convenioWriteRepository.save(item);
        }).orElse(null);

        ResPutConvenio resPutConvenio = (ResPutConvenio) AbstractResponseApi.ok(ResPutConvenio.class);
        resPutConvenio.setConveniosResponse(modelMapperUtil.map(entityUpdate, ConveniosResponse.class));

        if (entityUpdate == null) {
            return BillPayUtil.buildResponse(resPutConvenio, HttpStatus.NOT_FOUND);
        }

        return BillPayUtil.buildResponse(resPutConvenio, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<AbstractResponseApi> consultar(Integer pageNo, Integer pageSize) {
        log.info("[ConvenioServiceImpl(consultar)] -> Listar ");

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("idCategoria"));

        List<ConveniosResponse> listConveniosResponse = modelMapperUtil.mapAll(convenioReadRepository.findAll(paging).getContent(),
                ConveniosResponse.class);

        ResGetConveniosList resGetConveniosList = (ResGetConveniosList) AbstractResponseApi
                .ok(ResGetConveniosList.class);

        resGetConveniosList.setListConveniosResponse(listConveniosResponse);

        return BillPayUtil.buildResponse(resGetConveniosList, HttpStatus.OK);
    }

    @Override
    public void eliminar(Integer id) {
        log.info("[ConvenioServiceImpl(eliminar)] -> Eliminando ");

        String msn = convenioReadRepository.findById(id).map(objDelete -> {
            convenioWriteRepository.delete(objDelete);

            return "Eliminado con exito";
        }).orElse("Error eliminando");

        log.info(msn);
    }

}

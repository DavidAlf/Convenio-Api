package com.proyecto.convenios.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ConvenioRtaDto {

    private Integer idConvenio;

	private String nombreConvenio;

	private Integer idConvenioPayc;

	private Integer idTipoConvenio;

	private Integer idEmpresa;

	private Integer idCiudad;

	private Integer idEstado;

	private Integer idCategoria;

	private String codigoNura;

	private Date fechaEstado;

	private String alias;

	private String urlRecaudos;
	
	private String urlConsulta;

	private String urlReverso;

	private Integer tipoBusqueda;

	private String codigoEan;

	private String permiteFacturaVencida;

	private Integer diasPermanenciaFactura;

	private Integer diasAntesVencimiento;

	private Integer ubicacionFactura;
	
	private Integer ubicacionAlternaFactura;

	private String imagenApoyoConvenio;

	private String pagoParcial;
	
	private Integer validaMonto;

	private boolean reintentable;

	private String convenioPadre;

	private Integer idConvenioPadre;

	private Integer idImplementacion;

	private Integer codigoExterno;

	private String nombreCategoria;

	private String nombreEmpresa;

	private String numeroDocumento;

	private Integer idTipoDocumento;

	private String etiquetaCampo;

	private String numeroTelefono;

	private String direccion;

	private Integer idBanco;

	private String nombreCiudad;

	private String nombreDepartamento;

	private String codigoDepartamento;

	private Integer codBancoConvenio;

	private Integer diasCiclo;

	private Integer mensajeriaIncuyeCentavos;

	private String permiteDomiciliacion;

	private Integer porcentajeAceptacion;

	private String subServicioPadre;

	private String nuraSubServicioPadre;

	private String codigoServicioAdicional;
	
	private Integer idEstructuraArchivoRecaudo;
	
	private Integer idFormato;
	
	private String tipoRefresco;

}

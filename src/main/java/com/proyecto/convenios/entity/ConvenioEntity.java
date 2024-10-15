package com.proyecto.convenios.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "convenios")
public class ConvenioEntity {

	@Id
	@Column(name = "ID_CONVENIO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idConvenio;

	@Column(name = "NOMBRE_CONVENIO")
	private String nombreConvenio;

	@Column(name = "ID_CONVENIO_PAYC")
	private Integer idConvenioPayc;

	@Column(name = "ID_TIPO_CONVENIO")
	private Integer idTipoConvenio;

	@Column(name = "ID_EMPRESA")
	private Integer idEmpresa;

	@Column(name = "ID_CIUDAD")
	private Integer idCiudad;

	@Column(name = "ID_ESTADO")
	private Integer idEstado;

	@Column(name = "ID_CATEGORIA")
	private Integer idCategoria;

	@Column(name = "CODIGO_NURA")
	private String codigoNura;

	@Column(name = "FECHA_ESTADO")
	private Date fechaEstado;

	@Column(name = "ALIAS")
	private String alias;

	@Column(name = "URL_RECAUDOS")
	private String urlRecaudos;

	@Column(name = "URL_CONSULTA")
	private String urlConsulta;

	@Column(name = "URL_REVERSO")
	private String urlReverso;

	@Column(name = "TIPO_BUSQUEDA")
	private Integer tipoBusqueda;

	@Column(name = "CODIGO_EAN")
	private String codigoEan;

	@Column(name = "REINTENTABLE")
	private String reintentable;

	@Column(name = "PERMITE_FACTURA_VENCIDA")
	private String permiteFacturaVencida;

	@Column(name = "DIAS_PERMANENCIA_FACTURA")
	private Integer diasPermanenciaFactura;

	@Column(name = "DIAS_ANTES_VENCIMIENTO")
	private Integer diasAntesVencimiento;

	@Column(name = "UBICACION_FACTURA")
	private Integer ubicacionFactura;

	@Column(name = "UBICACION_ALTERNA_FACTURA")
	private Integer ubicacionAlternaFactura;

	@Column(name = "IMAGEN_APOYO_CONVENIO")
	private String imagenApoyoConvenio;

	@Column(name = "CONVENIO_PADRE")
	private String convenioPadre;

	@Column(name = "ID_CONVENIO_PADRE")
	private Integer idConvenioPadre;

	@Column(name = "PAGO_PARCIAL")
	private String pagoParcial;

	@Column(name = "VALIDA_MONTO")
	private Integer validaMonto;

	@Column(name = "ID_IMPLEMENTACION")
	private Integer idImplementacion;

	@Column(name = "DIAS_CICLO")
	private Integer diasCiclo;

	@Column(name = "MENSAJERIA_INCLUYE_CENTAVOS")
	private Integer mensajeriaIncuyeCentavos;

	@Column(name = "PERMITE_DOMICILIACION")
	private String permiteDomiciliacion;

	@Column(name = "PORCENTAJE_ACEPTACION")
	private Integer porcentajeAceptacion;

	@Column(name = "SUBSERVICIO_PADRE")
	private String subServicioPadre;

	@Column(name = "NURA_SUBSERVICIO_PADRE")
	private String nuraSubServicioPadre;

	@Column(name = "CODIGO_SERVICIO_ADICIONAL")
	private String codigoServicioAdicional;

	@Column(name = "FECHA_MIGRACION", updatable = false)
	private Date fechaMigracion;

	@Column(name = "ID_ESTRUCTURA_ARCHIVO_RECAUDO")
	private Integer idEstructuraArchivoRecaudo;

	@Column(name = "ID_FORMATO")
	private Integer idFormato;

	@Column(name = "TIPO_REFRESCO")
	private String tipoRefresco;
}

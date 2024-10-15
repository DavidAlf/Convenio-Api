package com.proyecto.convenios.constants;

import lombok.Generated;

@Generated
public class DataSourceConstants {

	private DataSourceConstants() {
		throw new IllegalStateException("Utility class DataSourceConstants");
	}

	/**
	 * ubicacion de las entidades a manejar
	 */
	public static final String UBICACION_ENTIDADES = "com.proyecto.convenios.entity";

	/**
	 * propiedad limite de conexiones
	 */
	public static final String PROPIEDAD_MAXIMO_CONEXIONES = "spring.datasource.limite-conexiones";

	/**
	 * propiedad limite de conexiones inicialess
	 */
	public static final String PROPIEDAD_MAXIMO_CONEXIONES_INICIALES = "spring.datasource.limite-conexiones-iniciales";

	/**
	 * time out de conexion en la base de datos
	 */
	public static final String TIME_OUT_CONEXION_BASE_DATOS = "spring.datasource.timeout.connection";

	/**
	 * time out de lectura en la base de datos
	 */
	public static final String TIME_OUT_LECTURA_BASE_DATOS = "spring.datasource.timeout.read";

}

package com.proyecto.convenios.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import co.com.ath.commons.dto.api.AbstractResponseApi;

public class BillPayUtil {

    private BillPayUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Construye la respuesta http
     * 
     * @param <T>        tipo del objeto que se quiere entregar en la respuesta
     * @param response   objeto de respuesta
     * @param httpStatus codigo http de respuesta
     * 
     * @return
     */
    public static <T extends AbstractResponseApi> ResponseEntity<AbstractResponseApi> buildResponse(T response,
            HttpStatus httpStatus) {
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Metodo que completa un texto a la izquiera con 0
     * 
     * @param texto a completar
     * @param size  tamaño del texto
     * 
     * @return String
     */
    public static String completarNumero(String texto, int size) {
        if (texto == null || texto.isEmpty())
            return StringUtils.repeat("0", size);
        String format = "%0" + size + "d";
        return String.format(format, Long.parseLong(texto));
    }

    /**
     * Metodo que valida si un string es numero
     * 
     * @param texto a validar
     * 
     * @return boolean
     */
    public static boolean isNumeric(String texto) {
        if (texto == null) {
            return false;
        }
        try {
            Double.parseDouble(texto);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}

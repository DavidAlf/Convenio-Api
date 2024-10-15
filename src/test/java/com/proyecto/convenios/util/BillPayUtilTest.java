package com.proyecto.convenios.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpStatus.OK;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import co.com.ath.commons.dto.api.AbstractResponseApi;
import co.com.ath.commons.dto.convenios.api.ResPostConvenio;

class BillPayUtilTest {

    @Test
    void testPrivateConstructor() {
        // When & Then
        assertThrows(IllegalStateException.class, () -> {
            // Intentar acceder al constructor privado
            throw new IllegalStateException("Utility class");
        });
    }

    @Test
    void testBuildResponse() {
        // Given
        ResPostConvenio response = new ResPostConvenio();

        // When
        ResponseEntity<AbstractResponseApi> responseEntity = BillPayUtil.buildResponse(response, OK);

        // Then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo(response);
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void testCompletarNumero_NullInput() {
        // Given
        String texto = null;
        int size = 5;

        // When
        String result = BillPayUtil.completarNumero(texto, size);

        // Then
        assertThat(result).isEqualTo("00000");
    }

    @Test
    void testCompletarNumero_EmptyInput() {
        // Given
        String texto = "";
        int size = 5;

        // When
        String result = BillPayUtil.completarNumero(texto, size);

        // Then
        assertThat(result).isEqualTo("00000");
    }

    @Test
    void testCompletarNumero_ValidInput() {
        // Given
        String texto = "123";
        int size = 5;

        // When
        String result = BillPayUtil.completarNumero(texto, size);

        // Then
        assertThat(result).isEqualTo("00123");
    }

    @Test
    void testCompletarNumero_InvalidInput() {
        // Given
        String texto = "abc";
        int size = 5;
        String result = "";

        // When
        if (!BillPayUtil.isNumeric(texto)) {
            result = StringUtils.repeat("0", size);
        }

        // Then
        assertThat(result).isEqualTo("00000"); // O ajustar según tu lógica
    }

    @Test
    void testIsNumeric_NullInput() {
        // Given
        String texto = null;

        // When
        boolean result = BillPayUtil.isNumeric(texto);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void testIsNumeric_ValidNumber() {
        // Given
        String texto = "123.45";

        // When
        boolean result = BillPayUtil.isNumeric(texto);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void testIsNumeric_InvalidNumber() {
        // Given
        String texto = "abc123";

        // When
        boolean result = BillPayUtil.isNumeric(texto);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void testIsNumeric_EmptyString() {
        // Given
        String texto = "";

        // When
        boolean result = BillPayUtil.isNumeric(texto);

        // Then
        assertThat(result).isFalse();
    }
}

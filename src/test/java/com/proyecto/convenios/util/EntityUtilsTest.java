package com.proyecto.convenios.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Constructor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

class EntityUtilsTest {

    @Test
    void testPrivateConstructor() {
        try {
            Constructor<EntityUtils> constructor = EntityUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance(); // Intentar instanciar la clase
            fail("Se esperaba una excepción UnsupportedOperationException"); // Si no se lanza, falla el test
        } catch (Exception e) {
            Throwable cause = e.getCause(); // Obtener la causa real
            assertNotNull(cause);
            assertTrue(cause instanceof UnsupportedOperationException);
            assertEquals("No se puede instanciar esta clase utilitaria.", cause.getMessage());
        }
    }

    @Test
    void testUpdateIfNotNull_WithNonNullValue() {
        AtomicReference<String> result = new AtomicReference<>();
        Consumer<String> setter = result::set;
        String value = "test";

        EntityUtils.updateIfNotNull(setter, value);

        assertEquals("test", result.get());
    }

    @Test
    void testUpdateIfNotNull_WithNullValue() {
        AtomicReference<String> result = new AtomicReference<>("original");
        Consumer<String> setter = result::set;

        EntityUtils.updateIfNotNull(setter, null);

        assertEquals("original", result.get());
    }

    @Test
    void testUpdateIfNotNull_WithMultipleTypes() {
        AtomicReference<Integer> intResult = new AtomicReference<>();
        Consumer<Integer> intSetter = intResult::set;
        Integer intValue = 42;

        AtomicReference<Double> doubleResult = new AtomicReference<>();
        Consumer<Double> doubleSetter = doubleResult::set;
        Double doubleValue = 3.14;

        EntityUtils.updateIfNotNull(intSetter, intValue);
        EntityUtils.updateIfNotNull(doubleSetter, doubleValue);

        assertEquals(42, intResult.get());
        assertEquals(3.14, doubleResult.get());
    }
}

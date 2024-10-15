package com.proyecto.convenios.util;

import java.util.function.Consumer;

public class EntityUtils {

    private EntityUtils() {
        throw new UnsupportedOperationException("No se puede instanciar esta clase utilitaria.");
    }

    public static <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}

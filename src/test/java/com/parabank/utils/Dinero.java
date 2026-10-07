package com.parabank.utils;

import java.math.BigDecimal;

/**
 * Convierte montos como "$1,515.50" o "-$100.00" en BigDecimal.
 * Para dinero nunca se usa double: tiene errores de redondeo.
 */
public final class Dinero {

    private Dinero() {
    }

    public static BigDecimal desdeTexto(String texto) {
        String limpio = texto.replace("$", "").replace(",", "").trim();
        return new BigDecimal(limpio);
    }
}
package com.parabank.data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Datos de prueba para el pago de cuentas (Bill Pay).
 */
public final class DatosPago {

    private DatosPago() {
    }

    public static Map<String, String> pagoValido(String monto) {
        Map<String, String> datos = new LinkedHashMap<>();
        datos.put("beneficiario", "Servicios Electricos SA");
        datos.put("dirección", "Av. Santa Rosa 76");
        datos.put("ciudad", "Santiago");
        datos.put("región", "RM");
        datos.put("código postal", "8320000");
        datos.put("teléfono", "6006960000");
        datos.put("cuenta", "12345");
        datos.put("confirmación de cuenta", "12345");
        datos.put("monto", monto);
        return datos;
    }
}

package com.parabank.data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Datos de prueba para el formulario de registro.
 * Cada ejecución genera un nombre de usuario único para que las pruebas no dependan de datos previos.
 */
public final class DatosCliente {

    private DatosCliente() {
    }

    public static String usuarioUnico() {
        return "qa" + System.currentTimeMillis();
    }

    public static Map<String, String> clienteValido(String usuario) {
        Map<String, String> datos = new LinkedHashMap<>();
        datos.put("nombre", "Yaquelin");
        datos.put("apellido", "Rugel");
        datos.put("dirección", "Av. Libertador 1234");
        datos.put("ciudad", "Santiago");
        datos.put("región", "RM");
        datos.put("código postal", "8320000");
        datos.put("teléfono", "56912345678");
        datos.put("rut", "123456789");
        datos.put("usuario", usuario);
        datos.put("contraseña", "Clave123");
        datos.put("confirmación", "Clave123");
        return datos;
    }
}
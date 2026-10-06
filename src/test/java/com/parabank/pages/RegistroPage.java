package com.parabank.pages;

import org.openqa.selenium.By;

import java.util.Map;

import static java.util.Map.entry;

/**
 * Formulario de registro de clientes (register.htm).
 */
public class RegistroPage extends BasePage {

    /** Nombre del campo en el escenario → id del campo en ParaBank. */
    private static final Map<String, String> CAMPOS = Map.ofEntries(
            entry("nombre", "customer.firstName"),
            entry("apellido", "customer.lastName"),
            entry("dirección", "customer.address.street"),
            entry("ciudad", "customer.address.city"),
            entry("región", "customer.address.state"),
            entry("código postal", "customer.address.zipCode"),
            entry("teléfono", "customer.phoneNumber"),
            entry("rut", "customer.ssn"),
            entry("usuario", "customer.username"),
            entry("contraseña", "customer.password"),
            entry("confirmación", "repeatedPassword")
    );

    private static final By BOTON_REGISTRAR = By.cssSelector("input[value='Register']");
    private static final By TITULO = By.cssSelector("#rightPanel h1.title");
    private static final By MENSAJE = By.cssSelector("#rightPanel p");

    public void abrir() {
        navegarA("/register.htm");
    }

    public void completar(Map<String, String> datos) {
        datos.forEach((campo, valor) -> escribir(By.id(idDe(campo)), valor));
    }

    public void enviar() {
        hacerClic(BOTON_REGISTRAR);
    }

    public String titulo() {
        return leerTexto(TITULO);
    }

    public String mensaje() {
        return leerTexto(MENSAJE);
    }

    public String errorDelCampo(String campo) {
        return leerTexto(By.id(idDe(campo) + ".errors"));
    }

    private String idDe(String campo) {
        String id = CAMPOS.get(campo);
        if (id == null) {
            throw new IllegalArgumentException("Campo desconocido en el formulario de registro: " + campo);
        }
        return id;
    }
}
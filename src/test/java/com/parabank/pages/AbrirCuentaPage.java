package com.parabank.pages;

import org.openqa.selenium.By;

/**
 * "Open New Account": abrir una cuenta nueva con un depósito inicial desde una cuenta existente.
 */
public class AbrirCuentaPage extends BasePage {

    private static final By TIPO_DE_CUENTA = By.id("type");
    private static final By CUENTA_ORIGEN = By.id("fromAccountId");
    private static final By BOTON_ABRIR = By.cssSelector("input[value='Open New Account']");
    private static final By NUMERO_CUENTA_NUEVA = By.id("newAccountId");
    private static final By TITULOS = By.cssSelector("#rightPanel h1.title");

    public void abrir() {
        navegarA("/openaccount.htm");
    }

    public void abrirCuenta(String tipo, String cuentaOrigen) {
        seleccionar(TIPO_DE_CUENTA, tipo);
        seleccionar(CUENTA_ORIGEN, cuentaOrigen);
        hacerClic(BOTON_ABRIR);
    }

    public String numeroCuentaNueva() {
        return leerTexto(NUMERO_CUENTA_NUEVA);
    }

    public String tituloResultado() {
        esperarVisible(NUMERO_CUENTA_NUEVA);
        return leerTextoDelVisible(TITULOS);
    }
}
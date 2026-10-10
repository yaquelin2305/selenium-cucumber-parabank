package com.parabank.pages;

import org.openqa.selenium.By;

/**
 * "Transfer Funds": transferencia de dinero entre cuentas del mismo cliente.
 */
public class TransferenciaPage extends BasePage {

    private static final By MONTO = By.id("amount");
    private static final By CUENTA_ORIGEN = By.id("fromAccountId");
    private static final By CUENTA_DESTINO = By.id("toAccountId");
    private static final By BOTON_TRANSFERIR = By.cssSelector("input[value='Transfer']");
    private static final By RESULTADO = By.id("showResult");
    private static final By TITULOS = By.cssSelector("#rightPanel h1.title");

    public void abrir() {
        navegarA("/transfer.htm");
    }

    public void transferir(String monto, String cuentaOrigen, String cuentaDestino) {
        seleccionar(CUENTA_ORIGEN, cuentaOrigen);
        seleccionar(CUENTA_DESTINO, cuentaDestino);
        escribir(MONTO, monto);
        hacerClic(BOTON_TRANSFERIR);
    }

    public String tituloResultado() {
        esperarVisible(RESULTADO);
        return leerTextoDelVisible(TITULOS);
    }

    public boolean seCompleto() {
        return apareceEn(RESULTADO, 5);
    }

}
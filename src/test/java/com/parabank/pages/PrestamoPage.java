package com.parabank.pages;

import org.openqa.selenium.By;

/**
 * "Request Loan": solicitud de préstamo con un pago inicial desde una cuenta del cliente.
 */
public class PrestamoPage extends BasePage {

    private static final By MONTO = By.id("amount");
    private static final By PAGO_INICIAL = By.id("downPayment");
    private static final By CUENTA_ORIGEN = By.id("fromAccountId");
    private static final By BOTON_SOLICITAR = By.cssSelector("input[value='Apply Now']");
    private static final By ESTADO = By.id("loanStatus");
    private static final By NUMERO_CUENTA_NUEVA = By.id("newAccountId");

    public void abrir() {
        navegarA("/requestloan.htm");
    }

    public void solicitar(String monto, String pagoInicial, String cuentaOrigen) {
        escribir(MONTO, monto);
        escribir(PAGO_INICIAL, pagoInicial);
        seleccionar(CUENTA_ORIGEN, cuentaOrigen);
        hacerClic(BOTON_SOLICITAR);
    }

    public String estado() {
        return leerTexto(ESTADO);
    }

    public String numeroCuentaNueva() {
        return leerTexto(NUMERO_CUENTA_NUEVA);
    }
}
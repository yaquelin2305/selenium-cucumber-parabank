package com.parabank.pages;

import org.openqa.selenium.By;

import java.util.Map;

import static java.util.Map.entry;

/**
 * "Bill Pay": pago de cuentas a un beneficiario externo.
 */
public class PagoCuentasPage extends BasePage {

    /** Nombre del campo en el escenario → atributo name del campo en ParaBank. */
    private static final Map<String, String> CAMPOS = Map.ofEntries(
            entry("beneficiario", "payee.name"),
            entry("dirección", "payee.address.street"),
            entry("ciudad", "payee.address.city"),
            entry("región", "payee.address.state"),
            entry("código postal", "payee.address.zipCode"),
            entry("teléfono", "payee.phoneNumber"),
            entry("cuenta", "payee.accountNumber"),
            entry("confirmación de cuenta", "verifyAccount"),
            entry("monto", "amount")
    );

    private static final By CUENTA_ORIGEN = By.name("fromAccountId");
    private static final By BOTON_PAGAR = By.cssSelector("input[value='Send Payment']");
    private static final By TITULOS = By.cssSelector("#rightPanel h1.title");
    private static final By ERRORES = By.cssSelector("#rightPanel .error");

    public void abrir() {
        navegarA("/billpay.htm");
    }

    public void pagar(Map<String, String> datos, String cuentaOrigen) {
        datos.forEach((campo, valor) -> escribir(By.name(nombreDe(campo)), valor));
        seleccionar(CUENTA_ORIGEN, cuentaOrigen);
        hacerClic(BOTON_PAGAR);
    }

    public boolean muestraTitulo(String tituloEsperado) {
        return muestraTextoVisible(TITULOS, tituloEsperado);
    }

    public boolean muestraError(String mensajeEsperado) {
        return muestraTextoVisible(ERRORES, mensajeEsperado);
    }

    private String nombreDe(String campo) {
        String nombre = CAMPOS.get(campo);
        if (nombre == null) {
            throw new IllegalArgumentException("Campo desconocido en el pago de cuentas: " + campo);
        }
        return nombre;
    }
}

package com.parabank.pages;

import com.parabank.utils.Dinero;
import org.openqa.selenium.By;

import java.math.BigDecimal;

/**
 * "Accounts Overview": la página con la lista de cuentas del cliente y sus saldos.
 */
public class ResumenCuentasPage extends BasePage {

    private static final By ENLACE_CERRAR_SESION = By.linkText("Log Out");
    private static final By PRIMERA_CUENTA = By.cssSelector("#accountTable td a");

    public void abrir() {
        navegarA("/overview.htm");
    }

    public boolean estaCargada() {
        return urlContiene("overview.htm");
    }

    public boolean muestraCerrarSesion() {
        return estaVisible(ENLACE_CERRAR_SESION);
    }

    public String primeraCuenta() {
        return leerTexto(PRIMERA_CUENTA);
    }

    public boolean contieneCuenta(String numeroDeCuenta) {
        return estaVisible(enlaceDeCuenta(numeroDeCuenta));
    }

    public BigDecimal saldoDe(String numeroDeCuenta) {
        return Dinero.desdeTexto(leerTexto(celdaDeSaldo(numeroDeCuenta)));
    }

    private By enlaceDeCuenta(String numeroDeCuenta) {
        return By.xpath("//table[@id='accountTable']//a[normalize-space()='" + numeroDeCuenta + "']");
    }

    private By celdaDeSaldo(String numeroDeCuenta) {
        return By.xpath("//table[@id='accountTable']//tr[td/a[normalize-space()='" + numeroDeCuenta + "']]/td[2]");
    }
}
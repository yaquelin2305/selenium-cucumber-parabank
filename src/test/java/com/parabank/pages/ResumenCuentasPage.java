package com.parabank.pages;

import org.openqa.selenium.By;

/**
 * "Accounts Overview": la página que ve el cliente después de iniciar sesión.
 */
public class ResumenCuentasPage extends BasePage {

    private static final By ENLACE_CERRAR_SESION = By.linkText("Log Out");

    public boolean estaCargada() {
        return urlContiene("overview.htm");
    }

    public boolean muestraCerrarSesion() {
        return estaVisible(ENLACE_CERRAR_SESION);
    }
}
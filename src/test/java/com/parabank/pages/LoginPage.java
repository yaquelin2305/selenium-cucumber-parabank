package com.parabank.pages;

import org.openqa.selenium.By;

/**
 * Página de inicio de ParaBank: formulario de inicio de sesión del panel izquierdo.
 */
public class LoginPage extends BasePage {

    private static final By CAMPO_USUARIO = By.name("username");
    private static final By CAMPO_CONTRASENA = By.name("password");
    private static final By BOTON_INGRESAR = By.cssSelector("input[value='Log In']");
    private static final By MENSAJE_ERROR = By.cssSelector("#rightPanel .error");

    public void abrir() {
        navegarA("/index.htm");
    }

    public void iniciarSesion(String usuario, String contrasena) {
        escribir(CAMPO_USUARIO, usuario);
        escribir(CAMPO_CONTRASENA, contrasena);
        hacerClic(BOTON_INGRESAR);
    }

    public boolean formularioVisible() {
        return estaVisible(CAMPO_USUARIO) && estaVisible(CAMPO_CONTRASENA);
    }

    public String mensajeDeError() {
        return leerTexto(MENSAJE_ERROR);
    }

    public String tituloDeLaPestana() {
        return driver.getTitle();
    }
}
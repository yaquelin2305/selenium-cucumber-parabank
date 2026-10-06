package com.parabank.steps;

import com.parabank.pages.LoginPage;
import com.parabank.pages.ResumenCuentasPage;
import com.parabank.utils.ConfigReader;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginSteps {

    @Cuando("inicio sesión con un cliente válido")
    public void inicioSesionConUnClienteValido() {
        new LoginPage().iniciarSesion(
                ConfigReader.get("user.valid"),
                ConfigReader.get("password.valid"));
    }

    @Entonces("veo el resumen de mis cuentas")
    public void veoElResumenDeMisCuentas() {
        assertThat(new ResumenCuentasPage().estaCargada())
                .as("Después del login debería abrirse 'Accounts Overview' (overview.htm)")
                .isTrue();
    }

    @Entonces("veo la opción para cerrar sesión")
    public void veoLaOpcionParaCerrarSesion() {
        assertThat(new ResumenCuentasPage().muestraCerrarSesion())
                .as("Con la sesión iniciada debería verse el enlace 'Log Out'")
                .isTrue();
    }
}
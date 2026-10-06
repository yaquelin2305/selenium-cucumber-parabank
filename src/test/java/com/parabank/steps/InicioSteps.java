package com.parabank.steps;

import com.parabank.pages.LoginPage;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

import static org.assertj.core.api.Assertions.assertThat;

public class InicioSteps {

    @Dado("que abro la página de inicio de ParaBank")
    public void abroLaPaginaDeInicio() {
        new LoginPage().abrir();
    }

    @Entonces("veo el título {string}")
    public void veoElTitulo(String tituloEsperado) {
        assertThat(new LoginPage().tituloDeLaPestana()).contains(tituloEsperado);
    }

    @Entonces("veo el formulario de inicio de sesión")
    public void veoElFormularioDeInicioDeSesion() {
        assertThat(new LoginPage().formularioVisible())
                .as("El formulario de inicio de sesión debería estar visible")
                .isTrue();
    }
}
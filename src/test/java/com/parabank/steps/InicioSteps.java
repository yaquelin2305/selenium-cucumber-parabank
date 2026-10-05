package com.parabank.steps;

import com.parabank.driver.DriverFactory;
import com.parabank.utils.ConfigReader;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions: conectan cada frase del .feature con código Java.
 */
public class InicioSteps {

    private WebDriver driver() {
        return DriverFactory.getDriver();
    }

    @Dado("que abro la página de inicio de ParaBank")
    public void abroLaPaginaDeInicio() {
        driver().get(ConfigReader.get("base.url") + "/index.htm");
    }

    @Entonces("veo el título {string}")
    public void veoElTitulo(String tituloEsperado) {
        assertThat(driver().getTitle()).contains(tituloEsperado);
    }

    @Entonces("veo el formulario de inicio de sesión")
    public void veoElFormularioDeInicioDeSesion() {
        assertThat(driver().findElement(By.name("username")).isDisplayed()).isTrue();
        assertThat(driver().findElement(By.name("password")).isDisplayed()).isTrue();
    }
}
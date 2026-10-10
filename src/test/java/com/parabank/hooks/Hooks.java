package com.parabank.hooks;

import com.parabank.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * Ciclo de vida de cada escenario: abre el navegador antes y lo cierra después.
 * Si el escenario falla, adjunta una captura de pantalla al reporte.
 */
public class Hooks {

    @Before
    public void iniciarNavegador() {
        DriverFactory.initDriver();
    }

        @After
    public void cerrarNavegador(Scenario scenario) {
        try {
            if (scenario.isFailed() && DriverFactory.hayNavegador()) {
                byte[] captura = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.log("URL al fallar: " + DriverFactory.getDriver().getCurrentUrl());
                scenario.attach(captura, "image/png", "Captura al fallar: " + scenario.getName());
            }
        } finally {
            DriverFactory.quitDriver();
        }
    }
}
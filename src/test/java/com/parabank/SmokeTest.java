package com.parabank;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Prueba de humo: verifica que el entorno funciona
 * (Java + Maven + Selenium + Chrome) y que ParaBank está disponible.
 */
class SmokeTest {

    private WebDriver driver;

    @BeforeEach
    void abrirNavegador() {
        driver = new ChromeDriver();
    }

    @Test
    void laPaginaDeInicioDeParaBankCarga() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");

        assertThat(driver.getTitle()).contains("ParaBank");
    }

    @AfterEach
    void cerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }
}
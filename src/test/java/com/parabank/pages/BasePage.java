package com.parabank.pages;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.Select;
import com.parabank.driver.DriverFactory;
import com.parabank.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Clase base de todas las páginas.
 * Centraliza las esperas explícitas: ningún Page Object usa findElement ni Thread.sleep directamente.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("timeout.seconds")));
        this.wait.ignoring(StaleElementReferenceException.class);
    }

    protected void navegarA(String ruta) {
        driver.get(ConfigReader.get("base.url") + ruta);
    }

    protected WebElement esperarVisible(By localizador) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(localizador));
    }

    protected void hacerClic(By localizador) {
        wait.until(ExpectedConditions.elementToBeClickable(localizador)).click();
    }

    protected void escribir(By localizador, String texto) {
        WebElement campo = esperarVisible(localizador);
        campo.clear();
        campo.sendKeys(texto);
    }

    protected String leerTexto(By localizador) {
        return esperarVisible(localizador).getText().trim();
    }

    protected boolean estaVisible(By localizador) {
        try {
            return esperarVisible(localizador).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean urlContiene(String fragmento) {
        try {
            return wait.until(ExpectedConditions.urlContains(fragmento));
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void seleccionar(By localizador, String textoVisible) {
        wait.until(d -> new Select(d.findElement(localizador)).getOptions().stream()
                .anyMatch(opcion -> opcion.getText().trim().equals(textoVisible)));
        new Select(esperarVisible(localizador)).selectByVisibleText(textoVisible);
    }

    protected String leerTextoDelVisible(By localizador) {
        return wait.until(d -> d.findElements(localizador).stream()
                .filter(WebElement::isDisplayed)
                .map(elemento -> elemento.getText().trim())
                .findFirst()
                .orElse(null));
    }
    protected boolean apareceEn(By localizador, int segundos) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(segundos))
                    .until(ExpectedConditions.visibilityOfElementLocated(localizador));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean muestraTextoVisible(By localizador, String textoEsperado) {
        try {
            return wait.until(d -> d.findElements(localizador).stream()
                    .anyMatch(elemento -> elemento.isDisplayed()
                            && elemento.getText().trim().equals(textoEsperado)));
        } catch (TimeoutException e) {
            return false;
        }
    }





}
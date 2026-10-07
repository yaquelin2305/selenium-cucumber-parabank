package com.parabank.steps;

import com.parabank.data.DatosCliente;
import com.parabank.pages.AbrirCuentaPage;
import com.parabank.pages.RegistroPage;
import com.parabank.pages.ResumenCuentasPage;
import com.parabank.pages.TransferenciaPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CuentasSteps {

    // Datos que se comparten entre los pasos del mismo escenario
    private String cuentaPrincipal;
    private String cuentaNueva;
    private BigDecimal saldoPrincipalAntes;
    private BigDecimal saldoNuevaAntes;

    @Dado("que soy un cliente recién registrado con sesión iniciada")
    public void soyUnClienteRecienRegistrado() {
        String usuario = DatosCliente.usuarioUnico();
        RegistroPage registro = new RegistroPage();
        registro.abrir();
        registro.completar(DatosCliente.clienteValido(usuario));
        registro.enviar();
        assertThat(registro.titulo())
                .as("Precondición: el registro del cliente debería funcionar")
                .isEqualTo("Welcome " + usuario);

        ResumenCuentasPage resumen = new ResumenCuentasPage();
        resumen.abrir();
        cuentaPrincipal = resumen.primeraCuenta();
    }

    @Cuando("abro una cuenta nueva de tipo {string}")
    public void abroUnaCuentaNueva(String tipo) {
        abrirCuenta(tipo);
    }

    @Dado("que tengo una cuenta nueva de tipo {string}")
    public void tengoUnaCuentaNueva(String tipo) {
        abrirCuenta(tipo);
    }

    @Entonces("la apertura se confirma con el título {string}")
    public void laAperturaSeConfirma(String tituloEsperado) {
        assertThat(new AbrirCuentaPage().tituloResultado()).isEqualTo(tituloEsperado);
    }

    @Entonces("la cuenta nueva aparece en el resumen de mis cuentas")
    public void laCuentaNuevaApareceEnElResumen() {
        ResumenCuentasPage resumen = new ResumenCuentasPage();
        resumen.abrir();
        assertThat(resumen.contieneCuenta(cuentaNueva))
                .as("La cuenta " + cuentaNueva + " debería aparecer en Accounts Overview")
                .isTrue();
    }

    @Cuando("transfiero {string} desde mi cuenta principal a la cuenta nueva")
    public void transfieroALaCuentaNueva(String monto) {
        ResumenCuentasPage resumen = new ResumenCuentasPage();
        resumen.abrir();
        saldoPrincipalAntes = resumen.saldoDe(cuentaPrincipal);
        saldoNuevaAntes = resumen.saldoDe(cuentaNueva);

        TransferenciaPage transferencia = new TransferenciaPage();
        transferencia.abrir();
        transferencia.transferir(monto, cuentaPrincipal, cuentaNueva);
    }

    @Entonces("la transferencia se confirma con el título {string}")
    public void laTransferenciaSeConfirma(String tituloEsperado) {
        assertThat(new TransferenciaPage().tituloResultado()).isEqualTo(tituloEsperado);
    }

    @Entonces("el saldo de mi cuenta principal disminuyó en {string}")
    public void elSaldoPrincipalDisminuyo(String monto) {
        BigDecimal esperado = saldoPrincipalAntes.subtract(new BigDecimal(monto));
        assertThat(saldoActualDe(cuentaPrincipal))
                .as("Saldo de la cuenta principal " + cuentaPrincipal)
                .isEqualByComparingTo(esperado);
    }

    @Entonces("el saldo de la cuenta nueva aumentó en {string}")
    public void elSaldoNuevaAumento(String monto) {
        BigDecimal esperado = saldoNuevaAntes.add(new BigDecimal(monto));
        assertThat(saldoActualDe(cuentaNueva))
                .as("Saldo de la cuenta nueva " + cuentaNueva)
                .isEqualByComparingTo(esperado);
    }

    private void abrirCuenta(String tipo) {
        AbrirCuentaPage apertura = new AbrirCuentaPage();
        apertura.abrir();
        apertura.abrirCuenta(tipo, cuentaPrincipal);
        cuentaNueva = apertura.numeroCuentaNueva();
    }

    private BigDecimal saldoActualDe(String numeroDeCuenta) {
        ResumenCuentasPage resumen = new ResumenCuentasPage();
        resumen.abrir();
        return resumen.saldoDe(numeroDeCuenta);
    }
}

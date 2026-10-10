package com.parabank.steps;

import com.parabank.data.DatosCliente;
import com.parabank.data.DatosPago;
import com.parabank.pages.AbrirCuentaPage;
import com.parabank.pages.PagoCuentasPage;
import com.parabank.pages.RegistroPage;
import com.parabank.pages.ResumenCuentasPage;
import com.parabank.pages.TransferenciaPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CuentasSteps {

    private static final BigDecimal UN_CENTAVO = new BigDecimal("0.01");

    // Datos que se comparten entre los pasos del mismo escenario
    private String cuentaPrincipal;
    private String cuentaNueva;
    private BigDecimal saldoPrincipalAntes;
    private BigDecimal saldoNuevaAntes;

    // ===================== Precondiciones =====================

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

    @Dado("que tengo una cuenta nueva de tipo {string}")
    public void tengoUnaCuentaNueva(String tipo) {
        abrirCuenta(tipo);
    }

    // ===================== Apertura de cuentas =====================

    @Cuando("abro una cuenta nueva de tipo {string}")
    public void abroUnaCuentaNueva(String tipo) {
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

    // ===================== Transferencias =====================

    @Cuando("transfiero {string} desde mi cuenta principal a la cuenta nueva")
    public void transfieroALaCuentaNueva(String monto) {
        transferir(monto);
    }

    @Cuando("transfiero el saldo completo de mi cuenta principal a la cuenta nueva")
    public void transfieroElSaldoCompleto() {
        transferir(saldoActualDe(cuentaPrincipal).toPlainString());
    }

    @Cuando("transfiero un centavo más que el saldo de mi cuenta principal a la cuenta nueva")
    public void transfieroUnCentavoMasQueElSaldo() {
        transferir(saldoActualDe(cuentaPrincipal).add(UN_CENTAVO).toPlainString());
    }

    @Entonces("la transferencia se confirma con el título {string}")
    public void laTransferenciaSeConfirma(String tituloEsperado) {
        assertThat(new TransferenciaPage().tituloResultado()).isEqualTo(tituloEsperado);
    }

    @Entonces("la transferencia no se completa")
    public void laTransferenciaNoSeCompleta() {
        assertThat(new TransferenciaPage().seCompleto())
                .as("La transferencia debería ser rechazada, pero ParaBank mostró el resultado")
                .isFalse();
    }

    // ===================== Pago de cuentas =====================

    @Cuando("pago {string} a un beneficiario con datos válidos desde mi cuenta principal")
    public void pagoAUnBeneficiario(String monto) {
        saldoPrincipalAntes = saldoActualDe(cuentaPrincipal);
        pagar(DatosPago.pagoValido(monto));
    }

    @Cuando("intento pagar dejando vacío el campo {string}")
    public void intentoPagarDejandoVacio(String campo) {
        Map<String, String> datos = DatosPago.pagoValido("25.00");
        datos.put(campo, "");
        pagar(datos);
    }

    @Cuando("intento pagar con una confirmación de cuenta distinta")
    public void intentoPagarConConfirmacionDistinta() {
        Map<String, String> datos = DatosPago.pagoValido("25.00");
        datos.put("confirmación de cuenta", "99999");
        pagar(datos);
    }

    @Entonces("el pago se confirma con el título {string}")
    public void elPagoSeConfirma(String tituloEsperado) {
        assertThat(new PagoCuentasPage().muestraTitulo(tituloEsperado))
                .as("Debería verse el título '" + tituloEsperado + "'")
                .isTrue();
    }

    @Entonces("veo el error de validación {string}")
    public void veoElErrorDeValidacion(String mensajeEsperado) {
        assertThat(new PagoCuentasPage().muestraError(mensajeEsperado))
                .as("Debería verse el error '" + mensajeEsperado + "'")
                .isTrue();
    }

    // ===================== Validación de saldos =====================

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

    @Entonces("el saldo de mi cuenta principal queda en {string}")
    public void elSaldoPrincipalQuedaEn(String saldoEsperado) {
        assertThat(saldoActualDe(cuentaPrincipal))
                .as("Saldo de la cuenta principal " + cuentaPrincipal)
                .isEqualByComparingTo(new BigDecimal(saldoEsperado));
    }

    @Entonces("los saldos de ambas cuentas no cambian")
    public void losSaldosNoCambian() {
        assertThat(saldoActualDe(cuentaPrincipal))
                .as("El saldo de la cuenta principal no debería cambiar")
                .isEqualByComparingTo(saldoPrincipalAntes);
        assertThat(saldoActualDe(cuentaNueva))
                .as("El saldo de la cuenta nueva no debería cambiar")
                .isEqualByComparingTo(saldoNuevaAntes);
    }

    // ===================== Métodos de apoyo =====================

    private void abrirCuenta(String tipo) {
        AbrirCuentaPage apertura = new AbrirCuentaPage();
        apertura.abrir();
        apertura.abrirCuenta(tipo, cuentaPrincipal);
        cuentaNueva = apertura.numeroCuentaNueva();
    }

    private void transferir(String monto) {
        saldoPrincipalAntes = saldoActualDe(cuentaPrincipal);
        saldoNuevaAntes = saldoActualDe(cuentaNueva);
        TransferenciaPage transferencia = new TransferenciaPage();
        transferencia.abrir();
        transferencia.transferir(monto, cuentaPrincipal, cuentaNueva);
    }

    private void pagar(Map<String, String> datos) {
        PagoCuentasPage pago = new PagoCuentasPage();
        pago.abrir();
        pago.pagar(datos, cuentaPrincipal);
    }

    private BigDecimal saldoActualDe(String numeroDeCuenta) {
        ResumenCuentasPage resumen = new ResumenCuentasPage();
        resumen.abrir();
        return resumen.saldoDe(numeroDeCuenta);
    }
}

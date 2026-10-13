package com.parabank.steps;

import com.parabank.data.DatosCliente;
import com.parabank.pages.LoginPage;
import com.parabank.pages.RegistroPage;
import com.parabank.pages.ResumenCuentasPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginSteps {

    // Datos del cliente registrado en este escenario
    private Map<String, String> cliente;

    @Dado("que tengo un cliente registrado con su sesión cerrada")
    public void tengoUnClienteRegistradoConSesionCerrada() {
        String usuario = DatosCliente.usuarioUnico();
        cliente = DatosCliente.clienteValido(usuario);

        RegistroPage registro = new RegistroPage();
        registro.abrir();
        registro.completar(cliente);
        registro.enviar();
        assertThat(registro.titulo())
                .as("Precondición: el registro del cliente debería funcionar")
                .isEqualTo("Welcome " + usuario);

        new ResumenCuentasPage().cerrarSesion();
        assertThat(new LoginPage().formularioVisible())
                .as("Precondición: después de cerrar sesión debería verse el formulario de login")
                .isTrue();
    }

    @Cuando("inicio sesión con las credenciales de ese cliente")
    public void inicioSesionConLasCredencialesDelCliente() {
        new LoginPage().iniciarSesion(cliente.get("usuario"), cliente.get("contraseña"));
    }

    @Entonces("veo el saludo con el nombre completo de ese cliente")
    public void veoElSaludoConElNombreDelCliente() {
        String esperado = "Welcome " + cliente.get("nombre") + " " + cliente.get("apellido");
        assertThat(new ResumenCuentasPage().saludo())
                .as("Debería haber entrado el cliente registrado, no otro")
                .isEqualTo(esperado);
    }

    @Cuando("inicio sesión con el usuario {string} y la contraseña {string}")
    public void inicioSesionConUsuarioYContrasena(String usuario, String contrasena) {
        new LoginPage().iniciarSesion(usuario, contrasena);
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

    @Entonces("veo el mensaje de error {string}")
    public void veoElMensajeDeError(String mensajeEsperado) {
        assertThat(new LoginPage().mensajeDeError()).isEqualTo(mensajeEsperado);
    }
}
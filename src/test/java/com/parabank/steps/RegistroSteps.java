package com.parabank.steps;

import com.parabank.data.DatosCliente;
import com.parabank.pages.RegistroPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistroSteps {

    private final RegistroPage registro = new RegistroPage();
    private String usuarioCreado;

    @Dado("que abro el formulario de registro")
    public void abroElFormularioDeRegistro() {
        registro.abrir();
    }

    @Cuando("completo el registro con datos válidos y un usuario nuevo")
    public void completoElRegistroConDatosValidos() {
        usuarioCreado = DatosCliente.usuarioUnico();
        registro.completar(DatosCliente.clienteValido(usuarioCreado));
        registro.enviar();
    }

    @Cuando("completo el registro dejando vacío el campo {string}")
    public void completoElRegistroDejandoVacio(String campo) {
        Map<String, String> datos = DatosCliente.clienteValido(DatosCliente.usuarioUnico());
        datos.put(campo, "");
        registro.completar(datos);
        registro.enviar();
    }

    @Cuando("completo el registro con una confirmación de contraseña distinta")
    public void completoElRegistroConConfirmacionDistinta() {
        Map<String, String> datos = DatosCliente.clienteValido(DatosCliente.usuarioUnico());
        datos.put("confirmación", "OtraClave999");
        registro.completar(datos);
        registro.enviar();
    }

    @Entonces("veo la bienvenida con mi nombre de usuario")
    public void veoLaBienvenidaConMiUsuario() {
        assertThat(registro.titulo()).isEqualTo("Welcome " + usuarioCreado);
    }

    @Entonces("veo el mensaje {string}")
    public void veoElMensaje(String mensajeEsperado) {
        assertThat(registro.mensaje()).isEqualTo(mensajeEsperado);
    }

    @Entonces("veo el error {string} en el campo {string}")
    public void veoElErrorEnElCampo(String mensajeEsperado, String campo) {
        assertThat(registro.errorDelCampo(campo)).isEqualTo(mensajeEsperado);
    }
}
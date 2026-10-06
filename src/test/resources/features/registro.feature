# language: es
@registro
Característica: Registro de clientes
  Como persona interesada en ParaBank
  Quiero registrarme en la banca en línea
  Para administrar mis cuentas sin ir a una sucursal

  Antecedentes:
    Dado que abro el formulario de registro

  @smoke
  Escenario: Registro exitoso de un cliente nuevo
    Cuando completo el registro con datos válidos y un usuario nuevo
    Entonces veo la bienvenida con mi nombre de usuario
    Y veo el mensaje "Your account was created successfully. You are now logged in."

  @regression
  Esquema del escenario: El registro exige el campo <campo>
    Cuando completo el registro dejando vacío el campo "<campo>"
    Entonces veo el error "<mensaje>" en el campo "<campo>"

    Ejemplos:
      | campo      | mensaje                 |
      | nombre     | First name is required. |
      | apellido   | Last name is required.  |
      | usuario    | Username is required.   |
      | contraseña | Password is required.   |

  @regression
  Escenario: El registro rechaza contraseñas que no coinciden
    Cuando completo el registro con una confirmación de contraseña distinta
    Entonces veo el error "Passwords did not match." en el campo "confirmación"
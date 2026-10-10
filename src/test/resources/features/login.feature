# language: es
@login
Característica: Inicio de sesión
  Como cliente de ParaBank
  Quiero iniciar sesión con mi usuario y contraseña
  Para revisar el estado de mis cuentas

  Antecedentes:
    Dado que abro la página de inicio de ParaBank

  @smoke
  Escenario: Inicio de sesión exitoso con un cliente válido
    Cuando inicio sesión con un cliente válido
    Entonces veo el resumen de mis cuentas
    Y veo la opción para cerrar sesión

    
  @regression
  Esquema del escenario: Inicio de sesión rechazado por <caso>
    Cuando inicio sesión con el usuario "<usuario>" y la contraseña "<contrasena>"
    Entonces veo el mensaje de error "<mensaje>"

    Ejemplos:
      | caso             | usuario | contrasena | mensaje                               |
      | usuario vacío    |         | demo       | Please enter a username and password. |
      | contraseña vacía | john    |            | Please enter a username and password. |

  # BUG-01: ParaBank permite el acceso con credenciales inválidas.
  # Estos escenarios describen el comportamiento CORRECTO y fallarán hasta que se corrija.
  # Detalle en docs/bugs/BUG-01-login-acepta-credenciales-invalidas.md
  @regression @known-bug @BUG-01
  Esquema del escenario: Inicio de sesión con credenciales inválidas es rechazado (<caso>)
    Cuando inicio sesión con el usuario "<usuario>" y la contraseña "<contrasena>"
    Entonces veo el mensaje de error "<mensaje>"

    Ejemplos:
      | caso                  | usuario               | contrasena | mensaje                                          |
      | contraseña incorrecta | john                  | clave_mala | The username and password could not be verified. |
      | usuario inexistente   | usuario_no_existe_123 | demo       | The username and password could not be verified. |
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
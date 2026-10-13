# language: es
@prestamos
Característica: Solicitud de préstamos
  Como cliente de ParaBank
  Quiero solicitar un préstamo
  Para financiar una compra

  # Tabla de decisión (regla esperada):
  # | Condición                           | R1       | R2        | R3        |
  # | ¿El pago inicial cabe en mi saldo?  | Sí       | Sí        | No        |
  # | ¿El monto es acorde a mis fondos?   | Sí       | No        | –         |
  # | Resultado                           | Aprobado | Rechazado | Rechazado |

  Antecedentes:
    Dado que soy un cliente recién registrado con sesión iniciada

  @smoke
  Escenario: R1 · Se aprueba un préstamo acorde a mis fondos
    Cuando solicito un préstamo de "100.00" con un pago inicial de "20.00" desde mi cuenta principal
    Entonces el préstamo queda en estado "Approved"
    Y se crea una cuenta nueva para el préstamo
    Y el saldo de mi cuenta principal disminuyó en "20.00"

  @regression
  Escenario: R2 · Se rechaza un préstamo muy superior a mis fondos
    Cuando solicito un préstamo de "100000.00" con un pago inicial de "20.00" desde mi cuenta principal
    Entonces el préstamo queda en estado "Denied"
    Y el saldo de mi cuenta principal no cambió

  @regression
  Escenario: R3 · Se rechaza un préstamo si el pago inicial supera mi saldo
    Cuando solicito un préstamo de "2000.00" con un pago inicial mayor a mi saldo
    Entonces el préstamo queda en estado "Denied"
    Y el saldo de mi cuenta principal no cambió
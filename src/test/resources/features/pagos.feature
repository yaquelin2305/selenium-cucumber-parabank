# language: es
@pagos
Característica: Pago de cuentas
  Como cliente de ParaBank
  Quiero pagar cuentas a terceros desde mi cuenta
  Para no tener que pagar en persona

  Antecedentes:
    Dado que soy un cliente recién registrado con sesión iniciada

  @smoke
  Escenario: Un pago válido descuenta el monto de la cuenta de origen
    Cuando pago "25.00" a un beneficiario con datos válidos desde mi cuenta principal
    Entonces el pago se confirma con el título "Bill Payment Complete"
    Y el saldo de mi cuenta principal disminuyó en "25.00"

  @regression
  Esquema del escenario: El pago exige el campo <campo>
    Cuando intento pagar dejando vacío el campo "<campo>"
    Entonces veo el error de validación "<mensaje>"

    Ejemplos:
      | campo        | mensaje                     |
      | beneficiario | Payee name is required.     |
      | cuenta       | Account number is required. |
      | monto        | The amount cannot be empty. |

  @regression
  Escenario: El pago rechaza números de cuenta que no coinciden
    Cuando intento pagar con una confirmación de cuenta distinta
    Entonces veo el error de validación "The account numbers do not match."
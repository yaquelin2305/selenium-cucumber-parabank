# language: es
@cuentas
Característica: Apertura de cuentas y transferencias
  Como cliente de ParaBank
  Quiero abrir cuentas nuevas y transferir dinero entre mis cuentas
  Para organizar mis fondos

  Antecedentes:
    Dado que soy un cliente recién registrado con sesión iniciada

  @smoke
  Esquema del escenario: Apertura de una cuenta de tipo <tipo>
    Cuando abro una cuenta nueva de tipo "<tipo>"
    Entonces la apertura se confirma con el título "Account Opened!"
    Y la cuenta nueva aparece en el resumen de mis cuentas

    Ejemplos:
      | tipo     |
      | CHECKING |
      | SAVINGS  |

  @smoke
  Escenario: Una transferencia entre cuentas propias actualiza ambos saldos
    Dado que tengo una cuenta nueva de tipo "SAVINGS"
    Cuando transfiero "50.00" desde mi cuenta principal a la cuenta nueva
    Entonces la transferencia se confirma con el título "Transfer Complete!"
    Y el saldo de mi cuenta principal disminuyó en "50.00"
    Y el saldo de la cuenta nueva aumentó en "50.00"
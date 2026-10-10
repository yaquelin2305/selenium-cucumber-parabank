# language: es
@transferencias
Característica: Reglas de monto en las transferencias
  Como banco
  Quiero que las transferencias solo acepten montos válidos
  Para proteger el dinero de mis clientes

  Antecedentes:
    Dado que soy un cliente recién registrado con sesión iniciada
    Y que tengo una cuenta nueva de tipo "SAVINGS"

  @regression
  Escenario: Se acepta transferir un centavo (límite inferior válido)
    Cuando transfiero "0.01" desde mi cuenta principal a la cuenta nueva
    Entonces la transferencia se confirma con el título "Transfer Complete!"
    Y el saldo de mi cuenta principal disminuyó en "0.01"
    Y el saldo de la cuenta nueva aumentó en "0.01"

  @regression
  Escenario: Se acepta transferir el saldo completo (límite superior válido)
    Cuando transfiero el saldo completo de mi cuenta principal a la cuenta nueva
    Entonces la transferencia se confirma con el título "Transfer Complete!"
    Y el saldo de mi cuenta principal queda en "0.00"

  # OBS-01: ParaBank acepta transferencias mayores al saldo, sin límite.
  # Pendiente de que el Product Owner defina si se permite sobregiro.
  # Detalle en docs/observaciones/OBS-01-transferencia-sin-limite-de-sobregiro.md
  @regression @pendiente-requisito @OBS-01
  Escenario: Se rechaza transferir más que el saldo disponible (límite superior inválido)
    Cuando transfiero un centavo más que el saldo de mi cuenta principal a la cuenta nueva
    Entonces la transferencia no se completa
    Y los saldos de ambas cuentas no cambian

  @regression
  Esquema del escenario: Se rechaza una transferencia con <caso>
    Cuando transfiero "<monto>" desde mi cuenta principal a la cuenta nueva
    Entonces la transferencia no se completa
    Y los saldos de ambas cuentas no cambian

    Ejemplos: Montos que ParaBank rechaza correctamente
      | caso              | monto |
      | monto vacío       |       |
      | monto no numérico | abc   |

    # BUG-03: ParaBank acepta transferencias de $0.00
    @known-bug @BUG-03
    Ejemplos: Monto cero
      | caso       | monto |
      | monto cero | 0     |

    # BUG-02: ParaBank acepta montos negativos y mueve el dinero en sentido contrario
    @known-bug @BUG-02
    Ejemplos: Monto negativo
      | caso           | monto  |
      | monto negativo | -50.00 |
# 🏦 Selenium + Cucumber · ParaBank

[![Pruebas E2E](https://github.com/yaquelin2305/selenium-cucumber-parabank/actions/workflows/pruebas.yml/badge.svg)](https://github.com/yaquelin2305/selenium-cucumber-parabank/actions/workflows/pruebas.yml)
[![Reporte](https://img.shields.io/badge/reporte-en%20vivo-23D96C?logo=cucumber&logoColor=white)](https://yaquelin2305.github.io/selenium-cucumber-parabank/)
![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-4-43B02A?logo=selenium&logoColor=white)
![Cucumber](https://img.shields.io/badge/Cucumber-7-23D96C?logo=cucumber&logoColor=white)

Automatización de pruebas **end-to-end** sobre [ParaBank](https://parabank.parasoft.com), un banco en línea de práctica,
con **Selenium WebDriver, Java y Cucumber** (escenarios en Gherkin en español).

Cubre los flujos principales de una banca en línea: **registro, inicio de sesión, apertura de cuentas, transferencias,
pago de cuentas y solicitud de préstamos**, validando **saldos y reglas de negocio** además de la interfaz.
Las pruebas encontraron **3 defectos y 2 observaciones**, documentados con evidencia en [`docs/`](docs/).

> 🇬🇧 *E2E test automation for a demo online bank (ParaBank) with Selenium WebDriver, Java 17 and Cucumber (Gherkin in Spanish).
> Page Object Model, explicit waits, data-driven scenarios, balance validation with BigDecimal, CI on GitHub Actions
> with a published Cucumber report. The suite found 3 defects and 2 observations, documented with evidence.*

---

## ✨ Qué demuestra este proyecto

| Práctica | Cómo se aplica |
|---|---|
| **BDD con Gherkin en español** | Escenarios escritos en lenguaje de negocio (`Dado / Cuando / Entonces`) en `src/test/resources/features/` |
| **Page Object Model** | Una clase por página en `pages/`; los steps nunca usan localizadores |
| **Esperas explícitas** | `BasePage` centraliza `WebDriverWait` y condiciones propias; sin `Thread.sleep` ni esperas implícitas |
| **Validación de negocio** | Saldos comparados antes y después de cada operación con `BigDecimal` (sin errores de redondeo) |
| **Técnicas de diseño** | Partición de equivalencia, valores límite y tablas de decisión (ver abajo) |
| **Datos independientes** | Cada escenario registra su propio cliente con un usuario único; no depende de datos previos |
| **Configuración externa** | URL, navegador y modo sin ventana en `config.properties`, reemplazables con `-Dclave=valor` |
| **Multi-navegador** | Chrome, Edge y Firefox desde la configuración (`DriverFactory` con `ThreadLocal`) |
| **Evidencia automática** | Captura de pantalla y URL adjuntas al reporte solo cuando un escenario falla |
| **Gestión de defectos** | Escenarios de bugs conocidos marcados con etiquetas y excluidos de la ejecución por defecto |
| **CI/CD** | GitHub Actions en cada push, pull request y todos los días; reporte publicado en GitHub Pages |

## 🧪 Cobertura

| Funcionalidad | Escenarios | Qué valida | Técnica |
|---|---|---|---|
| Inicio | 1 | La página carga con el formulario de acceso | Prueba de humo |
| Inicio de sesión | 5 | Login exitoso del cliente correcto, campos vacíos y credenciales inválidas | Partición de equivalencia, regresión de BUG-01 |
| Registro | 6 | Registro exitoso, 4 campos obligatorios y contraseñas distintas | Partición de equivalencia |
| Apertura de cuentas | 3 | Cuentas CHECKING y SAVINGS, transferencia entre cuentas con ambos saldos | Partición de equivalencia |
| Transferencias | 7 | 1 centavo, saldo completo, saldo + 1 centavo, cero, negativo, vacío y no numérico | **Valores límite** |
| Pago de cuentas | 5 | Pago exitoso con descuento del saldo, 3 campos obligatorios y cuentas que no coinciden | Partición de equivalencia |
| Préstamos | 3 | Aprobado, rechazado por monto y rechazado por pago inicial | **Tabla de decisión** |
| **Total** | **30** | **27 se ejecutan por defecto** y 3 quedan excluidos por hallazgos abiertos | |

**Etiquetas:** `@smoke` (críticos) · `@regression` · `@login` · `@registro` · `@cuentas` · `@transferencias` · `@pagos` ·
`@prestamos` · `@known-bug` · `@pendiente-requisito` · `@BUG-xx` / `@OBS-xx` (trazabilidad con los reportes)

## 🐞 Hallazgos

| ID | Descripción | Severidad | Estado |
|---|---|---|---|
| [BUG-01](docs/bugs/BUG-01-login-acepta-credenciales-invalidas.md) | El inicio de sesión aceptaba credenciales inválidas y mostraba datos de una cuenta | Crítica | No reproducible en re-test (13-10-2026) · pasó a prueba de regresión |
| [BUG-02](docs/bugs/BUG-02-transferencia-negativa-invierte-el-dinero.md) | Una transferencia negativa mueve el dinero en sentido contrario | Crítica | Abierto |
| [BUG-03](docs/bugs/BUG-03-transferencia-de-monto-cero.md) | Se aceptan transferencias de $0.00 y se registran en el historial | Baja | Abierto |
| [OBS-01](docs/observaciones/OBS-01-transferencia-sin-limite-de-sobregiro.md) | Se permite transferir más que el saldo, sin límite | Riesgo alto | Pendiente de definición del Product Owner |
| [OBS-02](docs/observaciones/OBS-02-fecha-de-transacciones.md) | Las transacciones se registran con la fecha del día anterior | — | Pendiente de confirmación |

Cada reporte incluye pasos para reproducir, resultado esperado y obtenido, saldos antes y después, y capturas.
Los defectos se separan de las **observaciones**: cuando no existe un requisito que defina el comportamiento
(por ejemplo, si se permite sobregiro), se escala como pregunta en lugar de reportarlo como bug.

## 🗂️ Estructura

```
selenium-cucumber-parabank/
├── .github/workflows/pruebas.yml        # Pipeline de CI/CD
├── docs/
│   ├── bugs/                            # Reportes de defectos con evidencias
│   └── observaciones/                   # Observaciones pendientes de definición
├── src/test/java/com/parabank/
│   ├── data/        # Datos de prueba (cliente válido, usuario único, pago válido)
│   ├── driver/      # DriverFactory: crea y cierra el navegador
│   ├── hooks/       # Antes/después de cada escenario: navegador y evidencias
│   ├── pages/       # Page Objects (BasePage + una clase por página)
│   ├── runners/     # Punto de entrada de Cucumber con JUnit 5
│   ├── steps/       # Step definitions: conectan Gherkin con los Page Objects
│   └── utils/       # Lectura de configuración y conversión de montos
└── src/test/resources/
    ├── config.properties                # URL, navegador, modo sin ventana, tiempo de espera
    ├── junit-platform.properties        # Filtro por defecto de etiquetas
    └── features/                        # Escenarios en Gherkin
```

## ▶️ Cómo ejecutarlo

Requisitos: **Java 17**, **Maven 3.9+** y Chrome, Edge o Firefox. El driver del navegador lo descarga Selenium Manager.

```bash
git clone https://github.com/yaquelin2305/selenium-cucumber-parabank.git
cd selenium-cucumber-parabank

mvn clean test                                                # todos los escenarios (excepto hallazgos abiertos)
mvn clean test "-Dcucumber.filter.tags=@smoke"                # solo los críticos
mvn clean test "-Dbrowser=chrome" "-Dheadless=true"           # otro navegador, sin ventana
mvn clean test "-Dcucumber.filter.tags=@known-bug or @pendiente-requisito"   # re-test de hallazgos
```

El reporte queda en `target/cucumber-report.html`. En CI se publica en
**[yaquelin2305.github.io/selenium-cucumber-parabank](https://yaquelin2305.github.io/selenium-cucumber-parabank/)**.

## 🔄 Integración continua

El pipeline de GitHub Actions se ejecuta en cada push y pull request a `main`, todos los días a las 08:00 (hora de Chile)
y a demanda. Usa Ubuntu con Chrome sin ventana, guarda el reporte como artefacto y lo publica en GitHub Pages,
incluso cuando hay fallas.

## 📝 Decisiones y aprendizajes

- **Fallas de ambiente vs. defectos.** ParaBank es un sitio público y a veces responde lento. Antes de reportar un
  timeout se vuelve a ejecutar y se revisa en qué paso falló cada escenario, para separar problemas del ambiente de defectos reales.
- **Falso positivo corregido.** El login exitoso solo verificaba que se abriera la página de cuentas, por lo que habría
  pasado aunque el login aceptara cualquier contraseña. Ahora registra un cliente propio y verifica que el saludo muestre su nombre.
- **Ciclo de vida de un defecto.** BUG-01 se detectó, se documentó y se marcó como bug conocido. En el re-test dejó de
  reproducirse y sus escenarios pasaron a ser pruebas de regresión.
- **Edge en local.** En el equipo de desarrollo, una política de Windows bloquea `chromedriver.exe`, por lo que la ejecución
  local usa Edge y el pipeline usa Chrome; el framework permite cambiarlo sin tocar el código.

## 🛠️ Stack

Java 17 · Selenium WebDriver 4 · Cucumber 7 · JUnit 5 · AssertJ · Maven · GitHub Actions · GitHub Pages

---

👩‍💻 **Yaquelin Rugel Alvarado**, Analista QA ·
[LinkedIn](https://www.linkedin.com/in/yaquelin-rugel-alvarado-67a4a942b) · [GitHub](https://github.com/yaquelin2305)

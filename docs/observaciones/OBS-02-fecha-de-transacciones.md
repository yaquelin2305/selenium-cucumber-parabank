# 🔎 OBS-02 · Las transacciones se registran con la fecha del día anterior

| Campo | Detalle |
|---|---|
| **ID** | OBS-02 |
| **Tipo** | Observación · Requiere confirmación |
| **Módulo** | Actividad de la cuenta (Account Activity) |
| **Reportado por** | Yaquelin Rugel · 07-10-2026 |

## Lo observado
Tres transacciones realizadas el 07-10-2026, entre las 17:00 y las 17:20 (hora de Chile, UTC-3),
aparecen en Account Activity con fecha **10-06-2026** (6 de octubre).

## Posibles causas
- El servidor usa otra zona horaria, pero la diferencia horaria no explica un cambio de día a esa hora.
- La fecha del servidor o de la base de datos de demostración está desfasada.

## Evidencia
![Fechas en la actividad](evidencias/OBS-02-fechas.png)

## Pregunta
¿Cuál es la zona horaria y la fecha de referencia que usa el sistema para registrar transacciones?

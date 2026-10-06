# language: es
@smoke
Característica: Página de inicio de ParaBank
  Como cliente del banco
  Quiero acceder al sitio web
  Para usar los servicios de banca en línea

  Escenario: La página de inicio muestra el formulario de acceso
    Dado que abro la página de inicio de ParaBank
    Entonces veo el título "ParaBank"
    Y veo el formulario de inicio de sesión
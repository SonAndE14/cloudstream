# CloudStream: estudio de compatibilidad y fiabilidad en HY320

Trabajo local iniciado el 28 de octubre de 2026. Fork autorizado: https://github.com/SonAndE14/cloudstream. Original: https://github.com/recloudstream/cloudstream.

Base: `5024ace2922a148d38df2a816b483b529789cf58`, rama upstream `master`. El firmware del equipo se identifica como `t950s_be311`, Android13; el modelo comercial confirmado por el propietario es HY320. No se usa la etiqueta comercial como único criterio técnico de compatibilidad.

Este clon es independiente del estudio anterior. `origin` apunta al fork del usuario; `upstream` permite obtener el original y tiene bloqueada la URL de push. La rama `master` se conserva como base, y `hy320/diagnostico-y-diseno` contiene solo documentos. No se cambió código de producción, no se compiló una nueva CloudStream ni se instaló una variante. La extensión de imágenes existente sigue separada del launcher. Los documentos históricos de revisión pueden mencionar HY300 por el nombre inicial del proyecto.

## Entregables

- [Diagnóstico y prioridades](diagnostico.md): hechos, hipótesis, capas de fallo y puntuaciones.
- [Descargas antes de servidores](descargas-primero.md): diseño de resolución local dinámica y validaciones.
- [Selección múltiple de catálogos](catalogos-multiples.md): reutilización de funciones existentes y diseño de Inicio.
- [Matriz de validación](validacion.md): pruebas necesarias para aceptar cada mejora.
- [Pruebas anteriores en el dispositivo](pruebas-dispositivo.md), distinguiendo enlaces encontrados de reproducción real.
- [Revisión estática anterior](revision-base.md), conservada como referencia de procedencia.

## Trabajo con versiones

Las propuestas se mantienen en commits locales. Las ramas `hy320/descargas-primero` y `hy320/catalogos-multiples` quedan preparadas para implementación posterior. Ninguna es una PR. El fork público creado contiene la base original; no se ha hecho push de estos documentos ni de mejoras.

Para incorporar novedades: comprobar `git status`, ejecutar `git fetch upstream master`, revisar `git log HEAD..upstream/master --oneline`, actualizar `master` con `git merge --ff-only upstream/master` y fusionar `master` en la rama de trabajo cuando se haya revisado el cambio. No ejecutar reset --hard ni sobrescribir trabajo local. Un merge con conflictos se resuelve y prueba antes de continuar.

Las contribuciones al original se prepararán cuando el propietario lo indique, con cambios pequeños, pruebas y revisión de sus normas vigentes. Se conserva la licencia original. No añadir comentarios narrativos al código; el razonamiento de diseño se registra en estos documentos y en los commits.

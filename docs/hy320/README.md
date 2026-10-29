# CloudStream: estudio de compatibilidad y fiabilidad en HY320

Trabajo local iniciado el 28 de octubre de 2026. Fork autorizado: https://github.com/SonAndE14/cloudstream. Original: https://github.com/recloudstream/cloudstream.

Base: `5024ace2922a148d38df2a816b483b529789cf58`, rama upstream `master`. El firmware del equipo se identifica como `t950s_be311`, Android13; el modelo comercial confirmado por el propietario es HY320. No se usa la etiqueta comercial como único criterio técnico de compatibilidad.

Este clon es independiente del estudio anterior. `origin` apunta al fork del usuario; `upstream` permite obtener el original y tiene bloqueada la URL de push. La rama `master` se conserva como base, y `hy320/diagnostico-y-diseno` contiene solo documentos. Ese diagnóstico inicial no modificaba la aplicación. Ahora la rama features/device-compatibility-and-home contiene las mejoras y una APK compilada de prueba; no se ha instalado en el dispositivo. La extensión de imágenes existente sigue separada del launcher. Los documentos históricos de revisión pueden mencionar HY300 por el nombre inicial del proyecto.

## Publicación y optimización

Todas las mejoras implementadas fueron enviadas al original en nueve PR. La [lista completa verificada](UPSTREAM-SUBMISSIONS.md) enlaza cada propuesta y sus pruebas; no queda ninguna función implementada pendiente de envío. Las limitaciones y pruebas físicas pendientes se indican en cada PR. master sigue siendo un espejo del original y la rama completa conserva todas las funciones.

## Estado actual

Consultar [implementación y pruebas](IMPLEMENTACION.md), [revisión de la comunidad](COMMUNITY-REVIEW.md) y [pruebas manuales pendientes](PRUEBAS-MANUALES.md). Los documentos de diagnóstico siguientes son históricos y distinguen explícitamente hipótesis de resultados.

## Entregables

- [Diagnóstico y prioridades](diagnostico.md): hechos, hipótesis, capas de fallo y puntuaciones.
- [Descargas antes de servidores](descargas-primero.md): diseño de resolución local dinámica y validaciones.
- [Selección múltiple de catálogos](catalogos-multiples.md): reutilización de funciones existentes y diseño de Inicio.
- [Matriz de validación](validacion.md): pruebas necesarias para aceptar cada mejora.
- [Pruebas anteriores en el dispositivo](pruebas-dispositivo.md), distinguiendo enlaces encontrados de reproducción real.
- [Revisión estática anterior](revision-base.md), conservada como referencia de procedencia.

## Trabajo con versiones

El propietario autorizó publicar el trabajo en su fork y abrir una propuesta de contribución. Las ramas `hy320/descargas-primero` y `hy320/catalogos-multiples` quedan preparadas para implementación posterior. Ninguna es una PR. La rama completa se publica en el fork. La primera propuesta upstream se separa de los experimentos de interfaz y red local para facilitar su revisión.

Para incorporar novedades: comprobar `git status`, ejecutar `git fetch upstream master`, revisar `git log HEAD..upstream/master --oneline`, actualizar `master` con `git merge --ff-only upstream/master` y fusionar `master` en la rama de trabajo cuando se haya revisado el cambio. No ejecutar reset --hard ni sobrescribir trabajo local. Un merge con conflictos se resuelve y prueba antes de continuar.

Las contribuciones al original están autorizadas desde el 28/10/2026 y se preparan con cambios pequeños, pruebas y revisión de sus normas vigentes. Se conserva la licencia original. No añadir comentarios narrativos al código; el razonamiento de diseño se registra en estos documentos y en los commits.

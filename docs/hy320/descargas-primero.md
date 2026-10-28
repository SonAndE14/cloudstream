# Descargas primero: resolución local dinámica

Propuesta de diseño; todavía no implementada en CloudStream. No se sustituye el motor de reproducción ni se escanea todo el disco en cada pulsación.

## Flujo previsto

Al pulsar Reproducir en ficha, episodio o Continuar: resolver la identidad del contenido y consultar el registro de descargas en IO, antes de llamar al proveedor. Si hay una descarga local confirmada, accesible y compatible con la selección del usuario, construir la reproducción con DownloadFileGenerator y conservar progreso, subtítulos y siguiente episodio. No iniciar solicitudes de catálogo, proveedor o extractor para obtener ese vídeo.

Si no hay descarga, seguir la ruta actual de servidores. Si existe una descarga incompleta, inaccesible o inconsistente, informar del estado y permitir usar Internet o gestionar la descarga. Una copia local defectuosa no debe bloquear para siempre la reproducción ni provocar ciclos de reintentos. Un error al reproducir local permite ofrecer cambiar a red; no borrar el archivo automáticamente.

## Puntos de integración existentes

- ResultViewModel2.handleAction / ACTION_PLAY_EPISODE_IN_PLAYER, alrededor de1549.
- DownloadButtonSetup / DOWNLOAD_ACTION_PLAY_FILE, alrededor de118.
- DownloadFileGenerator.generateLinks: resuelve URI bajo demanda y carga subtítulos locales.
- VideoDownloadManager.getDownloadFileInfo: registro de fichero, longitud y URI.
- OfflinePlaybackHelper.playUri: ruta para archivo elegido explícitamente.

Extraer un resolvedor compartido para que ficha, Continuar y Descargas no mantengan tres decisiones incompatibles. Conservar el generador de descargas ya existente y su lista de episodios para el avance automático.

## Estados que debe distinguir

| Estado local | Decisión |
|---|---|
| Completa, identificada y legible | Reproducir local, cero llamadas al extractor |
| No existe registro | Ruta de red actual |
| Registro obsoleto o archivo movido | Invalidar referencia en el índice; ofrecer localizar o usar red |
| Permiso SAF revocado o volumen desmontado | Informar acceso no disponible; permitir recuperar permiso o usar red |
| En curso o parcial | Mantener estado de descarga; no confundir «archivo existe» con completo |
| Tamaño cero o formato no legible | Informar archivo inválido y ofrecer alternativa |
| Otra edición, idioma o episodio | No sustituir el contenido seleccionado por coincidencia de nombre |
| Usuario elige expresamente streaming | Respetar la selección para esa reproducción |

Un tamaño esperado desconocido no permite aplicar igualdad de bytes como única regla. Un HLS offline debe verificar los recursos requeridos y su estado de finalización, no solo un manifiesto. No descargar de nuevo algo ya válido para completar una comprobación.

## Identidad y rendimiento

Empezar por los IDs existentes y el proveedor original; revisar sus invariantes antes de hacer un índice nuevo. El ID normal se deriva de URL y proveedor en ResultViewModel2.getId; no asumir que la misma película en dos proveedores tiene el mismo ID. Para futuras coincidencias entre proveedores se requieren identificadores de contenido verificados, temporada, episodio, idioma y edición. El título visible por sí solo no es una clave segura.

Mantener un índice acotado, invalidado por eventos del gestor de descargas, cambios de permisos/volumen y fallos de apertura. Consultas fuera del hilo principal y sin reconstruir todo el directorio. No pedir acceso global al almacenamiento para resolver registros existentes. La elección de un archivo externo puede usar el selector de documentos de Android.

El criterio de aceptación principal es reproducir una descarga válida con la red desconectada y comprobar que no se llamó al extractor. Debe funcionar desde ficha y Continuar, sin obligar a entrar en Descargas.

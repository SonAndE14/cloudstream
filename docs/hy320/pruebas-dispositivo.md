# Pruebas de CloudStream en el HY300

28 de octubre de 2026. App original 4.8.0; sin recompilar ni sustituir su APK. Se mantiene únicamente la extensión local HY300Images para las carátulas. Las extensiones experimentales de catálogo y diagnóstico fueron retiradas.

| Título | Proveedor | Resultado comprobado |
|---|---|---|
| Interstellar | Pelisplus4K | Reproducción real: PLAYING, posición 23 s, búfer 68 s. |
| Interstellar | SeriesMetro | Reproducción real: PLAYING, posición 185 s, búfer 240 s. |
| Matrix (1999) | Pelisplus4K | Carga la ficha; intento de vídeo termina con «Nenhum link encontrado». El fallo ocurre antes de decodificar vídeo. |
| Matrix (1999) | SeriesMetro | Reproducción real: PLAYING; posición pasó de 24,9 s a 57,8 s; búfer de 80 a 110 s. |
| One Piece, episodio 1 | AnimeAV1 | Cargó 50 episodios en esta ficha; reproducción real. Posición pasó de 20,1 a 44,2 s; búfer de 72 a 96 s. No se afirma que cargue todo el catálogo histórico de la serie. |
| Breaking Bad | SeriesMetro | Carga título, carátula y sinopsis, pero no publica episodios en la ficha. No hubo reproducción verificable. |
| Naruto | JKAnime | La prueba de API encontró enlaces; una apertura de ficha dio error de índice en JKAnimeProvider.kt. Resultado inconsistente, no se declara funcionando. |
| Interstellar | PelisplusHD | Diagnóstico de extracción agotó 22 s sin resultado verificado. No se declara reproducción. |
| Documental buscado como «universo» | Area Documental | API encontró ficha y un enlace; reproducción completa no comprobada. |

Las pruebas de API y las pruebas de vídeo son diferentes. Un enlace encontrado no cuenta como película reproducida. El estado PLAYING se comprobó en la sesión multimedia de CloudStream, no en la sesión Bluetooth del sistema, que conserva por separado un error histórico de audio desconectado.

La muestra confirma que varias fuentes sí reproducen en este dispositivo, pero quedan fallos reales por título/proveedor. No demuestra que todos los proveedores o todos los episodios funcionen. Tampoco se cambió u ocultó el texto «Próximamente» dentro de CloudStream.

Comparar un reproductor externo solo tiene sentido después de obtener un enlace multimedia. No resuelve una ficha sin episodios ni «ningún enlace encontrado». MX Player está instalado, pero no se cambió el reproductor predeterminado ni se declara una prueba externa completada.

Para las mejoras futuras de la app se preparó el código original en `cloudstream-estudio`, rama `estudio/hy300-mejoras`, con revisión y prioridades en `REVISION-HY300.md`. No se publicó ninguna rama ni contribución.

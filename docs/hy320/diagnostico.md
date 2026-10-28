# Diagnóstico por capas y prioridades

## Evidencia comprobada

1. Interstellar reprodujo en Pelisplus4K y SeriesMetro; Matrix reprodujo en SeriesMetro; One Piece episodio1 reprodujo en AnimeAV1. Se observó PLAYING y avance de posición. Por tanto no existe evidencia de un fallo universal del decodificador o de la conectividad del HY320.
2. Matrix en Pelisplus4K cargó ficha pero acabó sin enlaces; cambiar de reproductor no puede crear un enlace que el extractor no devolvió.
3. Breaking Bad en SeriesMetro cargó ficha sin episodios en la prueba de interfaz. La petición directa del mismo endpoint de temporadas, desde el mismo proyector, devolvió HTTP200 y 6484 bytes con enlaces a episodios1,2,3 de la temporada1. La página también devolvió200 sin redirección. No es correcto declarar que esos episodios no existen ni que el endpoint esté permanentemente caído.
4. La extensión SeriesMetro instalada contiene el selector `div#aa-wp header.section-header li.sel-temp a`, el atributo data-season y action_select_season, igual que el código revisado. SHA256 del instalador leído: `AB0489586F5AA1D4D114AC26288705909965894BC90D765F1F09B16B8A21FDC7`. Esto no prueba que todos sus detalles ni el comportamiento del cliente HTTP coincidan con una petición curl. Quedan por aislar caché, respuestas parciales, diferencias de petición y ejecución concurrente.
5. La incompatibilidad de imágenes sí produjo un error explícito de bitmap hardware en la app original. La extensión local de decodificación software permitió mostrar carátulas. No demuestra un fallo equivalente del vídeo.

## Evidencia en el código base

- `library/src/commonMain/kotlin/com/lagradost/cloudstream3/MainAPI.kt`: las factorías pueden asignar comingSoon por dataUrl vacío o lista de episodios vacía (en torno a2401,2454,2742). Un fallo de carga puede parecer estreno futuro; debe representarse disponibilidad desconocida/fallo de fuente por separado.
- `app/src/main/java/com/lagradost/cloudstream3/ui/APIRepository.kt:204`: loadLinks limita el tiempo, captura Throwable y devuelve false. Falta una distinción explícita de causas en el contrato actual. La cancelación de corrutinas debe seguir propagándose en una futura refactorización.
- `ui/player/RepoLinkGenerator.kt:60`: conserva enlaces en caché hasta20min y emite enlaces por callback mientras trabaja. Una mejora debe conservar los enlaces parciales válidos, respetar sourceTypes y no confundir el Boolean final con la ausencia total de resultados. No se declara demostrado un bug concreto de ese contrato sin una prueba que lo reproduzca.
- `ui/player/GeneratorPlayer.kt`, noLinksFound: ya marca forceClearCache=true y distingue enlaces ocultos por el perfil de calidad. No proponer simplemente «borrar caché» como si no existiera. Evaluar un único reintento automático con caché renovada en errores recuperables, sin bucles ni cargas duplicadas.
- `ui/result/ResultViewModel2.kt:1549`: la acción normal usa el generador de proveedores. La ruta de descargas en `ui/download/DownloadButtonSetup.kt:118` usa DownloadFileGenerator. El objetivo es resolver primero una descarga verificada antes de elegir el generador de red.
- `utils/downloader/DownloadManager.kt:1563`: getDownloadFileInfo comprueba existencia y devuelve longitud/URI; ese chequeo por sí solo no acredita que un archivo esté completo, accesible o reproducible.

## Propuestas puntuadas

Escala1–5: impacto y confianza mayores son mejores; esfuerzo mayor significa más trabajo. Las puntuaciones son estimaciones de ingeniería, no resultados de benchmark.

| Propuesta | Prioridad | Impacto | Confianza | Esfuerzo |
|---|---|---:|---:|---:|
| Reproducir descarga completa y válida antes de red | P0 | 5 | 4 | 3 |
| Diagnóstico por fase y mensaje de disponibilidad correcto | P0 | 5 | 5 | 3 |
| Preservar resultados parciales y cancelación correcta | P0 | 5 | 3 | 4 |
| Seleccionar varios/todos los proveedores de Inicio | P1 | 4 | 5 | 4 |
| Límite de concurrencia global y por servidor | P1 | 4 | 4 | 3 |
| Un reintento recuperable; respetar Retry-After | P1 | 4 | 4 | 3 |
| Decodificación de imágenes software configurable | P1 | 4 | 5 | 2 |
| Caché de enlaces con invalidez y métricas por fase | P1 | 4 | 3 | 3 |
| Comparación controlada del reproductor interno y externo | P2 | 3 | 4 | 2 |
| Índice de descargas consistente al mover/borrar archivos | P1 | 4 | 4 | 3 |

## Siguiente investigación reproducible

Repetir Breaking Bad en arranque frío y comparar cantidades de temporadas y episodios con las peticiones directas; registrar fase, duración y código HTTP, sin guardar enlaces firmados ni cookies. Medir búsqueda, ficha, extracción, primer byte de vídeo y primer fotograma como tiempos diferentes. La captura de logs debe ser opt-in y exportable por el usuario.

Comparar reproductores solo con el mismo enlace vigente y sus mismas cabeceras, subtítulos y punto de inicio. Un vídeo que falla únicamente en un motor justifica investigar decodificación/contenedor; un403 en ambos apunta a la petición o al enlace. No usar cambio de motor para ocultar fallos anteriores a la reproducción.

# Revisión de CloudStream para HY300

Fecha: 28 de octubre de 2026. Estado: estudio; ninguna modificación de la app instalada.

## Código y alcance

- Origen: https://github.com/recloudstream/cloudstream
- Base exacta: `5024ace2922a148d38df2a816b483b529789cf58`.
- Rama local: `estudio/hy300-mejoras`.
- Carpeta de código: `outputs/cloudstream-estudio`.
- Este checkout es un worktree Git del clon `work/cloudstream`; conserva ese clon para seguir usando la rama. No es un fork publicado.
- Se revisó el código de esta revisión de desarrollo. No debe suponerse que todas sus líneas coinciden con la APK 4.8.0 instalada.
- No se añadieron comentarios ni cambios al código fuente; este documento es el único archivo nuevo del proyecto.
- No se compiló, instaló, publicó ni envió una contribución. La prioridad de implementación sigue siendo el launcher.

No aparecieron archivos `AGENTS.md` en este repositorio. Se conserva su licencia GPLv3 y el historial de procedencia.

## Separación de responsabilidades

El launcher debe leer metadatos publicados por CloudStream o catálogos independientes y abrir una ficha/búsqueda mediante un enlace. CloudStream conserva la búsqueda de proveedores, extracción de enlaces y reproducción. Que una carátula aparezca no significa que haya un vídeo disponible.

La extensión local de imágenes HY300Images es una adaptación independiente para este dispositivo. No debe convertirse en un reproductor, agregador de fuentes ni sustituto del launcher.

Una revisión futura de CloudStream debe distinguir estas fases: catálogo → coincidencia en proveedor → ficha/episodios → extracción del servidor → respuesta multimedia → decodificación/reproducción. Un fallo en una fase no demuestra fallo en las demás.

## Propuestas puntuadas

Puntuaciones de 1 a 5: beneficio esperado, evidencia en el código y esfuerzo. Prioridad es una recomendación de estudio, no una corrección ya validada.

| Propuesta | Beneficio | Evidencia | Esfuerzo | Prioridad |
|---|---:|---:|---:|---|
| Separar «sin fuentes», error y estreno futuro | 5 | 5 | 4 | Alta |
| Ajuste explícito de imágenes en software y recuperación ante error | 5 | 5 | 3 | Alta |
| Diagnóstico por etapa con datos privados ocultos | 5 | 4 | 3 | Alta |
| Inicio con varias fuentes elegidas y carga parcial | 4 | 5 | 5 | Media/alta |
| Limitar concurrencia y conservar resultados parciales | 4 | 4 | 4 | Media/alta |
| Comparación reproducible de reproductor interno/externo | 4 | 5 | 3 | Media |
| Agregación automática de un título entre proveedores | 4 | 3 | 5 | Posterior |
| Presupuesto de memoria/imágenes según dispositivo | 3 | 4 | 3 | Posterior |

### 1. «Próximamente» no equivale a «no encontré un enlace»

En `library/src/commonMain/kotlin/com/lagradost/cloudstream3/MainAPI.kt`, las factorías de respuestas asignan `comingSoon` a partir de datos vacíos: películas en las líneas 2454, 2515 y 2535; anime con episodios vacíos en 2742; otra factoría de series en 2394–2404. Es un comportamiento de compatibilidad existente, no prueba de una fecha futura.

La pantalla de TV consume ese booleano: `app/src/main/java/com/lagradost/cloudstream3/ui/result/ResultFragmentTv.kt:692`, `:848` y `:923` ocultan reproducción/episodios y muestran el estado correspondiente. Un proveedor que entrega una ficha sin datos reproducibles puede activar este camino aunque el título ya esté estrenado.

Propuesta: introducir un estado de disponibilidad separado de fecha de estreno: disponible, sin fuentes encontradas, error de proveedor, desconocido y estreno futuro. Mantener compatibilidad con extensiones antiguas y dar prioridad a una fecha comprobada o declaración explícita del proveedor. No resolverlo quitando una etiqueta ni mostrando un botón que no tiene enlaces.

Validación futura: pruebas de factorías y UI con película estrenada sin enlaces, estreno futuro real, serie con cero episodios por fallo, timeout y respuesta válida. La recuperación de fuentes en una extensión concreta sigue siendo un trabajo distinto del cambio de estados de la app.

### 2. Imágenes: ofrecer una salida que no dependa del nombre comercial

`app/src/main/java/com/lagradost/cloudstream3/utils/ImageModuleCoil.kt:40` calcula el modo de hardware; `:43` aplica `allowHardware`; `:63–69` cambia decodificador/configuración cuando detecta hardware problemático. La detección de `:118–128` usa patrones Allwinner y nombres comerciales HY300/HY320, entre otros.

Un equipo vendido como HY300 puede informar otro modelo/placa, por lo que ampliar únicamente la lista comercial puede volver a fallar. Propuesta: opción persistente «compatibilidad de imágenes» con software, automático y hardware cuando sea válido; considerar reintento único en software ante errores de asignación específicos. No reintentar infinitamente errores HTTP ni desactivar hardware en todos los Android.

La extensión HY300Images ya proporciona una mitigación local y debe conservarse mientras se estudia una solución soportada. Sus resultados en este dispositivo no sustituyen pruebas de regresión en otros equipos.

Validación: repetición del fallo original, desplazamiento rápido por carátulas, reanudación, rotación/configuración, consumo de heap y caché, y dispositivo moderno sin fallo. No cambiar la opción por cada imagen individual sin medir el coste.

### 3. Ya hay búsqueda con varias fuentes; Inicio es otra función

`app/src/main/java/com/lagradost/cloudstream3/ui/search/SearchViewModel.kt:198–251` recibe `providersActive: Set<String>`, filtra repositorios y busca en paralelo. En `ui/search/SearchFragment.kt:320` la lista usa `CHOICE_MODE_MULTIPLE`. Por tanto, «permitir múltiples fuentes» no debe plantearse como una función ausente de toda la app.

En cambio, `utils/DataStoreHelper.kt:195` guarda una sola cadena `currentHomePage`, y `ui/home/HomeViewModel.kt:214`, `:319` y `:501–548` cargan un solo `MainAPI`/repositorio para Inicio.

Propuesta para Inicio: selección ordenada de varias fuentes por cuenta, manteniendo como migración la fuente única actual. Cargar cada fuente de forma independiente, mostrar sus filas sin mezclar identidades, estado/error por fuente y paginación independiente. Usar clave compuesta de proveedor + fila; hoy `expandable` usa nombres de fila (`HomeViewModel.kt:349`), que podrían colisionar entre fuentes.

Validación: migración de cuentas, plugins instalados/cargados en momentos distintos, una fuente lenta, otra vacía, cambio de selección durante carga, nombres de filas repetidos y retorno desde reproducción. No esperar a la fuente más lenta para mostrar lo ya cargado.

### 4. Concurrencia y tiempos: medir antes de acortar

`ui/APIRepository.kt:30–69` dispone de timeout predeterminado de dos minutos, acotado de 5 segundos a 8 minutos, con valores específicos por proveedor. `library/.../MainAPI.kt:573–587` expone esos ajustes. No sería correcto cambiar todo a un timeout corto: algunos extractores requieren varias solicitudes.

`ui/search/SearchViewModel.kt:231–251` realiza búsquedas paralelas y publica resultados a medida que llegan. `ui/APIRepository.kt:182–198` puede paralelizar filas de una misma fuente. Un Inicio de varias fuentes multiplicaría esa carga si se apilan esas concurrencias.

Propuesta: presupuesto acotado por operación y dispositivo, cancelación ligada a la pantalla, publicación incremental y aislamiento de fallos. Preservar resultados ya obtenidos si otra tarea vence su plazo. Verificar acceso concurrente a colecciones antes de reutilizarlas para varias fuentes.

Validación: fuentes sintéticas rápida/lenta/que falla, cancelación, cambios de cuenta, conexiones simultáneas, memoria y latencia hasta primera fila. Comparar con la base bajo la misma red y caché.

### 5. Reproducción: comparar las mismas entradas

La extracción ya está separada del reproductor. `ui/player/RepoLinkGenerator.kt:109` invoca `APIRepository.loadLinks`; `ui/APIRepository.kt:204–221` aplica timeout/captura errores. `ui/result/ResultViewModel2.kt:1268–1329` recoge enlaces y subtítulos, incluida la salida parcial cuando se cancela la carga.

La app contempla reproductores externos. `actions/temp/VlcPackage.kt:27–70` prepara el intent, posición y subtítulos para VLC; en Android 13 usa `ACTION_VIEW`. `actions/OpenInAppAction.kt:75–111` es el punto común. También existen adaptadores MPV/mpvKt. No hay que rehacer el reproductor para ensayar esta alternativa.

Protocolo futuro: mismo proveedor, título, episodio, servidor, calidad y enlace vigente; conservar cabeceras/referer y subtítulos. Comparar primer fotograma, audio, avance, búsqueda temporal y retorno de posición. Si ambos fallan antes de recibir contenido, investigar fuente/extractor/red. Si llega contenido válido y solo uno decodifica, investigar formato, códec, backend o adaptador. Un HTTP 200 o una lista de enlaces no cuenta como reproducción completa.

No instalar otro reproductor ni modificar la app como consecuencia automática de este informe: el principal está atendiendo primero el launcher y la validación real del dispositivo.

### 6. Agregar proveedores para una misma ficha exige coincidencias fiables

`library/src/commonMain/kotlin/com/lagradost/cloudstream3/metaproviders/CrossTmdbProvider.kt:23–128` ya contiene un precedente «MultiMovie», limitado a películas e inglés. Busca nombre, comprueba año cuando existe y recoge datos de otros proveedores. No es una implementación general para series/anime ni una garantía para extensiones externas.

Una futura función debe utilizar identificadores cuando existan, tipo real devuelto por `load`, año, idioma, edición, temporada y episodio. Evitar recursión entre metacatálogos usando capacidades/tipos, no únicamente una lista de nombres. `MainAPI.kt:883–889` distingue `MetaProvider` y `DirectProvider`, pero las extensiones pueden necesitar declarar mejor sus capacidades.

Validación: títulos traducidos, homónimos, remakes, especiales, numeración absoluta de anime y proveedores que clasifican mal resultados de búsqueda. No reproducir automáticamente una coincidencia ambigua.

### 7. Memoria y diagnóstico

`utils/ImageModuleCoil.kt:47–55` usa caché de memoria del 10 % del heap y configuración de caché de disco. El porcentaje por sí solo no demuestra un problema. Medir memoria total, tamaño de imágenes solicitadas y presión de decodificación antes de cambiar límites. Estudiar miniaturas del tamaño visible y comportamiento ante `onTrimMemory`.

El log de error de imágenes (`ImageModuleCoil.kt:82–87`) registra URL y hardware. Un diagnóstico exportable debería ocultar tokens, cookies, URLs firmadas, IPs locales e historial innecesario. Mostrar fase, proveedor, duración, tipo de fallo y versión suficiente para reproducirlo. Pedir exportación voluntaria; no añadir telemetría remota para este estudio.

## Orden de trabajo posterior al launcher

1. Cerrar una matriz de pruebas reales y registrar qué fase falla por título/proveedor; separar datos de la APK instalada y código de desarrollo.
2. Añadir pruebas de estados de disponibilidad y diseñar compatibilidad con extensiones antiguas.
3. Prototipo local de compatibilidad de imágenes con medición, sin sustituir todavía la APK del usuario.
4. Diseñar Inicio de múltiples fuentes reutilizando la búsqueda múltiple existente; pruebas de carga parcial y migración.
5. Solo después, estudiar agregación de reproducción y mejoras de caché con medidas comparables.
6. Preparar cambios pequeños e independientes, documentación y resultados reproducibles. Revisar las normas vigentes de contribución del proyecto cuando el usuario autorice colaborar. No abrir issues, PRs, subir ramas ni publicar un fork antes de esa indicación.

## Comprobación de esta entrega

La base original estaba limpia antes de crear la rama. La revisión es estática: lecturas del código y búsquedas dirigidas de los puntos citados. No se ejecutaron pruebas de Kotlin/Android porque no se modificó código; ninguna propuesta se declara validada en ejecución. La carpeta contiene el código original del commit indicado y este informe. El estado Git permite revisar exactamente esa diferencia.


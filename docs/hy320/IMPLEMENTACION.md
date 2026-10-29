# Implementación en curso — CloudStream independiente

Rama: features/device-compatibility-and-home. El launcher queda fuera de este trabajo. No instalar ni sustituir CloudStream en el proyector sin petición del propietario. Sin PR ni publicación de cambios por ahora.

## Peticiones que deben conservarse

- Compatibilidad de imágenes automática ante fallos de bitmap hardware y opción software, sin depender solo del nombre del dispositivo.
- Diagnóstico de disponibilidad: no confundir ficha/episodios vacíos, extracción y reproducción; reintento real que invalide la ficha en caché.
- Descargas completas y legibles primero, sin solicitudes de enlaces; conservar opción streaming y avance al siguiente episodio.
- Inicio con varias/todas las fuentes compatibles, selección única anterior conservada, resultados parciales y concurrencia acotada.
- Tarjetas normales +25% configurable; la tarjeta principal no crece.
- Cinco destacados iniciales, fondo horizontal si el proveedor lo ofrece y alternativa cuando no.
- Abrir Inicio desde el lanzador y raíz de cada sección, con opción para restaurar navegación anterior. Respetar enlaces, reproductores externos y retorno de selectores.
- Menú lateral TV ocultable: solo revelar desde el borde inicial o con tecla Opciones; respetar reproducción y pantallas sin menú.
- Carátula alternativa visible cuando no hay imagen o falla, sin inventar una carátula real.
- Verificar puntuaciones en TV y no fabricar valores cuando el proveedor no aporta nota.
- Hasta12 recomendaciones entre fuentes seleccionadas, con filtro Todo/por proveedor y presupuesto de red limitado.
- Envío MANUAL de título y progreso entre dispositivos en la misma LAN, elegido por el usuario: código temporal y confirmación en el receptor. No sincronización automática de todo el historial.
- Mantener APIs de extensiones, datos y comportamientos anteriores como opciones; documentar limitaciones y pruebas pendientes.

## Implementado en esta rama

Las opciones están en Ajustes → General → Compatibilidad del dispositivo y navegación. El selector de inicio conserva el modo de una fuente y añade selección múltiple y Todas las fuentes compatibles. Elegir Todas también incluye nuevas fuentes compatibles que se instalen después; una selección explícita permanece fija.

Las consultas agregadas muestran resultados parciales. Se permiten tres consultas de catálogo simultáneas, se serializan las de una misma fuente y se limita el lote a 35 segundos. Las tarjetas aparecen antes de esperar los metadatos de los destacados. Las recomendaciones usan dos consultas simultáneas y un presupuesto global de 18 segundos. Una fuente fallida no elimina resultados obtenidos por otras.

Las recomendaciones parten de las nativas del título, consultan coincidencias de título/año en proveedores directos seleccionados y completan, si hace falta, con sugerencias del catálogo del mismo tipo de contenido. Estas últimas no garantizan relación argumental. Se excluye el título abierto y se deduplican títulos para presentación, sin fusionar historiales ni descargas. No se obliga a alcanzar doce si las fuentes no proporcionan candidatos. La opción de seis permanece disponible.

El decodificador reintenta en software los errores identificados de bitmap hardware; no trata errores HTTP, cancelaciones ni falta de memoria como un problema de bitmap. La imagen alternativa evita tarjetas vacías, pero no recupera una portada que el proveedor no publica o bloquea. No se han añadido servicios externos para inventar notas o imágenes.

La selección de visibilidad de puntuaciones usa el contexto de la tarjeta, sin depender de que exista una etiqueta de calidad en esa presentación. Las bibliotecas permiten nota personal o nota pública cuando esté disponible. Esto corrige una ruta potencial de ocultación; no demuestra por sí solo que todas las diferencias observadas entre Auto y TV tengan esa causa.

La ficha transitoriamente no reproducible tiene una caché más corta y el reintento invalida realmente su caché antes de cargarla otra vez. El texto distingue disponibilidad desconocida de estreno futuro. Esto no repara automáticamente extractores o servidores externos caídos y no garantiza reproducción de cada fuente.

Las descargas completas y legibles se utilizan antes de extraer enlaces remotos en el reproductor interno. La comprobación incluye tamaño esperado o un marcador de finalización y excluye archivos que todavía se descargan. Las descargas antiguas sin tamaño ni constancia de finalización no se suponen completas. Casting y reintento con caché forzada conservan el flujo remoto.

## Envío manual en red local

La actividad de compartir se abre desde General o desde Compartir de una ficha. Ambos dispositivos necesitan esta variante para el envío local. El receptor se activa explícitamente por dos minutos, para un solo envío, con un código aleatorio de doce caracteres (se permiten guiones al introducirlo). El descubrimiento usa DNS-SD y hay entrada manual de IP y puerto. No hay servicio permanente ni sincronización de todo el historial.

La carga contiene un solo título, proveedor, URL y posición/episodio. Se valida con un lector JSON de campos permitidos, tamaño limitado y sin deserialización de tipos arbitrarios. El receptor confirma antes de abrir la ficha o puede buscar el título si no dispone del proveedor. La posición pendiente se limita a la cuenta receptora, al proveedor y URL exactos y caduca en diez minutos; se aplica después de conocer los episodios reales. No se inicia reproducción automáticamente.

El transporte experimental usa claves ECDH efímeras y AES-GCM derivado del secreto y código. Limita conexiones, intentos, tiempos y tamaños; cierra sockets al abandonar recepción. No conserva emparejamientos ni expone credenciales de proveedores. No equivale a un protocolo auditado y requiere revisión antes de proponerlo al repositorio original. El código tiene más entropía que un PIN corto para reducir ataques de adivinación sin conexión.

## Verificación

Validación final del 28/10/2026: :app:testPrereleaseDebugUnitTest y :app:assemblePrereleaseDebug completadas correctamente. Las 38 pruebas JVM pasan sin fallos ni errores: ocho originales de etiquetas de subtítulos y treinta de los cambios (bitmaps, caché, archivos locales, borde del menú, agregación de fuentes, selección de recomendaciones, validación de envío y transporte local). La compilación final incremental duró 22 segundos. La APK usa com.lagradost.cloudstream3.prerelease.debug y firma de depuración, para prueba separada de la versión original.

Los destacados también tienen concurrencia de dos, ocho segundos por ficha y dieciocho segundos por lote. Se conserva lo que respondió a tiempo. No se han realizado mediciones de fluidez en un Android físico: estos límites reducen el bloqueo por red y no se presentan como un benchmark de rendimiento.

Android SDK37 y build-tools36 se obtuvieron mediante el Gradle original, con licencias del SDK ya aceptadas. Ninguna APK nueva está instalada en el dispositivo. Quedan pendientes las comprobaciones visuales de mando, notas y foco en TV, reproducción real de varios proveedores, permisos de red en Android37 y transferencia entre dos Android físicos.

Antes de colaborar con upstream: revisar cada bloque en cambios pequeños, probar estos escenarios reales, respetar AI-POLICY.md y explicar el uso de asistencia de IA. No se ha abierto ningún PR ni enviado estos cambios al repositorio original.

# Pruebas pendientes en dispositivos

La APK de prueba tiene identificador separado. No se ha instalado automáticamente. Para la transferencia local hacen falta dos instalaciones de esta variante; la versión original no recibe este protocolo.

| Área | Comprobación | Resultado necesario |
|---|---|---|
| Portadas | Comparar Auto y Software con las mismas fichas en TV y teléfono; repetir una URL bloqueada y una ficha sin imagen | Imagen cuando se pueda decodificar; alternativa visible ante ausencia/error; sin bucle de reintentos |
| Notas | Misma fuente y título en Auto/TV; activar/desactivar notas; incluir ficha con nota y sin nota | Nota real consistente, ninguna nota inventada, ajuste respetado |
| Inicio múltiple | Elegir dos fuentes rápidas y una caída; luego Todas; volver a fuente única | Filas identificadas por fuente, resultados parciales, desplazamiento y paginación correctos |
| Recomendaciones | Abrir película, serie y anime; probar 6/12 y filtro Todas/por fuente | Hasta el límite configurado, título actual excluido, abrir la ficha en su proveedor original |
| Destacados | Ajustar 1/3/5 con proveedor con fondo horizontal y sin él | Hero mantiene tamaño, fondo si está disponible, alternativa original en otro caso |
| Tarjetas | 100/125/150 y tamaño anterior de póster | Escala combinada sin recorte del foco ni saltos al navegar |
| Mando | Moverse entre portadas; izquierda desde segundo elemento y desde primero; Opciones; Atrás; reproducir vídeo | Menú aparece solo desde borde o tecla prevista; reproductor conserva sus teclas |
| Navegación | Home del mando, selección de cada sección, retorno de reproductor externo, enlace compartido | Raíz por defecto; destino anterior solo con ajuste; enlaces y retornos conservados |
| Disponibilidad | Ficha con estreno futuro, serie vacía transitoria y proveedor sin enlaces; reintentar tras volver el servidor | No se confunden estados ni se simula vídeo; reintento solicita ficha nueva |
| Descargas | Archivo completo, parcial, borrado, sin permiso, episodio siguiente, streaming preferido y casting | Se usa solo local completo legible; el resto conserva recuperación remota |
| LAN | Recibir, descubrir y enviar título/episodio/progreso; código erróneo; rechazar; aceptar; cambiar cuenta; cerrar recepción | Confirmación previa; un solo envío; progreso en episodio correcto y misma cuenta; sin listener persistente |
| LAN compatible | Android antiguo, Android actual y Android37 con permiso denegado/concedido; Wi-Fi con aislamiento de clientes | Fallo comprensible y acotado donde la red/permiso impide conexión; sin exigir que funcione una red aislada |

No se debe presentar la compilación o las pruebas JVM como sustituto de esta validación. Los fallos de un proveedor externo deben registrarse separando catálogo, imagen, ficha/episodios, extracción de enlaces y reproducción real.

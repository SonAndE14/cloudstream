# Validación antes de implementar o contribuir

Ninguna propuesta del estudio se declara resuelta por haber escrito estos documentos. El launcher es un proyecto distinto de la app y se valida por separado.

| Mejora | Prueba decisiva | Regresión que debe evitar |
|---|---|---|
| Prioridad local | Ficha/Continuar reproducen archivo completo sin red ni invocación al proveedor | Cambiar de episodio, idioma o edición por error |
| Descarga parcial | Detectar estado en curso, cero bytes, tamaño desconocido, archivo movido y permiso revocado | Reproducir incompleto como si estuviera finalizado |
| Subtítulos locales | Abrir con la copia local y conservar selección y progreso | Volver a descargar subtítulos válidos |
| Varias fuentes | Dos rápidas, una lenta y una fallida; resultados parciales siguen utilizables | Esperar a todas o borrar resultados correctos |
| Seleccionar todas | Instalación, desinstalación y deshabilitación mientras la pantalla está abierta | Incluir extractores sin catálogo ni respetar idiomas |
| Falsos Próximamente | Datos vacíos por fallo no se presentan como estreno futuro | Marcar como disponible un título sin fuentes |
| Reintento | Fallo temporal con una recuperación y cancelación al salir | Bucle infinito, repetición de solicitudes no recuperables |
| Caché | Enlace vigente y expirado; perfiles de calidad distintos | Reusar enlace inválido o excluir enlaces válidos por tipo |
| Imágenes | Bitmaps software/hardware, retorno y desplazamiento largo | OOM o depender exclusivamente del nombre comercial |
| Motor externo | Mismo enlace vigente, cabeceras y punto de inicio | Comparar servidores diferentes y culpar al decodificador |

Medidas mínimas: tiempo de ficha, primer enlace válido, primer fotograma, p50/p95 sobre varias ejecuciones, memoria máxima, solicitudes de red y fallos por fase. Registrar dispositivo, firmware, versión de la app y versión de extensiones. Para pruebas deterministas usar fixtures/servidor controlado con respuestas vacías, lentas, parciales y errores HTTP; complementar con proveedores reales porque pueden cambiar.

Antes de publicar: compilar la variante de prueba con identidad separada, ejecutar pruebas relevantes y usar una instalación aislada solo con autorización. No reemplazar la app actual del proyector como parte de esta revisión. Preparar una PR por problema, con reproducción, resultado antes/después y compatibilidad con la base upstream actualizada.

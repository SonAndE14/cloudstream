# Selección de varias o todas las fuentes de Inicio

Propuesta; no implementada. Hay que distinguir catálogo de metadatos, proveedor de episodios y servidor de vídeo. Seleccionar todos los catálogos no convierte cada ficha en reproducible.

## Lo que ya existe

SearchViewModel.search recibe providersActive como Set<String>. En el filtro de alrededor de236, un conjunto vacío admite todos los repositorios aplicables. La búsqueda múltiple ya existe y debe reutilizarse. No anunciar una nueva búsqueda multifuentе como si la app no tuviera ninguna.

HomeViewModel.loadAndCancel recibe preferredApiName:String y usa un solo APIRepository; DataStoreHelper.currentHomePage conserva un String. La mejora principal está en Inicio y en presentar con claridad los controles que ya existen en Buscar.

## Comportamiento propuesto

Cuadrícula de iconos o fichas de proveedores con selección múltiple, «Seleccionar todos», «Ninguno» y filtro por idioma/tipo. Para Inicio, «todos» incluye las extensiones instaladas, habilitadas y compatibles con página principal, mostrando por qué una extensión no es aplicable. Un extractor que solo resuelve vídeo no debe aparecer como un catálogo seleccionable.

Guardar una selección explícita o una política «todos los compatibles». Diferenciarlas permite que una extensión recién instalada se incluya automáticamente solo en el modo Todos. Migrar la antigua preferencia individual a un conjunto de un elemento; conservar un camino de regreso y no reemplazar las elecciones existentes silenciosamente.

Cada fuente conserva filas, orden, caché y paginación independientes. Mostrar resultados que llegan primero y el estado de las restantes; si una falla no vaciar las demás. Cancelar trabajo de la selección anterior al cambiar de pantalla. Limitar concurrencia y medir memoria, tiempos y consumo de red en el HY320 antes de fijar valores definitivos.

## Límites de la agregación

Deduplicar solo con identidad fiable; misma película doblada/subtitulada puede requerir variantes separadas. No fusionar dos series por título ni mezclar episodios o idiomas. Conservar acceso al proveedor original y al motivo de un error.

Evitar que un metacatálogo consulte recursivamente a otros metacatálogos sin límite. Ese problema puede estar en la extensión, por lo que se investigará y corregirá en su propio repositorio cuando corresponda. El fork de la app no debe contener parches ocultos de proveedores externos.

## Pruebas de aceptación

Migración de una fuente; todas compatibles; selección vacía con significado claro; fuente deshabilitada o desinstalada; instalación de una nueva en modo Todos; una lenta y otra que responde; error parcial; paginación de una fila sin duplicar las demás; cambio de idioma; rotación/retorno sin duplicar peticiones; memoria y cancelación en el dispositivo.

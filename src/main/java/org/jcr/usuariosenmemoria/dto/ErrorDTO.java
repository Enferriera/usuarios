package org.jcr.usuariosenmemoria.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO (Data Transfer Object) para representar errores en las respuestas HTTP.
 *
 * <p>Este record proporciona un formato estandarizado y consistente para todas las
 * respuestas de error de la API, mejorando la experiencia del desarrollador que
 * consume la API.</p>
 *
 * <h3>¿Por qué necesitamos un DTO de error estandarizado?</h3>
 * <ul>
 *   <li><b>Consistencia:</b> Todos los errores tienen la misma estructura JSON</li>
 *   <li><b>Parseo fácil:</b> Los clientes pueden manejar errores de forma predecible</li>
 *   <li><b>Información completa:</b> Incluye timestamp, código HTTP, mensaje y detalles</li>
 *   <li><b>Debugging facilitado:</b> El timestamp ayuda a correlacionar errores con logs del servidor</li>
 * </ul>
 *
 * <h3>Estructura del JSON de error:</h3>
 * <pre>{@code
 * {
 *   "timestamp": "2025-10-31T10:30:00",    // Momento exacto del error
 *   "status": 400,                          // Código HTTP (400, 404, 409, 500, etc.)
 *   "error": "Error de validación",        // Descripción general del tipo de error
 *   "detalles": [                           // Lista de detalles específicos
 *     "nombre: El nombre no puede estar vacío",
 *     "email: Formato de email inválido"
 *   ]
 * }
 * }</pre>
 *
 * <h3>Ventajas de usar Java Records:</h3>
 * <p>Los records (introducidos en Java 14) son perfectos para DTOs porque:</p>
 * <ul>
 *   <li>Son inmutables por defecto (todos los campos son final)</li>
 *   <li>Generan automáticamente constructor, getters, equals(), hashCode() y toString()</li>
 *   <li>Tienen sintaxis concisa y legible</li>
 *   <li>Transmiten intención: este es un portador de datos, no una clase con lógica</li>
 * </ul>
 *
 * <h3>Factory Methods (Métodos de fábrica):</h3>
 * <p>Esta clase proporciona dos métodos estáticos para crear instancias de forma conveniente:</p>
 * <ul>
 *   <li><b>of():</b> Para errores con múltiples detalles (lista de mensajes)</li>
 *   <li><b>simple():</b> Para errores con un solo detalle (mensaje único)</li>
 * </ul>
 *
 * <h3>Ejemplo de uso en GlobalExceptionHandler:</h3>
 * <pre>{@code
 * // Error con múltiples detalles (validaciones fallidas)
 * @ExceptionHandler(MethodArgumentNotValidException.class)
 * public ResponseEntity<ErrorDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
 *     List<String> detalles = ex.getBindingResult()
 *         .getFieldErrors()
 *         .stream()
 *         .map(error -> error.getField() + ": " + error.getDefaultMessage())
 *         .toList();
 *
 *     ErrorDTO errorDTO = ErrorDTO.of(
 *         HttpStatus.BAD_REQUEST.value(),
 *         "Error de validación",
 *         detalles  // Lista con múltiples mensajes
 *     );
 *
 *     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
 * }
 *
 * // Error con un solo detalle (ID no encontrado)
 * @ExceptionHandler(IllegalArgumentException.class)
 * public ResponseEntity<ErrorDTO> handleIllegalArgument(IllegalArgumentException ex) {
 *     ErrorDTO errorDTO = ErrorDTO.simple(
 *         HttpStatus.BAD_REQUEST.value(),
 *         "Argumento inválido",
 *         ex.getMessage()  // Un solo mensaje
 *     );
 *
 *     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
 * }
 * }</pre>
 *
 * <h3>Ejemplos de respuestas de error generadas:</h3>
 *
 * <h4>Ejemplo 1: Error de validación (múltiples campos)</h4>
 * <pre>{@code
 * POST /api/usuarios
 * {
 *   "nombre": "",
 *   "email": "correo-invalido",
 *   "password": "123"
 * }
 *
 * HTTP/1.1 400 Bad Request
 * {
 *   "timestamp": "2025-10-31T14:30:00",
 *   "status": 400,
 *   "error": "Error de validación",
 *   "detalles": [
 *     "nombre: El nombre no puede estar vacío",
 *     "email: Formato de email inválido",
 *     "password: La contraseña debe tener al menos 6 caracteres"
 *   ]
 * }
 * }</pre>
 *
 * <h4>Ejemplo 2: Usuario no encontrado (un solo detalle)</h4>
 * <pre>{@code
 * GET /api/usuarios/999
 *
 * HTTP/1.1 400 Bad Request
 * {
 *   "timestamp": "2025-10-31T14:31:00",
 *   "status": 400,
 *   "error": "Argumento inválido",
 *   "detalles": [
 *     "No existe un usuario con el ID 999"
 *   ]
 * }
 * }</pre>
 *
 * <h4>Ejemplo 3: Email duplicado (conflicto)</h4>
 * <pre>{@code
 * POST /api/usuarios
 * {
 *   "nombre": "Juan",
 *   "email": "juan@example.com",  // Ya existe
 *   "password": "pass123"
 * }
 *
 * HTTP/1.1 409 Conflict
 * {
 *   "timestamp": "2025-10-31T14:32:00",
 *   "status": 409,
 *   "error": "Error de integridad de datos",
 *   "detalles": [
 *     "El email ya está registrado"
 *   ]
 * }
 * }</pre>
 *
 * <h3>Comparación con otros formatos de error:</h3>
 * <p>Diferentes APIs usan diferentes formatos. Esta API sigue un formato estándar similar
 * a Spring Boot por defecto:</p>
 * <table border="1">
 *   <tr>
 *     <th>Campo</th>
 *     <th>Tipo</th>
 *     <th>Propósito</th>
 *   </tr>
 *   <tr>
 *     <td>timestamp</td>
 *     <td>LocalDateTime</td>
 *     <td>Momento exacto del error (para debugging y correlación con logs)</td>
 *   </tr>
 *   <tr>
 *     <td>status</td>
 *     <td>int</td>
 *     <td>Código HTTP (400, 404, 409, 500) - redundante pero útil en el body</td>
 *   </tr>
 *   <tr>
 *     <td>error</td>
 *     <td>String</td>
 *     <td>Descripción general del tipo de error</td>
 *   </tr>
 *   <tr>
 *     <td>detalles</td>
 *     <td>List&lt;String&gt;</td>
 *     <td>Mensajes específicos (pueden ser múltiples para errores de validación)</td>
 *   </tr>
 * </table>
 *
 * <h3>¿Por qué incluir 'status' en el body si ya está en la respuesta HTTP?</h3>
 * <ul>
 *   <li>Facilita el parsing: el cliente puede leer todo del JSON sin analizar headers</li>
 *   <li>Es un patrón común en APIs REST</li>
 *   <li>Útil para logging y debugging del lado del cliente</li>
 * </ul>
 *
 * @param timestamp Momento en que ocurrió el error (generado automáticamente con LocalDateTime.now())
 * @param status Código de estado HTTP (400, 404, 409, 500, etc.)
 * @param error Descripción general del tipo de error
 * @param detalles Lista de mensajes de error específicos (puede ser un solo elemento)
 *
 * @author Juan Cruz Robledo
 * @version 1.0
 * @since 2025
 * @see GlobalExceptionHandler
 */
public record ErrorDTO(
        /**
         * Timestamp del momento exacto en que ocurrió el error.
         *
         * <p>Usado para:</p>
         * <ul>
         *   <li>Correlacionar errores del cliente con logs del servidor</li>
         *   <li>Debugging y auditoría</li>
         *   <li>Identificar si el error es reciente o de una petición antigua</li>
         * </ul>
         *
         * <p>Formato: ISO 8601 (ej: "2025-10-31T14:30:00")</p>
         */
        LocalDateTime timestamp,

        /**
         * Código de estado HTTP del error.
         *
         * <p>Valores comunes en esta API:</p>
         * <ul>
         *   <li><b>400 BAD REQUEST:</b> Validación fallida, parámetros inválidos</li>
         *   <li><b>404 NOT FOUND:</b> Recurso no encontrado</li>
         *   <li><b>409 CONFLICT:</b> Email duplicado, conflicto con datos existentes</li>
         *   <li><b>500 INTERNAL SERVER ERROR:</b> Error inesperado del servidor</li>
         * </ul>
         */
        int status,

        /**
         * Descripción general del tipo de error.
         *
         * <p>Ejemplos:</p>
         * <ul>
         *   <li>"Error de validación"</li>
         *   <li>"Argumento inválido"</li>
         *   <li>"Error de integridad de datos"</li>
         *   <li>"Error interno del servidor"</li>
         * </ul>
         */
        String error,

        /**
         * Lista de mensajes de error detallados.
         *
         * <p>Para errores de validación, contiene un mensaje por cada campo que falló.</p>
         * <p>Para otros errores, típicamente contiene un solo mensaje explicativo.</p>
         *
         * <p>Ejemplos:</p>
         * <ul>
         *   <li>["nombre: El nombre no puede estar vacío", "email: Formato de email inválido"]</li>
         *   <li>["No existe un usuario con el ID 999"]</li>
         *   <li>["El email ya está registrado"]</li>
         * </ul>
         */
        List<String> detalles
) {
    /**
     * Factory method para crear un ErrorDTO con múltiples detalles.
     *
     * <p>Este método es ideal para errores de validación donde múltiples campos
     * pueden fallar simultáneamente.</p>
     *
     * <h3>¿Qué es un Factory Method?</h3>
     * <p>Un factory method es un patrón de diseño que proporciona un método estático
     * para crear instancias de una clase. Ventajas:</p>
     * <ul>
     *   <li>Nombres descriptivos que explican la intención (of, simple, etc.)</li>
     *   <li>Puede realizar lógica adicional antes de crear la instancia</li>
     *   <li>Más legible que usar 'new' con muchos parámetros</li>
     * </ul>
     *
     * <h3>¿Por qué se llama "of"?</h3>
     * <p>Es una convención de naming común en Java (ej: List.of(), Set.of()) que
     * indica "crear una instancia a partir de estos valores".</p>
     *
     * <h3>Ejemplo de uso:</h3>
     * <pre>{@code
     * List<String> detalles = List.of(
     *     "nombre: El nombre no puede estar vacío",
     *     "email: Formato de email inválido",
     *     "password: La contraseña debe tener al menos 6 caracteres"
     * );
     *
     * ErrorDTO error = ErrorDTO.of(400, "Error de validación", detalles);
     *
     * // JSON generado:
     * // {
     * //   "timestamp": "2025-10-31T14:30:00",
     * //   "status": 400,
     * //   "error": "Error de validación",
     * //   "detalles": ["nombre: ...", "email: ...", "password: ..."]
     * // }
     * }</pre>
     *
     * @param status Código de estado HTTP (400, 404, 409, 500, etc.)
     * @param error Descripción general del tipo de error
     * @param detalles Lista con múltiples mensajes de error
     * @return Nueva instancia de ErrorDTO con timestamp actual
     */
    public static ErrorDTO of(int status, String error, List<String> detalles) {
        return new ErrorDTO(LocalDateTime.now(), status, error, detalles);
    }

    /**
     * Factory method para crear un ErrorDTO con un solo detalle.
     *
     * <p>Este método es ideal para errores simples donde solo hay un mensaje
     * explicativo (ej: usuario no encontrado, email duplicado).</p>
     *
     * <h3>Conveniencia vs. of():</h3>
     * <p>Este método es un atajo para evitar escribir List.of(detalle) manualmente:</p>
     * <pre>{@code
     * // Sin simple():
     * ErrorDTO error = ErrorDTO.of(400, "Argumento inválido",
     *     List.of("No existe un usuario con el ID 999"));
     *
     * // Con simple():
     * ErrorDTO error = ErrorDTO.simple(400, "Argumento inválido",
     *     "No existe un usuario con el ID 999");
     * }</pre>
     *
     * <h3>Ejemplo de uso:</h3>
     * <pre>{@code
     * ErrorDTO error = ErrorDTO.simple(
     *     409,
     *     "Error de integridad de datos",
     *     "El email ya está registrado"
     * );
     *
     * // JSON generado:
     * // {
     * //   "timestamp": "2025-10-31T14:30:00",
     * //   "status": 409,
     * //   "error": "Error de integridad de datos",
     * //   "detalles": ["El email ya está registrado"]
     * // }
     * }</pre>
     *
     * <h3>¿Por qué detalles es una lista aunque solo haya un elemento?</h3>
     * <p>Mantener detalles como lista (incluso con un elemento) proporciona:</p>
     * <ul>
     *   <li><b>Consistencia:</b> Todos los errores tienen la misma estructura JSON</li>
     *   <li><b>Flexibilidad:</b> El cliente no necesita manejar dos formatos diferentes</li>
     *   <li><b>Extensibilidad:</b> Fácil agregar más detalles en el futuro</li>
     * </ul>
     *
     * @param status Código de estado HTTP (400, 404, 409, 500, etc.)
     * @param error Descripción general del tipo de error
     * @param detalle Mensaje de error único
     * @return Nueva instancia de ErrorDTO con timestamp actual y lista de un solo elemento
     */
    public static ErrorDTO simple(int status, String error, String detalle) {
        return new ErrorDTO(LocalDateTime.now(), status, error, List.of(detalle));
    }

}

package org.jcr.usuariosenmemoria.exception;

import jakarta.validation.ConstraintViolationException;
import org.jcr.usuariosenmemoria.dto.ErrorDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * Manejador global de excepciones para la aplicación.
 *
 * <p>Esta clase centraliza el manejo de errores de toda la aplicación, convirtiendo excepciones
 * en respuestas HTTP estructuradas y consistentes.</p>
 *
 * <h3>¿Qué es @ControllerAdvice?</h3>
 * <p>@ControllerAdvice es una especialización de @Component que permite:</p>
 * <ul>
 *   <li>Manejar excepciones globalmente para todos los controladores</li>
 *   <li>Aplicar configuración común a múltiples controladores</li>
 *   <li>Vincular datos globalmente (model attributes)</li>
 *   <li>Personalizar el comportamiento de los controladores</li>
 * </ul>
 *
 * <h3>Ventajas del Manejo Centralizado de Excepciones:</h3>
 * <ul>
 *   <li><b>Consistencia:</b> Todas las respuestas de error tienen el mismo formato JSON</li>
 *   <li><b>DRY:</b> Evita repetir código de manejo de errores en cada controlador</li>
 *   <li><b>Mantenibilidad:</b> Cambios en el formato de error solo requieren modificar esta clase</li>
 *   <li><b>Separación de responsabilidades:</b> Los controladores se enfocan en la lógica del negocio</li>
 *   <li><b>Experiencia de usuario:</b> Los clientes reciben mensajes de error claros y estructurados</li>
 * </ul>
 *
 * <h3>¿Qué es @ExceptionHandler?</h3>
 * <p>@ExceptionHandler es una anotación que marca métodos para manejar tipos específicos
 * de excepciones. Cuando ocurre una excepción en cualquier controlador:</p>
 * <ol>
 *   <li>Spring busca un método @ExceptionHandler que maneje ese tipo de excepción</li>
 *   <li>Si lo encuentra, invoca ese método en lugar de propagar el error</li>
 *   <li>El método retorna una ResponseEntity con el código HTTP y mensaje apropiados</li>
 * </ol>
 *
 * <h3>Jerarquía de Excepciones Manejadas:</h3>
 * <pre>{@code
 * Exception (genérica)
 * ├── RuntimeException
 * │   ├── IllegalArgumentException (ID no encontrado, argumentos inválidos)
 * │   ├── DataAccessException
 * │   │   └── DataIntegrityViolationException (email duplicado, constraints BD)
 * │   └── ConstraintViolationException (validaciones programáticas)
 * └── MethodArgumentNotValidException (validaciones @Valid en DTOs)
 * }</pre>
 *
 * <h3>Mapeo de Excepciones a Códigos HTTP:</h3>
 * <table border="1">
 *   <tr>
 *     <th>Excepción</th>
 *     <th>Código HTTP</th>
 *     <th>Significado</th>
 *   </tr>
 *   <tr>
 *     <td>MethodArgumentNotValidException</td>
 *     <td>400 BAD REQUEST</td>
 *     <td>Validación de DTO fallida (@NotBlank, @Email, etc.)</td>
 *   </tr>
 *   <tr>
 *     <td>IllegalArgumentException</td>
 *     <td>400 BAD REQUEST</td>
 *     <td>ID de usuario no encontrado, parámetros inválidos</td>
 *   </tr>
 *   <tr>
 *     <td>ConstraintViolationException</td>
 *     <td>400 BAD REQUEST</td>
 *     <td>Validaciones en otros contextos</td>
 *   </tr>
 *   <tr>
 *     <td>DataIntegrityViolationException</td>
 *     <td>409 CONFLICT</td>
 *     <td>Email duplicado, violación de constraint UNIQUE</td>
 *   </tr>
 *   <tr>
 *     <td>Exception (genérica)</td>
 *     <td>500 INTERNAL SERVER ERROR</td>
 *     <td>Error inesperado no controlado</td>
 *   </tr>
 * </table>
 *
 * <h3>Flujo de Manejo de Excepciones:</h3>
 * <pre>{@code
 * 1. Controller → Lanza una excepción (ej: IllegalArgumentException)
 * 2. Spring intercepta la excepción
 * 3. Spring busca @ExceptionHandler que maneje ese tipo
 * 4. GlobalExceptionHandler.handleIllegalArgument() es invocado
 * 5. El método construye un ErrorDTO con detalles del error
 * 6. Retorna ResponseEntity con código HTTP 400 y ErrorDTO en JSON
 * 7. Cliente recibe respuesta HTTP estructurada con el error
 * }</pre>
 *
 * <h3>Formato de Respuesta de Error:</h3>
 * <pre>{@code
 * {
 *   "status": 400,
 *   "error": "Error de validación",
 *   "timestamp": "2025-10-31T10:30:00",
 *   "details": [
 *     "nombre: El nombre no puede estar vacío",
 *     "email: Formato de email inválido"
 *   ]
 * }
 * }</pre>
 *
 * <h3>Ejemplo de flujo completo:</h3>
 * <pre>{@code
 * // Cliente envía petición inválida
 * POST /api/usuarios
 * {
 *   "nombre": "",
 *   "email": "correo-invalido",
 *   "password": "123"
 * }
 *
 * // Spring valida el DTO usando @Valid
 * // Validación falla → lanza MethodArgumentNotValidException
 *
 * // GlobalExceptionHandler intercepta la excepción
 * // handleValidationErrors() construye ErrorDTO con detalles
 *
 * // Cliente recibe:
 * HTTP/1.1 400 Bad Request
 * {
 *   "status": 400,
 *   "error": "Error de validación",
 *   "timestamp": "2025-10-31T10:30:00",
 *   "details": [
 *     "nombre: El nombre no puede estar vacío",
 *     "email: Formato de email inválido",
 *     "password: La contraseña debe tener al menos 6 caracteres"
 *   ]
 * }
 * }</pre>
 *
 * <h3>Seguridad en el Manejo de Errores:</h3>
 * <ul>
 *   <li><b>No exponer stack traces en producción:</b> Pueden revelar información sensible</li>
 *   <li><b>Mensajes genéricos para errores 500:</b> No revelar detalles de implementación</li>
 *   <li><b>Logging interno:</b> Registrar detalles completos en logs para debugging</li>
 * </ul>
 *
 * @author Juan Cruz Robledo
 * @version 1.0
 * @since 2025
 * @see ErrorDTO
 * @see ControllerAdvice
 * @see ExceptionHandler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validación de Bean Validation en DTOs.
     *
     * <p>Este handler captura excepciones lanzadas cuando las validaciones @Valid fallan
     * en los parámetros de los métodos del controlador.</p>
     *
     * <h3>¿Cuándo se lanza MethodArgumentNotValidException?</h3>
     * <p>Esta excepción ocurre cuando:</p>
     * <ul>
     *   <li>Un controlador recibe un DTO marcado con @Valid</li>
     *   <li>El DTO contiene anotaciones de validación (@NotBlank, @Email, @Size, etc.)</li>
     *   <li>Una o más validaciones fallan</li>
     * </ul>
     *
     * <h3>Ejemplo de flujo:</h3>
     * <pre>{@code
     * // Cliente envía:
     * POST /api/usuarios
     * {
     *   "nombre": "",
     *   "email": "correo-invalido",
     *   "password": "123"
     * }
     *
     * // Controlador:
     * public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioRequestDTO dto)
     *
     * // Spring ejecuta validaciones:
     * // - nombre: falla @NotBlank
     * // - email: falla @Email
     * // - password: falla @Size(min=6)
     *
     * // Spring lanza MethodArgumentNotValidException
     * // Este método intercepta la excepción
     * }</pre>
     *
     * <h3>Procesamiento de Errores:</h3>
     * <ol>
     *   <li>Obtiene BindingResult que contiene todos los errores de validación</li>
     *   <li>Extrae FieldErrors (errores específicos de cada campo)</li>
     *   <li>Usa Streams para transformar cada error a formato legible: "campo: mensaje"</li>
     *   <li>Construye ErrorDTO con todos los detalles</li>
     *   <li>Retorna ResponseEntity con código 400 BAD REQUEST</li>
     * </ol>
     *
     * <h3>¿Qué es BindingResult?</h3>
     * <p>BindingResult es un objeto de Spring que almacena el resultado del proceso
     * de binding (vinculación de datos HTTP a objetos Java) y validación. Contiene:</p>
     * <ul>
     *   <li>Lista de errores de validación (FieldErrors)</li>
     *   <li>Información sobre qué campos fallaron</li>
     *   <li>Mensajes de error configurados en las anotaciones</li>
     * </ul>
     *
     * <h3>Respuesta generada:</h3>
     * <pre>{@code
     * HTTP/1.1 400 Bad Request
     * {
     *   "status": 400,
     *   "error": "Error de validación",
     *   "timestamp": "2025-10-31T10:30:00",
     *   "details": [
     *     "nombre: El nombre no puede estar vacío",
     *     "email: Formato de email inválido",
     *     "password: La contraseña debe tener al menos 6 caracteres"
     *   ]
     * }
     * }</pre>
     *
     * @param ex La excepción capturada que contiene los errores de validación
     * @return ResponseEntity con código 400 y ErrorDTO detallando todos los errores
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Extraer todos los errores de campo y formatearlos como "campo: mensaje"
        List<String> detalles = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        // Construir DTO de error con la lista de detalles
        ErrorDTO errorDTO = ErrorDTO.of(HttpStatus.BAD_REQUEST.value(),
                "Error de validación", detalles);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    /**
     * Maneja errores de validación de constraints en otros contextos.
     *
     * <p>Este handler captura ConstraintViolationException que ocurre cuando
     * se violan constraints de validación en contextos diferentes a @Valid en controllers
     * (por ejemplo, validaciones programáticas o en otros componentes).</p>
     *
     * <h3>¿Cuándo se lanza ConstraintViolationException?</h3>
     * <ul>
     *   <li>Validaciones directas con Validator.validate()</li>
     *   <li>Validaciones en métodos de servicio con @Validated</li>
     *   <li>Validaciones de parámetros con @Valid en servicios</li>
     * </ul>
     *
     * <h3>Diferencia con MethodArgumentNotValidException:</h3>
     * <table border="1">
     *   <tr>
     *     <th>Aspecto</th>
     *     <th>MethodArgumentNotValidException</th>
     *     <th>ConstraintViolationException</th>
     *   </tr>
     *   <tr>
     *     <td>Contexto</td>
     *     <td>@Valid en parámetros de controllers</td>
     *     <td>Validaciones programáticas o en servicios</td>
     *   </tr>
     *   <tr>
     *     <td>Jerarquía</td>
     *     <td>Exception directa</td>
     *     <td>RuntimeException</td>
     *   </tr>
     * </table>
     *
     * <h3>Respuesta generada:</h3>
     * <pre>{@code
     * HTTP/1.1 400 Bad Request
     * {
     *   "status": 400,
     *   "error": "Error de validación",
     *   "timestamp": "2025-10-31T10:30:00",
     *   "details": [
     *     "email: Debe ser un email válido",
     *     "nombre: No puede estar vacío"
     *   ]
     * }
     * }</pre>
     *
     * @param ex La excepción capturada que contiene las violaciones de constraints
     * @return ResponseEntity con código 400 y ErrorDTO con los detalles de las violaciones
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolation(ConstraintViolationException ex) {
        // Extraer todas las violaciones y formatearlas
        List<String> detalles = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();

        ErrorDTO errorDTO = ErrorDTO.of(HttpStatus.BAD_REQUEST.value(),
                "Error de validación", detalles);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    /**
     * Maneja IllegalArgumentException lanzadas por la lógica de negocio.
     *
     * <p>Este handler captura IllegalArgumentException que típicamente se usan para
     * indicar que un argumento proporcionado es inválido o no cumple las condiciones
     * de negocio.</p>
     *
     * <h3>¿Cuándo se lanza IllegalArgumentException en esta API?</h3>
     * <ul>
     *   <li><b>Usuario no encontrado:</b> Cuando se busca/actualiza/elimina un ID que no existe</li>
     *   <li><b>Parámetros inválidos:</b> Cuando se proporcionan valores fuera de rango</li>
     * </ul>
     *
     * <h3>Ejemplo de uso en el Service:</h3>
     * <pre>{@code
     * public UsuarioResponseDTO buscarUsuario(Long id) {
     *     Usuario usuario = repository.findById(id)
     *         .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con el ID " + id));
     *     return UsuarioResponseDTO.fromEntity(usuario);
     * }
     * }</pre>
     *
     * <h3>Flujo completo:</h3>
     * <pre>{@code
     * // Cliente solicita:
     * GET /api/usuarios/999
     *
     * // Service no encuentra el usuario
     * // Lanza: new IllegalArgumentException("No existe un usuario con el ID 999")
     *
     * // Este handler intercepta la excepción
     * // Genera respuesta con el mensaje de la excepción
     * }</pre>
     *
     * <h3>Respuesta generada:</h3>
     * <pre>{@code
     * HTTP/1.1 400 Bad Request
     * {
     *   "status": 400,
     *   "error": "Argumento inválido",
     *   "timestamp": "2025-10-31T10:30:00",
     *   "detail": "No existe un usuario con el ID 999"
     * }
     * }</pre>
     *
     * <h3>¿Por qué 400 BAD REQUEST?</h3>
     * <p>Según las convenciones HTTP:</p>
     * <ul>
     *   <li><b>400 BAD REQUEST:</b> La petición contiene datos inválidos (ID no válido)</li>
     *   <li><b>404 NOT FOUND:</b> El recurso/endpoint no existe (alternativa menos común para este caso)</li>
     * </ul>
     * <p>Esta API usa 400 porque considera que solicitar un ID inexistente es un error
     * en los parámetros de la petición.</p>
     *
     * @param ex La excepción capturada que contiene el mensaje de error
     * @return ResponseEntity con código 400 y ErrorDTO con el mensaje del error
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorDTO errorDTO = ErrorDTO.simple(HttpStatus.BAD_REQUEST.value(),
                "Argumento inválido", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    /**
     * Maneja errores de integridad de datos en la base de datos.
     *
     * <p>Este handler captura DataIntegrityViolationException que ocurre cuando
     * se violan constraints definidos a nivel de base de datos.</p>
     *
     * <h3>¿Cuándo se lanza DataIntegrityViolationException?</h3>
     * <ul>
     *   <li><b>Constraint UNIQUE:</b> Intentar insertar un email que ya existe</li>
     *   <li><b>Constraint NOT NULL:</b> Intentar insertar NULL en campo obligatorio</li>
     *   <li><b>Constraint FOREIGN KEY:</b> Intentar referenciar un registro inexistente</li>
     *   <li><b>Constraint CHECK:</b> Violación de reglas CHECK de la BD</li>
     * </ul>
     *
     * <h3>Caso más común en esta API: Email duplicado</h3>
     * <pre>{@code
     * // Usuario.java define:
     * @Column(unique = true, nullable = false, length = 255)
     * private String email;
     *
     * // Esto crea en BD:
     * ALTER TABLE usuarios ADD CONSTRAINT uk_email UNIQUE (email);
     *
     * // Si se intenta insertar email duplicado:
     * POST /api/usuarios
     * {
     *   "nombre": "Juan",
     *   "email": "juan@example.com",  // Ya existe en BD
     *   "password": "pass123"
     * }
     *
     * // La base de datos lanza una excepción SQL
     * // JPA la envuelve en DataIntegrityViolationException
     * // Este handler la intercepta
     * }</pre>
     *
     * <h3>Detección inteligente del tipo de error:</h3>
     * <p>El método analiza la causa raíz (rootCause) de la excepción para determinar
     * qué constraint se violó:</p>
     * <ul>
     *   <li>Si el mensaje contiene "EMAIL" o "UNIQUE" → "El email ya está registrado"</li>
     *   <li>Otros casos → "Violación de constraint de integridad" (mensaje genérico)</li>
     * </ul>
     *
     * <h3>¿Por qué usar getRootCause()?</h3>
     * <p>Las excepciones de JPA típicamente envuelven excepciones SQL:</p>
     * <pre>{@code
     * DataIntegrityViolationException (Spring)
     *   └─ JdbcSQLIntegrityConstraintViolationException (JDBC)
     *       └─ Mensaje real: "Unique index or primary key violation: UK_EMAIL"
     * }</pre>
     * <p>getRootCause() obtiene la excepción más profunda que contiene el mensaje real de la BD.</p>
     *
     * <h3>Respuesta generada para email duplicado:</h3>
     * <pre>{@code
     * HTTP/1.1 409 Conflict
     * {
     *   "status": 409,
     *   "error": "Error de integridad de datos",
     *   "timestamp": "2025-10-31T10:30:00",
     *   "detail": "El email ya está registrado"
     * }
     * }</pre>
     *
     * <h3>¿Por qué 409 CONFLICT?</h3>
     * <p>Según las convenciones HTTP:</p>
     * <ul>
     *   <li><b>409 CONFLICT:</b> La petición entra en conflicto con el estado actual del recurso
     *       (el email ya existe)</li>
     *   <li><b>400 BAD REQUEST:</b> Alternativa menos específica</li>
     * </ul>
     * <p>409 CONFLICT es semánticamente más correcto porque comunica explícitamente
     * que hay un conflicto con datos existentes.</p>
     *
     * @param ex La excepción capturada que contiene información sobre la violación de integridad
     * @return ResponseEntity con código 409 CONFLICT y ErrorDTO con mensaje descriptivo
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String mensaje = "Error de integridad de datos";
        String detalle = "El email ya está registrado";

        // Detectar el tipo de constraint violado analizando la causa raíz
        Throwable rootCause = ex.getRootCause();
        if (rootCause != null) {
            String rootMessage = rootCause.getMessage();
            if (rootMessage != null && (rootMessage.toUpperCase().contains("EMAIL") ||
                                       rootMessage.toUpperCase().contains("UNIQUE"))) {
                detalle = "El email ya está registrado";
            } else {
                detalle = "Violación de constraint de integridad";
            }
        }

        ErrorDTO errorDTO = ErrorDTO.simple(HttpStatus.CONFLICT.value(), mensaje, detalle);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDTO);
    }

    /**
     * Maneja cualquier excepción no controlada por los handlers específicos.
     *
     * <p>Este es el handler de último recurso (catch-all) que captura cualquier
     * Exception que no fue manejada por los handlers más específicos.</p>
     *
     * <h3>¿Cuándo se ejecuta este handler?</h3>
     * <ul>
     *   <li>Excepciones inesperadas no contempladas</li>
     *   <li>Errores de infraestructura (BD caída, timeout de conexión)</li>
     *   <li>NullPointerException no controladas</li>
     *   <li>Cualquier RuntimeException no específica</li>
     * </ul>
     *
     * <h3>Orden de precedencia de @ExceptionHandler:</h3>
     * <p>Spring busca el handler más específico primero:</p>
     * <ol>
     *   <li>Handler exacto (ej: IllegalArgumentException)</li>
     *   <li>Handler de subclase (ej: RuntimeException)</li>
     *   <li>Handler genérico (ej: Exception) &lt;- este método</li>
     * </ol>
     *
     * <h3>Principio de seguridad: No exponer detalles internos</h3>
     * <p>Este método NO incluye el stack trace ni detalles técnicos de la excepción
     * en la respuesta porque:</p>
     * <ul>
     *   <li><b>Seguridad:</b> Los stack traces revelan estructura interna de la aplicación</li>
     *   <li><b>Información sensible:</b> Pueden contener rutas de archivos, nombres de clases,
     *       queries SQL, etc.</li>
     *   <li><b>Experiencia de usuario:</b> Los detalles técnicos no son útiles para el cliente</li>
     * </ul>
     *
     * <h3>Buena práctica: Logging interno</h3>
     * <p>En producción, se recomienda agregar logging:</p>
     * <pre>{@code
     * @ExceptionHandler(Exception.class)
     * public ResponseEntity<ErrorDTO> handleGeneric(Exception ex) {
     *     // Registrar el error completo en logs para debugging
     *     log.error("Error inesperado: ", ex);
     *
     *     // Retornar mensaje genérico al cliente
     *     String detalle = "Ha ocurrido un error inesperado...";
     *     ...
     * }
     * }</pre>
     *
     * <h3>Respuesta generada:</h3>
     * <pre>{@code
     * HTTP/1.1 500 Internal Server Error
     * {
     *   "status": 500,
     *   "error": "Error interno del servidor",
     *   "timestamp": "2025-10-31T10:30:00",
     *   "detail": "Ha ocurrido un error inesperado. Por favor, contacte al administrador"
     * }
     * }</pre>
     *
     * <h3>¿Por qué 500 INTERNAL SERVER ERROR?</h3>
     * <p>500 es el código estándar para errores del servidor:</p>
     * <ul>
     *   <li>Indica que el servidor encontró una condición inesperada</li>
     *   <li>El problema NO está en la petición del cliente</li>
     *   <li>El servidor no pudo completar la operación</li>
     * </ul>
     *
     * @param ex La excepción genérica capturada
     * @return ResponseEntity con código 500 y ErrorDTO con mensaje genérico
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneric(Exception ex) {
        // No exponer detalles de excepciones en producción (principio de seguridad)
        String detalle = "Ha ocurrido un error inesperado. Por favor, contacte al administrador";

        ErrorDTO errorDTO = ErrorDTO.simple(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor", detalle);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }
}
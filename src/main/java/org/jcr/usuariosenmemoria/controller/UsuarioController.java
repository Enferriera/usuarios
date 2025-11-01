package org.jcr.usuariosenmemoria.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jcr.usuariosenmemoria.dto.UsuarioRequestDTO;
import org.jcr.usuariosenmemoria.dto.UsuarioResponseDTO;
import org.jcr.usuariosenmemoria.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que expone los endpoints de la API para gestión de usuarios.
 *
 * <p>Esta clase es la capa de presentación en la arquitectura de tres capas:
 * <b>Controller</b> → Service → Repository</p>
 *
 * <h3>Responsabilidades del Controller:</h3>
 * <ul>
 *   <li>Exponer endpoints HTTP REST para las operaciones CRUD</li>
 *   <li>Recibir y validar peticiones HTTP (request body y path variables)</li>
 *   <li>Delegar la lógica de negocio al Service</li>
 *   <li>Construir respuestas HTTP con el código de estado apropiado</li>
 *   <li>Manejar la serialización/deserialización JSON (automático con Spring)</li>
 * </ul>
 *
 * <h3>Anotaciones de Spring MVC utilizadas:</h3>
 * <ul>
 *   <li><b>@RestController:</b> Marca esta clase como controlador REST. Combina @Controller + @ResponseBody,
 *       lo que significa que todos los métodos retornan datos que se serializan automáticamente a JSON
 *       (no retornan vistas HTML).</li>
 *   <li><b>@RequestMapping("/api/usuarios"):</b> Define el prefijo de ruta base para todos los endpoints
 *       de este controlador. Todas las rutas serán relativas a "/api/usuarios".</li>
 *   <li><b>@RequiredArgsConstructor:</b> (Lombok) Genera automáticamente un constructor con el campo
 *       'service', permitiendo la inyección de dependencias por constructor.</li>
 * </ul>
 *
 * <h3>¿Qué es REST (Representational State Transfer)?</h3>
 * <p>REST es un estilo arquitectónico para APIs web que utiliza los métodos HTTP estándar:</p>
 * <ul>
 *   <li><b>POST:</b> Crear un nuevo recurso (usuario)</li>
 *   <li><b>GET:</b> Obtener/listar recursos existentes</li>
 *   <li><b>PUT:</b> Actualizar completamente un recurso existente</li>
 *   <li><b>DELETE:</b> Eliminar un recurso existente</li>
 * </ul>
 *
 * <h3>Convenciones REST implementadas:</h3>
 * <table border="1">
 *   <tr>
 *     <th>Operación</th>
 *     <th>Método HTTP</th>
 *     <th>Ruta</th>
 *     <th>Código de Estado</th>
 *   </tr>
 *   <tr>
 *     <td>Crear usuario</td>
 *     <td>POST</td>
 *     <td>/api/usuarios</td>
 *     <td>201 CREATED</td>
 *   </tr>
 *   <tr>
 *     <td>Listar usuarios</td>
 *     <td>GET</td>
 *     <td>/api/usuarios</td>
 *     <td>200 OK</td>
 *   </tr>
 *   <tr>
 *     <td>Buscar usuario</td>
 *     <td>GET</td>
 *     <td>/api/usuarios/{id}</td>
 *     <td>200 OK</td>
 *   </tr>
 *   <tr>
 *     <td>Actualizar usuario</td>
 *     <td>PUT</td>
 *     <td>/api/usuarios/{id}</td>
 *     <td>200 OK</td>
 *   </tr>
 *   <tr>
 *     <td>Eliminar usuario</td>
 *     <td>DELETE</td>
 *     <td>/api/usuarios/{id}</td>
 *     <td>204 NO CONTENT</td>
 *   </tr>
 * </table>
 *
 * <h3>¿Qué es ResponseEntity?</h3>
 * <p>ResponseEntity es una clase de Spring que representa toda la respuesta HTTP, incluyendo:</p>
 * <ul>
 *   <li><b>Código de estado HTTP:</b> 200 OK, 201 CREATED, 204 NO CONTENT, etc.</li>
 *   <li><b>Headers HTTP:</b> Content-Type, Location, etc. (opcionales)</li>
 *   <li><b>Cuerpo de la respuesta (body):</b> El objeto serializado a JSON</li>
 * </ul>
 *
 * <h3>Flujo completo de una petición HTTP:</h3>
 * <pre>{@code
 * 1. Cliente HTTP (navegador, Postman, cURL) → Petición HTTP
 * 2. Spring DispatcherServlet → Enruta la petición al método correcto del Controller
 * 3. Controller → Valida parámetros con @Valid
 * 4. Controller → Delega al Service
 * 5. Service → Ejecuta lógica de negocio
 * 6. Service → Retorna DTO
 * 7. Controller → Construye ResponseEntity con código de estado
 * 8. Spring → Serializa DTO a JSON automáticamente
 * 9. Cliente HTTP &lt;- Respuesta HTTP con JSON
 * }</pre>
 *
 * <h3>Inyección de Dependencias:</h3>
 * <p>Este controlador usa inyección por constructor (mejor práctica) para obtener el UsuarioService.
 * Spring automáticamente detecta e inyecta la implementación del servicio al crear este bean.</p>
 *
 * <h3>Ejemplo de petición/respuesta completa:</h3>
 * <pre>{@code
 * // Petición HTTP para crear usuario
 * POST /api/usuarios HTTP/1.1
 * Content-Type: application/json
 *
 * {
 *   "nombre": "Juan Pérez",
 *   "email": "juan@example.com",
 *   "password": "pass123456"
 * }
 *
 * // Respuesta HTTP del servidor
 * HTTP/1.1 201 Created
 * Content-Type: application/json
 *
 * {
 *   "id": 1,
 *   "nombre": "Juan Pérez",
 *   "email": "juan@example.com"
 * }
 * }</pre>
 *
 * @author Juan Cruz Robledo
 * @version 1.0
 * @since 2025
 * @see UsuarioService
 * @see UsuarioRequestDTO
 * @see UsuarioResponseDTO
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    /**
     * Servicio de lógica de negocio para gestión de usuarios.
     *
     * <p>Esta dependencia es inyectada automáticamente por Spring a través del
     * constructor generado por Lombok (@RequiredArgsConstructor).</p>
     *
     * <p>El modificador 'final' garantiza inmutabilidad y que siempre estará
     * presente después de la construcción del objeto.</p>
     */
    private final UsuarioService service;

    /**
     * Endpoint para crear un nuevo usuario.
     *
     * <h3>Especificaciones del endpoint:</h3>
     * <ul>
     *   <li><b>Método HTTP:</b> POST</li>
     *   <li><b>Ruta:</b> /api/usuarios</li>
     *   <li><b>Content-Type:</b> application/json</li>
     *   <li><b>Código de éxito:</b> 201 CREATED</li>
     * </ul>
     *
     * <h3>Anotaciones utilizadas:</h3>
     * <ul>
     *   <li><b>@PostMapping:</b> Mapea peticiones HTTP POST a este método. Equivalente a
     *       @RequestMapping(method = RequestMethod.POST). POST se usa para crear recursos.</li>
     *   <li><b>@RequestBody:</b> Indica que el parámetro 'dto' debe ser deserializado del
     *       cuerpo (body) de la petición HTTP. Spring automáticamente convierte el JSON
     *       recibido en un objeto UsuarioRequestDTO.</li>
     *   <li><b>@Valid:</b> Activa la validación de Bean Validation sobre el DTO. Spring
     *       verifica automáticamente las anotaciones @NotBlank, @Email, @Size definidas
     *       en UsuarioRequestDTO. Si alguna validación falla, lanza MethodArgumentNotValidException
     *       que es manejada por GlobalExceptionHandler.</li>
     * </ul>
     *
     * <h3>Flujo de ejecución:</h3>
     * <ol>
     *   <li>Cliente envía petición POST con JSON en el body</li>
     *   <li>Spring deserializa JSON → UsuarioRequestDTO</li>
     *   <li>@Valid activa validaciones (nombre, email, password)</li>
     *   <li>Si validación falla → HTTP 400 BAD REQUEST (manejado por GlobalExceptionHandler)</li>
     *   <li>Si validación pasa → Controller llama service.crearUsuario()</li>
     *   <li>Service crea el usuario en BD y retorna UsuarioResponseDTO</li>
     *   <li>Controller construye ResponseEntity con status 201 CREATED</li>
     *   <li>Spring serializa UsuarioResponseDTO → JSON</li>
     *   <li>Cliente recibe HTTP 201 con el usuario creado (incluyendo su ID)</li>
     * </ol>
     *
     * <h3>¿Por qué usar ResponseEntity.status(HttpStatus.CREATED)?</h3>
     * <p>REST define semánticas específicas para códigos HTTP:</p>
     * <ul>
     *   <li><b>200 OK:</b> Operación exitosa en general</li>
     *   <li><b>201 CREATED:</b> Recurso creado exitosamente (mejor práctica para POST)</li>
     * </ul>
     * <p>Usar 201 CREATED en lugar de 200 OK comunica explícitamente al cliente que
     * se creó un nuevo recurso, lo cual es semánticamente correcto.</p>
     *
     * <h3>Ejemplo de petición/respuesta:</h3>
     * <pre>{@code
     * // Petición
     * POST /api/usuarios HTTP/1.1
     * Content-Type: application/json
     *
     * {
     *   "nombre": "María García",
     *   "email": "maria@example.com",
     *   "password": "secure123"
     * }
     *
     * // Respuesta exitosa
     * HTTP/1.1 201 Created
     * Content-Type: application/json
     *
     * {
     *   "id": 5,
     *   "nombre": "María García",
     *   "email": "maria@example.com"
     * }
     *
     * // Respuesta con error de validación
     * HTTP/1.1 400 Bad Request
     * Content-Type: application/json
     *
     * {
     *   "message": "El nombre debe tener entre 2 y 100 caracteres",
     *   "timestamp": "2025-10-31T10:30:00"
     * }
     *
     * // Respuesta con email duplicado
     * HTTP/1.1 409 Conflict
     * Content-Type: application/json
     *
     * {
     *   "message": "El email ya está registrado",
     *   "timestamp": "2025-10-31T10:30:00"
     * }
     * }</pre>
     *
     * <h3>Manejo de errores:</h3>
     * <ul>
     *   <li>Validación fallida → 400 BAD REQUEST (MethodArgumentNotValidException)</li>
     *   <li>Email duplicado → 409 CONFLICT (DataIntegrityViolationException)</li>
     *   <li>Error interno → 500 INTERNAL SERVER ERROR (Exception)</li>
     * </ul>
     *
     * @param dto Datos del usuario a crear, validados con @Valid
     * @return ResponseEntity con código 201 CREATED y el usuario creado (incluyendo ID generado)
     */
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO usuarioCreado = service.crearUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }

    /**
     * Endpoint para listar todos los usuarios registrados.
     *
     * <h3>Especificaciones del endpoint:</h3>
     * <ul>
     *   <li><b>Método HTTP:</b> GET</li>
     *   <li><b>Ruta:</b> /api/usuarios</li>
     *   <li><b>Código de éxito:</b> 200 OK</li>
     * </ul>
     *
     * <h3>Anotaciones utilizadas:</h3>
     * <ul>
     *   <li><b>@GetMapping:</b> Mapea peticiones HTTP GET a este método. GET se usa para
     *       obtener recursos sin modificar el estado del servidor (operación idempotente y segura).</li>
     * </ul>
     *
     * <h3>Flujo de ejecución:</h3>
     * <ol>
     *   <li>Cliente envía petición GET sin body ni parámetros</li>
     *   <li>Controller llama service.listarUsuarios()</li>
     *   <li>Service obtiene todos los usuarios de la BD y los convierte a DTOs</li>
     *   <li>Controller usa ResponseEntity.ok() (código 200) con la lista</li>
     *   <li>Spring serializa la lista de DTOs → JSON array</li>
     *   <li>Cliente recibe HTTP 200 con array JSON de usuarios</li>
     * </ol>
     *
     * <h3>¿Por qué usar ResponseEntity.ok()?</h3>
     * <p>ResponseEntity.ok() es un método estático conveniente que:</p>
     * <ul>
     *   <li>Establece automáticamente el código de estado HTTP 200 OK</li>
     *   <li>Acepta el body como parámetro</li>
     *   <li>Equivale a: ResponseEntity.status(HttpStatus.OK).body(lista)</li>
     * </ul>
     *
     * <h3>Ejemplo de petición/respuesta:</h3>
     * <pre>{@code
     * // Petición
     * GET /api/usuarios HTTP/1.1
     *
     * // Respuesta exitosa con usuarios
     * HTTP/1.1 200 OK
     * Content-Type: application/json
     *
     * [
     *   {
     *     "id": 1,
     *     "nombre": "Juan Pérez",
     *     "email": "juan@example.com"
     *   },
     *   {
     *     "id": 2,
     *     "nombre": "Ana López",
     *     "email": "ana@example.com"
     *   }
     * ]
     *
     * // Respuesta exitosa sin usuarios (lista vacía)
     * HTTP/1.1 200 OK
     * Content-Type: application/json
     *
     * []
     * }</pre>
     *
     * <h3>Consideraciones:</h3>
     * <ul>
     *   <li>Si no hay usuarios, retorna lista vacía [] (no null ni error 404)</li>
     *   <li>Los passwords NO son incluidos en la respuesta (seguridad)</li>
     *   <li>Para aplicaciones con muchos datos, considerar implementar paginación</li>
     * </ul>
     *
     * @return ResponseEntity con código 200 OK y lista de todos los usuarios (puede ser vacía)
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarUsuarios());
    }

    /**
     * Endpoint para buscar un usuario específico por su ID.
     *
     * <h3>Especificaciones del endpoint:</h3>
     * <ul>
     *   <li><b>Método HTTP:</b> GET</li>
     *   <li><b>Ruta:</b> /api/usuarios/{id}</li>
     *   <li><b>Código de éxito:</b> 200 OK</li>
     * </ul>
     *
     * <h3>Anotaciones utilizadas:</h3>
     * <ul>
     *   <li><b>@GetMapping("/{id}"):</b> Mapea peticiones GET con un parámetro de ruta dinámico.
     *       El {id} es una variable de plantilla que captura el valor de la URL.</li>
     *   <li><b>@PathVariable:</b> Extrae el valor {id} de la URL y lo vincula al parámetro del método.
     *       Spring automáticamente convierte el String de la URL al tipo Long.</li>
     * </ul>
     *
     * <h3>¿Qué es una Path Variable?</h3>
     * <p>Las path variables son parte de la URL y representan identificadores de recursos:</p>
     * <pre>{@code
     * GET /api/usuarios/5    → id = 5
     * GET /api/usuarios/123  → id = 123
     * }</pre>
     *
     * <h3>Flujo de ejecución:</h3>
     * <ol>
     *   <li>Cliente envía petición GET a /api/usuarios/5</li>
     *   <li>Spring extrae "5" de la URL y lo convierte a Long</li>
     *   <li>Controller llama service.buscarUsuario(5L)</li>
     *   <li>Service busca en BD usando repository.findById()</li>
     *   <li>Si existe → Service retorna UsuarioResponseDTO</li>
     *   <li>Si NO existe → Service lanza IllegalArgumentException</li>
     *   <li>Controller construye ResponseEntity con código 200</li>
     *   <li>Spring serializa el DTO → JSON</li>
     *   <li>Cliente recibe HTTP 200 con el usuario</li>
     * </ol>
     *
     * <h3>Ejemplo de petición/respuesta:</h3>
     * <pre>{@code
     * // Petición
     * GET /api/usuarios/3 HTTP/1.1
     *
     * // Respuesta exitosa
     * HTTP/1.1 200 OK
     * Content-Type: application/json
     *
     * {
     *   "id": 3,
     *   "nombre": "Carlos Ruiz",
     *   "email": "carlos@example.com"
     * }
     *
     * // Respuesta cuando no existe
     * HTTP/1.1 400 Bad Request
     * Content-Type: application/json
     *
     * {
     *   "message": "No existe un usuario con el ID 3",
     *   "timestamp": "2025-10-31T10:30:00"
     * }
     * }</pre>
     *
     * <h3>Manejo de errores:</h3>
     * <ul>
     *   <li>ID no existe → 400 BAD REQUEST (IllegalArgumentException)</li>
     *   <li>ID inválido (ej: "abc") → 400 BAD REQUEST (conversión fallida)</li>
     * </ul>
     *
     * @param id Identificador único del usuario a buscar (extraído de la URL)
     * @return ResponseEntity con código 200 OK y los datos del usuario encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarUsuario(id));
    }

    /**
     * Endpoint para actualizar los datos de un usuario existente.
     *
     * <h3>Especificaciones del endpoint:</h3>
     * <ul>
     *   <li><b>Método HTTP:</b> PUT</li>
     *   <li><b>Ruta:</b> /api/usuarios/{id}</li>
     *   <li><b>Content-Type:</b> application/json</li>
     *   <li><b>Código de éxito:</b> 200 OK</li>
     * </ul>
     *
     * <h3>Anotaciones utilizadas:</h3>
     * <ul>
     *   <li><b>@PutMapping("/{id}"):</b> Mapea peticiones HTTP PUT a este método. PUT se usa
     *       para actualizar completamente un recurso existente (reemplazar todos sus campos).</li>
     *   <li><b>@PathVariable Long id:</b> Extrae el ID del usuario desde la URL.</li>
     *   <li><b>@RequestBody @Valid UsuarioRequestDTO dto:</b> Deserializa el JSON del body
     *       y valida los nuevos datos del usuario.</li>
     * </ul>
     *
     * <h3>PUT vs PATCH:</h3>
     * <p>Diferencias entre métodos de actualización:</p>
     * <ul>
     *   <li><b>PUT:</b> Reemplaza completamente el recurso (todos los campos deben enviarse)</li>
     *   <li><b>PATCH:</b> Actualización parcial (solo envía campos a modificar)</li>
     * </ul>
     * <p>Esta API usa PUT, por lo que el cliente debe enviar todos los campos (nombre, email, password)
     * incluso si solo quiere cambiar uno.</p>
     *
     * <h3>Flujo de ejecución:</h3>
     * <ol>
     *   <li>Cliente envía petición PUT a /api/usuarios/5 con JSON en el body</li>
     *   <li>Spring extrae id=5 de la URL y deserializa el JSON → UsuarioRequestDTO</li>
     *   <li>@Valid activa validaciones sobre el DTO</li>
     *   <li>Si validación falla → HTTP 400 BAD REQUEST</li>
     *   <li>Si validación pasa → Controller llama service.actualizarUsuario(5, dto)</li>
     *   <li>Service busca el usuario por ID</li>
     *   <li>Si no existe → lanza IllegalArgumentException (HTTP 400)</li>
     *   <li>Si existe → actualiza los campos y persiste en BD</li>
     *   <li>Service retorna UsuarioResponseDTO con los datos actualizados</li>
     *   <li>Controller construye ResponseEntity con código 200</li>
     *   <li>Cliente recibe HTTP 200 con el usuario actualizado</li>
     * </ol>
     *
     * <h3>Ejemplo de petición/respuesta:</h3>
     * <pre>{@code
     * // Petición
     * PUT /api/usuarios/2 HTTP/1.1
     * Content-Type: application/json
     *
     * {
     *   "nombre": "Ana María López",
     *   "email": "ana.lopez@example.com",
     *   "password": "newPassword123"
     * }
     *
     * // Respuesta exitosa
     * HTTP/1.1 200 OK
     * Content-Type: application/json
     *
     * {
     *   "id": 2,
     *   "nombre": "Ana María López",
     *   "email": "ana.lopez@example.com"
     * }
     *
     * // Respuesta cuando el ID no existe
     * HTTP/1.1 400 Bad Request
     * Content-Type: application/json
     *
     * {
     *   "message": "No existe un usuario con el ID 2",
     *   "timestamp": "2025-10-31T10:30:00"
     * }
     *
     * // Respuesta cuando el email ya está en uso por otro usuario
     * HTTP/1.1 409 Conflict
     * Content-Type: application/json
     *
     * {
     *   "message": "El email ya está registrado",
     *   "timestamp": "2025-10-31T10:30:00"
     * }
     * }</pre>
     *
     * <h3>Manejo de errores:</h3>
     * <ul>
     *   <li>ID no existe → 400 BAD REQUEST (IllegalArgumentException)</li>
     *   <li>Validación fallida → 400 BAD REQUEST (MethodArgumentNotValidException)</li>
     *   <li>Email duplicado → 409 CONFLICT (DataIntegrityViolationException)</li>
     * </ul>
     *
     * <h3>Consideraciones de seguridad:</h3>
     * <ul>
     *   <li>El password se actualiza en texto plano (en producción debería cifrarse)</li>
     *   <li>No se valida que el usuario tenga permisos para actualizar (sin autenticación)</li>
     * </ul>
     *
     * @param id ID del usuario a actualizar (extraído de la URL)
     * @param dto Nuevos datos del usuario, validados con @Valid
     * @return ResponseEntity con código 200 OK y el usuario con los datos actualizados
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(service.actualizarUsuario(id, dto));
    }

    /**
     * Endpoint para eliminar un usuario del sistema.
     *
     * <h3>Especificaciones del endpoint:</h3>
     * <ul>
     *   <li><b>Método HTTP:</b> DELETE</li>
     *   <li><b>Ruta:</b> /api/usuarios/{id}</li>
     *   <li><b>Código de éxito:</b> 204 NO CONTENT</li>
     * </ul>
     *
     * <h3>Anotaciones utilizadas:</h3>
     * <ul>
     *   <li><b>@DeleteMapping("/{id}"):</b> Mapea peticiones HTTP DELETE a este método.
     *       DELETE se usa para eliminar recursos del servidor.</li>
     *   <li><b>@PathVariable Long id:</b> Extrae el ID del usuario a eliminar desde la URL.</li>
     * </ul>
     *
     * <h3>¿Por qué retornar ResponseEntity&lt;Void&gt;?</h3>
     * <p>Al eliminar un recurso, REST recomienda retornar:</p>
     * <ul>
     *   <li><b>204 NO CONTENT:</b> Eliminación exitosa, sin cuerpo en la respuesta (mejor práctica)</li>
     *   <li><b>200 OK:</b> Eliminación exitosa, con cuerpo de confirmación (alternativa)</li>
     * </ul>
     * <p>Esta API usa 204 NO CONTENT porque es semánticamente más correcto: el recurso fue
     * eliminado y ya no hay contenido para retornar.</p>
     *
     * <h3>¿Qué significa ResponseEntity.noContent().build()?</h3>
     * <ul>
     *   <li><b>noContent():</b> Método estático que establece el código 204 NO CONTENT</li>
     *   <li><b>build():</b> Construye el ResponseEntity sin body (vacío)</li>
     *   <li><b>Void:</b> Indica explícitamente que no hay cuerpo en la respuesta</li>
     * </ul>
     *
     * <h3>Flujo de ejecución:</h3>
     * <ol>
     *   <li>Cliente envía petición DELETE a /api/usuarios/8</li>
     *   <li>Spring extrae id=8 de la URL</li>
     *   <li>Controller llama service.eliminarUsuario(8L)</li>
     *   <li>Service busca el usuario por ID</li>
     *   <li>Si no existe → lanza IllegalArgumentException (HTTP 400)</li>
     *   <li>Si existe → lo elimina de la BD usando repository.delete()</li>
     *   <li>Service retorna (método void)</li>
     *   <li>Controller construye ResponseEntity con código 204 sin body</li>
     *   <li>Cliente recibe HTTP 204 sin contenido</li>
     * </ol>
     *
     * <h3>Ejemplo de petición/respuesta:</h3>
     * <pre>{@code
     * // Petición
     * DELETE /api/usuarios/7 HTTP/1.1
     *
     * // Respuesta exitosa (sin body)
     * HTTP/1.1 204 No Content
     *
     * // Respuesta cuando el ID no existe
     * HTTP/1.1 400 Bad Request
     * Content-Type: application/json
     *
     * {
     *   "message": "No existe un usuario con el ID 7",
     *   "timestamp": "2025-10-31T10:30:00"
     * }
     * }</pre>
     *
     * <h3>Idempotencia de DELETE:</h3>
     * <p>DELETE debe ser idempotente según REST: realizar la misma petición múltiples veces
     * debe producir el mismo resultado. En esta implementación:</p>
     * <ul>
     *   <li><b>Primera llamada:</b> Elimina el usuario → HTTP 204</li>
     *   <li><b>Segunda llamada:</b> Usuario no existe → HTTP 400</li>
     * </ul>
     * <p>Esto NO es estrictamente idempotente. Una alternativa sería retornar 204 aunque
     * el usuario ya no exista, pero esta API prefiere validar explícitamente la existencia.</p>
     *
     * <h3>Manejo de errores:</h3>
     * <ul>
     *   <li>ID no existe → 400 BAD REQUEST (IllegalArgumentException)</li>
     *   <li>ID inválido → 400 BAD REQUEST (conversión fallida)</li>
     * </ul>
     *
     * <h3>Consideraciones:</h3>
     * <ul>
     *   <li>La eliminación es permanente (sin papelera de reciclaje)</li>
     *   <li>No se valida que el usuario tenga permisos para eliminar</li>
     *   <li>En aplicaciones reales, considerar "soft delete" (marcar como eliminado sin borrar)</li>
     * </ul>
     *
     * @param id ID del usuario a eliminar (extraído de la URL)
     * @return ResponseEntity con código 204 NO CONTENT y sin body
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}

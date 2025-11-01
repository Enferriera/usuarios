package org.jcr.usuariosenmemoria.service;

import lombok.RequiredArgsConstructor;
import org.jcr.usuariosenmemoria.dto.UsuarioRequestDTO;
import org.jcr.usuariosenmemoria.dto.UsuarioResponseDTO;
import org.jcr.usuariosenmemoria.model.Usuario;
import org.jcr.usuariosenmemoria.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio que contiene la lógica de negocio para la gestión de usuarios.
 *
 * <p>Esta clase es parte de la capa de servicio en la arquitectura de tres capas:
 * Controller → <b>Service</b> → Repository</p>
 *
 * <h3>Responsabilidades del Service:</h3>
 * <ul>
 *   <li>Implementar la lógica de negocio de la aplicación</li>
 *   <li>Validar reglas de negocio antes de persistir datos</li>
 *   <li>Coordinar operaciones entre múltiples repositorios (si fuera necesario)</li>
 *   <li>Transformar datos entre Entities y DTOs</li>
 *   <li>Manejar transacciones</li>
 * </ul>
 *
 * <h3>Anotaciones utilizadas:</h3>
 * <ul>
 *   <li><b>@Service:</b> Marca esta clase como un componente de servicio de Spring.
 *       Spring la detecta automáticamente y la registra como un bean.</li>
 *   <li><b>@RequiredArgsConstructor:</b> Lombok genera automáticamente un constructor
 *       con todos los campos marcados como 'final'. Esto permite la inyección de
 *       dependencias por constructor (mejor práctica).</li>
 * </ul>
 *
 * <h3>Inyección de Dependencias por Constructor:</h3>
 * <p>El patrón de inyección por constructor (facilitado por @RequiredArgsConstructor) tiene ventajas:</p>
 * <ul>
 *   <li>Los campos pueden ser 'final' (inmutables)</li>
 *   <li>Facilita las pruebas unitarias (no requiere reflexión)</li>
 *   <li>Hace explícitas las dependencias de la clase</li>
 *   <li>Evita NullPointerException (las dependencias siempre están inicializadas)</li>
 * </ul>
 *
 * <h3>Patrón de conversión Entity ↔ DTO:</h3>
 * <p>Este servicio sigue el patrón de convertir entre capas:</p>
 * <pre>
 * Request HTTP (JSON) → DTO → Entity → Base de Datos
 * Base de Datos → Entity → DTO → Response HTTP (JSON)
 * </pre>
 *
 * @author Juan Cruz Robledo
 * @version 1.0
 * @since 2025
 * @see UsuarioRepository
 * @see UsuarioRequestDTO
 * @see UsuarioResponseDTO
 */
@Service
@RequiredArgsConstructor
public class UsuarioService {

    /**
     * Repositorio para acceso a datos de usuarios.
     *
     * <p>Esta dependencia es inyectada automáticamente por Spring a través del
     * constructor generado por Lombok (@RequiredArgsConstructor).</p>
     *
     * <p>El modificador 'final' garantiza que esta dependencia es inmutable y
     * siempre estará presente después de la construcción del objeto.</p>
     */
    private final UsuarioRepository repository;

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * <h3>Flujo de ejecución:</h3>
     * <ol>
     *   <li>Recibe un UsuarioRequestDTO validado (validado por @Valid en el Controller)</li>
     *   <li>Construye una entidad Usuario usando el patrón Builder</li>
     *   <li>Persiste la entidad en la base de datos usando repository.save()</li>
     *   <li>Convierte la entidad persistida a UsuarioResponseDTO</li>
     *   <li>Retorna el DTO (que incluye el ID generado automáticamente)</li>
     * </ol>
     *
     * <h3>¿Por qué usar el Builder pattern?</h3>
     * <p>El patrón Builder (proporcionado por Lombok @Builder) permite crear objetos
     * de forma fluida y legible:</p>
     * <pre>{@code
     * Usuario usuario = Usuario.builder()
     *     .nombre("Juan")
     *     .email("juan@example.com")
     *     .password("pass123")
     *     .build();
     * }</pre>
     *
     * <h3>@Transactional - Control de Transacciones:</h3>
     * <p>Este método está anotado con @Transactional para garantizar atomicidad en toda la operación:</p>
     * <ul>
     *   <li>Si la construcción, persistencia y conversión a DTO son exitosas, la transacción se confirma (commit)</li>
     *   <li>Si ocurre cualquier excepción en cualquier punto, todos los cambios se revierten (rollback)</li>
     *   <li>Previene estados inconsistentes donde el usuario se guarda pero falla alguna operación posterior</li>
     *   <li>Garantiza que todas las operaciones de BD se ejecuten como una unidad atómica</li>
     * </ul>
     *
     * <h3>¿Por qué agregar @Transactional si save() ya es transaccional?</h3>
     * <p>Aunque repository.save() es transaccional por defecto, agregar @Transactional aquí:</p>
     * <ul>
     *   <li>Hace explícita la naturaleza transaccional del método (mejor documentación del código)</li>
     *   <li>Extiende la transacción a toda la operación, incluyendo la conversión a DTO</li>
     *   <li>Permite agregar lógica adicional en el futuro sin preocuparse por transacciones</li>
     *   <li>Sigue las mejores prácticas de Spring para métodos de servicio con múltiples pasos</li>
     * </ul>
     *
     * <h3>Manejo de errores:</h3>
     * <p>Si el email ya existe, repository.save() lanzará una DataIntegrityViolationException
     * debido al constraint UNIQUE en la columna email. Esta excepción será capturada por
     * GlobalExceptionHandler y convertida en una respuesta HTTP 409 CONFLICT.</p>
     *
     * @param usuario DTO con los datos del usuario a crear (ya validado)
     * @return UsuarioResponseDTO con los datos del usuario creado, incluyendo el ID generado
     * @throws org.springframework.dao.DataIntegrityViolationException si el email ya existe
     */
    @Transactional
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO usuario) {
        // 1. Transformar DTO a Entity usando Builder pattern
        Usuario usuarioCreado = Usuario.builder()
                .nombre(usuario.nombre())
                .email(usuario.email())
                .password(usuario.password())
                .build();

        // 2. Persistir en BD y transformar Entity a DTO en una sola línea
        return UsuarioResponseDTO.fromEntity(repository.save(usuarioCreado));
    }

    /**
     * Lista todos los usuarios registrados en el sistema.
     *
     * <h3>Flujo de ejecución:</h3>
     * <ol>
     *   <li>Obtiene todas las entidades Usuario de la base de datos</li>
     *   <li>Convierte la lista a un Stream para procesamiento funcional</li>
     *   <li>Transforma cada entidad Usuario a UsuarioResponseDTO</li>
     *   <li>Recolecta los DTOs en una lista inmutable</li>
     * </ol>
     *
     * <h3>Programación Funcional con Streams:</h3>
     * <p>Este método usa la API de Streams de Java para transformar datos de forma declarativa:</p>
     * <pre>{@code
     * repository.findAll()              // List<Usuario>
     *     .stream()                     // Stream<Usuario>
     *     .map(UsuarioResponseDTO::fromEntity)  // Stream<UsuarioResponseDTO>
     *     .toList();                    // List<UsuarioResponseDTO>
     * }</pre>
     *
     * <h3>Method Reference:</h3>
     * <p>La expresión <code>UsuarioResponseDTO::fromEntity</code> es una referencia a método,
     * equivalente a la lambda: <code>usuario -> UsuarioResponseDTO.fromEntity(usuario)</code></p>
     *
     * <h3>Consideraciones de rendimiento:</h3>
     * <p>Si la tabla tiene muchos usuarios, considerar implementar paginación usando
     * <code>repository.findAll(Pageable pageable)</code> en lugar de cargar todos los
     * registros en memoria.</p>
     *
     * @return Lista de UsuarioResponseDTO con todos los usuarios (lista vacía si no hay usuarios)
     */
    public List<UsuarioResponseDTO> listarUsuarios() {
        return repository.findAll()
                .stream()
                .map(UsuarioResponseDTO::fromEntity)
                .toList();
    }

    /**
     * Busca un usuario específico por su ID.
     *
     * <h3>Flujo de ejecución:</h3>
     * <ol>
     *   <li>Busca el usuario en la BD por su ID usando repository.findById()</li>
     *   <li>findById() retorna un Optional&lt;Usuario&gt; (puede o no contener un usuario)</li>
     *   <li>orElseThrow() lanza una excepción si el Optional está vacío</li>
     *   <li>Si se encuentra, convierte la entidad a DTO y la retorna</li>
     * </ol>
     *
     * <h3>Optional y manejo de ausencia:</h3>
     * <p>Optional es una clase contenedor introducida en Java 8 que puede contener o no un valor.
     * Evita el uso de null y hace explícito cuando un valor puede estar ausente.</p>
     *
     * <pre>{@code
     * Optional<Usuario> opcional = repository.findById(id);
     *
     * // Forma tradicional con if:
     * if (opcional.isPresent()) {
     *     Usuario usuario = opcional.get();
     *     // usar usuario
     * } else {
     *     throw new IllegalArgumentException("No encontrado");
     * }
     *
     * // Forma funcional (usada aquí):
     * Usuario usuario = opcional.orElseThrow(() -> new IllegalArgumentException("No encontrado"));
     * }</pre>
     *
     * <h3>¿Por qué IllegalArgumentException?</h3>
     * <p>Se usa IllegalArgumentException porque indica que el argumento (ID) proporcionado
     * no es válido (no existe en la BD). Esta excepción es capturada por GlobalExceptionHandler
     * y convertida en HTTP 400 BAD REQUEST.</p>
     *
     * @param id Identificador único del usuario a buscar
     * @return UsuarioResponseDTO con los datos del usuario encontrado
     * @throws IllegalArgumentException si no existe un usuario con ese ID
     */
    public UsuarioResponseDTO buscarUsuario(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con el ID " + id));
        return UsuarioResponseDTO.fromEntity(usuario);
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * <h3>Flujo de ejecución:</h3>
     * <ol>
     *   <li>Busca el usuario por ID (lanza excepción si no existe)</li>
     *   <li>Actualiza los campos del usuario con los nuevos valores del DTO</li>
     *   <li>Persiste los cambios en la base de datos</li>
     *   <li>Convierte la entidad actualizada a DTO y la retorna</li>
     * </ol>
     *
     * <h3>@Transactional - Control de Transacciones:</h3>
     * <p>Esta anotación garantiza que toda la operación se ejecute dentro de una transacción:</p>
     * <ul>
     *   <li>Si todo funciona correctamente, los cambios se confirman (commit)</li>
     *   <li>Si ocurre una excepción, todos los cambios se revierten (rollback)</li>
     *   <li>Previene estados inconsistentes en la base de datos</li>
     * </ul>
     *
     * <h3>¿Por qué es necesario @Transactional aquí?</h3>
     * <p>Aunque repository.save() es transaccional, esta operación implica:</p>
     * <ol>
     *   <li>findById() - Una consulta SELECT</li>
     *   <li>Modificación de campos en memoria</li>
     *   <li>save() - Una consulta UPDATE</li>
     * </ol>
     * <p>@Transactional asegura que estas operaciones se ejecuten como una unidad atómica.</p>
     *
     * <h3>Prevención de Race Conditions:</h3>
     * <p>Este método previene condiciones de carrera porque:</p>
     * <ul>
     *   <li>Primero verifica la existencia (findById)</li>
     *   <li>Luego actualiza usando la misma entidad obtenida</li>
     *   <li>Todo dentro de la misma transacción</li>
     * </ul>
     *
     * <h3>Manejo de email duplicado:</h3>
     * <p>Si se intenta cambiar el email a uno que ya existe, repository.save() lanzará
     * DataIntegrityViolationException (HTTP 409 CONFLICT).</p>
     *
     * @param id ID del usuario a actualizar
     * @param dto DTO con los nuevos datos del usuario (ya validado)
     * @return UsuarioResponseDTO con los datos actualizados
     * @throws IllegalArgumentException si no existe un usuario con ese ID
     * @throws org.springframework.dao.DataIntegrityViolationException si el nuevo email ya existe
     */
    @Transactional
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        // 1. Buscar el usuario existente (lanza excepción si no existe)
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con el ID " + id));

        // 2. Actualizar los campos con los nuevos valores
        usuario.setNombre(dto.nombre());
        usuario.setEmail(dto.email());
        usuario.setPassword(dto.password());

        // 3. Persistir cambios y retornar DTO
        return UsuarioResponseDTO.fromEntity(repository.save(usuario));
    }

    /**
     * Elimina un usuario del sistema.
     *
     * <h3>Flujo de ejecución:</h3>
     * <ol>
     *   <li>Busca el usuario por ID (lanza excepción si no existe)</li>
     *   <li>Elimina el usuario de la base de datos</li>
     * </ol>
     *
     * <h3>@Transactional - Garantía de Atomicidad:</h3>
     * <p>La anotación @Transactional es crítica aquí porque:</p>
     * <ul>
     *   <li>Asegura que buscar y eliminar sea una operación atómica</li>
     *   <li>Previene race conditions (otro thread no puede eliminar entre medio)</li>
     *   <li>Si la eliminación falla, la transacción se revierte</li>
     * </ul>
     *
     * <h3>¿Por qué usar findById() + delete() en lugar de deleteById()?</h3>
     * <p>Comparación de enfoques:</p>
     * <table border="1">
     *   <tr>
     *     <th>Enfoque</th>
     *     <th>Ventajas</th>
     *     <th>Desventajas</th>
     *   </tr>
     *   <tr>
     *     <td>deleteById()</td>
     *     <td>Más conciso (una línea)</td>
     *     <td>No verifica existencia, falla silenciosamente</td>
     *   </tr>
     *   <tr>
     *     <td>findById() + delete()</td>
     *     <td>Valida existencia, error explícito</td>
     *     <td>Requiere dos operaciones de BD</td>
     *   </tr>
     * </table>
     *
     * <p><b>Decisión de diseño:</b> Usamos findById() + delete() porque queremos retornar
     * un error HTTP 400 si el usuario no existe, en lugar de fallar silenciosamente.</p>
     *
     * <h3>Prevención de Race Conditions:</h3>
     * <p>El patrón usado previene el problema TOCTOU (Time-Of-Check-Time-Of-Use):</p>
     * <ul>
     *   <li><b>INCORRECTO:</b> existsById() luego deleteById() - Otro thread puede eliminar entre medio</li>
     *   <li><b>CORRECTO:</b> findById() luego delete(entidad) - Usa la misma entidad obtenida</li>
     * </ul>
     *
     * @param id ID del usuario a eliminar
     * @throws IllegalArgumentException si no existe un usuario con ese ID
     */
    @Transactional
    public void eliminarUsuario(Long id) {
        // Buscar usuario (valida existencia)
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con el ID " + id));

        // Eliminar usando la entidad obtenida (previene race conditions)
        repository.delete(usuario);
    }

}
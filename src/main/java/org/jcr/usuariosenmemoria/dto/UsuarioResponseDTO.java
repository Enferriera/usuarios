package org.jcr.usuariosenmemoria.dto;

import org.jcr.usuariosenmemoria.model.Usuario;

/**
 * DTO (Data Transfer Object) para enviar datos de usuario en las respuestas HTTP.
 *
 * <p>Este record se utiliza para retornar información del usuario al cliente en todas
 * las operaciones de la API (GET, POST, PUT). Es la representación pública de un usuario.</p>
 *
 * <h3>Diferencias con la Entidad Usuario:</h3>
 * <p>Este DTO <b>NO incluye el campo password</b> por razones de seguridad. La contraseña
 * nunca debe ser expuesta en las respuestas de la API.</p>
 *
 * <h3>Ventajas de usar un DTO de respuesta separado:</h3>
 * <ul>
 *   <li><b>Seguridad:</b> Controla exactamente qué información se expone al cliente</li>
 *   <li><b>Estabilidad de API:</b> Cambios en la entidad no afectan automáticamente la API</li>
 *   <li><b>Optimización:</b> Puede incluir solo los campos necesarios para cada endpoint</li>
 *   <li><b>Transformación:</b> Permite formatear o combinar datos antes de enviarlos</li>
 * </ul>
 *
 * <h3>Patrón Factory Method:</h3>
 * <p>El método estático fromEntity() implementa el patrón Factory Method para crear
 * instancias de UsuarioResponseDTO a partir de entidades Usuario. Este patrón centraliza
 * la lógica de conversión y hace el código más mantenible.</p>
 *
 * <h3>Ejemplo de flujo completo:</h3>
 * <pre>{@code
 * // 1. El controlador recibe una petición GET /api/usuarios/1
 * // 2. El servicio busca el usuario en la BD y obtiene una entidad Usuario
 * Usuario usuario = repository.findById(1L).get();
 *
 * // 3. El servicio convierte la entidad a DTO usando el factory method
 * UsuarioResponseDTO dto = UsuarioResponseDTO.fromEntity(usuario);
 *
 * // 4. El controlador retorna el DTO (Spring lo convierte a JSON automáticamente)
 * // JSON enviado al cliente:
 * {
 *   "id": 1,
 *   "nombre": "Juan Pérez",
 *   "email": "juan@example.com"
 *   // Nota: password NO está incluido
 * }
 * }</pre>
 *
 * @param id       Identificador único del usuario
 * @param nombre   Nombre completo del usuario
 * @param email    Correo electrónico del usuario
 *
 * @author Juan Cruz Robledo
 * @version 1.0
 * @since 2025
 */
public record UsuarioResponseDTO(

        /**
         * Identificador único del usuario.
         *
         * <p>Este es el ID generado automáticamente por la base de datos cuando se
         * crea el usuario. Es usado para identificar de forma única cada usuario en
         * operaciones de consulta, actualización y eliminación.</p>
         *
         * <p><b>Tipo Long:</b> Permite hasta 9,223,372,036,854,775,807 usuarios únicos,
         * suficiente para cualquier aplicación práctica.</p>
         */
        Long id,

        /**
         * Nombre completo del usuario.
         *
         * <p>Este campo refleja el valor almacenado en la base de datos sin ninguna
         * transformación adicional.</p>
         */
        String nombre,

        /**
         * Correo electrónico del usuario.
         *
         * <p>Este campo refleja el valor almacenado en la base de datos. Es único
         * en todo el sistema (garantizado por el constraint UNIQUE de la BD).</p>
         */
        String email

) {
    /**
     * Factory method que crea un UsuarioResponseDTO a partir de una entidad Usuario.
     *
     * <p>Este método estático implementa el patrón Factory Method para encapsular
     * la lógica de conversión de Entity a DTO. Centralizar esta lógica tiene ventajas:</p>
     * <ul>
     *   <li>Si cambia la estructura de Usuario o UsuarioResponseDTO, solo se modifica aquí</li>
     *   <li>Facilita agregar transformaciones o validaciones en un solo lugar</li>
     *   <li>Hace el código del servicio más limpio y legible</li>
     *   <li>Sigue el principio DRY (Don't Repeat Yourself)</li>
     * </ul>
     *
     * <h3>¿Por qué excluir la contraseña?</h3>
     * <p>La contraseña es información sensible que:</p>
     * <ul>
     *   <li>No debe exponerse en respuestas HTTP (riesgo de seguridad)</li>
     *   <li>No es necesaria para la representación del usuario en el cliente</li>
     *   <li>Debe permanecer privada incluso si está cifrada</li>
     * </ul>
     *
     * <h3>Uso típico en el Service:</h3>
     * <pre>{@code
     * public UsuarioResponseDTO buscarUsuario(Long id) {
     *     Usuario usuario = repository.findById(id)
     *         .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
     *     return UsuarioResponseDTO.fromEntity(usuario);
     * }
     * }</pre>
     *
     * @param usuario La entidad Usuario a convertir (no debe ser null)
     * @return UsuarioResponseDTO con id, nombre y email (sin password)
     */
    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail()
                // Nota: Intencionalmente NO incluye usuario.getPassword()
        );
    }

}

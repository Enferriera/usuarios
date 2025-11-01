package org.jcr.usuariosenmemoria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA que representa un Usuario en la base de datos.
 *
 * <p>Esta clase es una entidad persistente que se mapea a la tabla "usuarios" en la base de datos.
 * Utiliza JPA (Java Persistence API) para el mapeo objeto-relacional (ORM) y Lombok para
 * reducir código boilerplate.</p>
 *
 * <h3>Anotaciones de Lombok:</h3>
 * <ul>
 *   <li><b>@Data:</b> Genera automáticamente getters, setters, toString(), equals() y hashCode()</li>
 *   <li><b>@AllArgsConstructor:</b> Genera un constructor con todos los campos como parámetros</li>
 *   <li><b>@NoArgsConstructor:</b> Genera un constructor sin parámetros (requerido por JPA)</li>
 *   <li><b>@Builder:</b> Implementa el patrón Builder para crear instancias de forma fluida</li>
 * </ul>
 *
 * <h3>Anotaciones JPA:</h3>
 * <ul>
 *   <li><b>@Entity:</b> Marca la clase como una entidad JPA persistible</li>
 *   <li><b>@Table:</b> Especifica el nombre de la tabla en la base de datos</li>
 * </ul>
 *
 * <h3>Ejemplo de uso con Builder:</h3>
 * <pre>{@code
 * Usuario usuario = Usuario.builder()
 *     .nombre("Juan Pérez")
 *     .email("juan@example.com")
 *     .password("miPassword123")
 *     .build();
 * }</pre>
 *
 * @author Juan Cruz Robledo
 * @version 1.0
 * @since 2025
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    /**
     * Identificador único del usuario (Clave Primaria).
     *
     * <p>Anotaciones:</p>
     * <ul>
     *   <li><b>@Id:</b> Indica que este campo es la clave primaria de la entidad</li>
     *   <li><b>@GeneratedValue:</b> Especifica que el valor se genera automáticamente</li>
     *   <li><b>GenerationType.IDENTITY:</b> Usa la estrategia de auto-incremento de la BD</li>
     * </ul>
     *
     * <p>El valor es asignado automáticamente por la base de datos al persistir la entidad.
     * No debe ser establecido manualmente al crear un nuevo usuario.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre completo del usuario.
     *
     * <p>Constraints de base de datos:</p>
     * <ul>
     *   <li><b>nullable = false:</b> Campo obligatorio, no puede ser NULL</li>
     *   <li><b>length = 100:</b> Máximo 100 caracteres</li>
     * </ul>
     *
     * <p>Validaciones adicionales se realizan a nivel de DTO con:</p>
     * <ul>
     *   <li>@NotBlank: No puede estar vacío</li>
     *   <li>@Size(min=2, max=100): Debe tener entre 2 y 100 caracteres</li>
     * </ul>
     */
    @Column(nullable = false, length = 100)
    private String nombre;

    /**
     * Dirección de correo electrónico del usuario.
     *
     * <p>Constraints de base de datos:</p>
     * <ul>
     *   <li><b>unique = true:</b> Debe ser único en toda la tabla (no puede haber duplicados)</li>
     *   <li><b>nullable = false:</b> Campo obligatorio, no puede ser NULL</li>
     *   <li><b>length = 255:</b> Máximo 255 caracteres</li>
     * </ul>
     *
     * <p>La constraint UNIQUE garantiza a nivel de base de datos que no puedan existir dos
     * usuarios con el mismo email. Si se intenta insertar un email duplicado, se lanza una
     * DataIntegrityViolationException que es capturada por GlobalExceptionHandler.</p>
     *
     * <p>Validaciones adicionales a nivel de DTO:</p>
     * <ul>
     *   <li>@NotBlank: No puede estar vacío</li>
     *   <li>@Email: Debe tener formato válido de email</li>
     * </ul>
     */
    @Column(unique = true, nullable = false, length = 255)
    private String email;

    /**
     * Contraseña del usuario.
     *
     * <p><b>IMPORTANTE:</b> En esta aplicación de demostración, la contraseña se almacena
     * en texto plano. En un entorno de producción, SIEMPRE debe estar cifrada usando
     * algoritmos como BCrypt, Argon2 o PBKDF2.</p>
     *
     * <p>Constraints de base de datos:</p>
     * <ul>
     *   <li><b>nullable = false:</b> Campo obligatorio, no puede ser NULL</li>
     *   <li><b>length = 255:</b> Máximo 255 caracteres</li>
     * </ul>
     *
     * <p>Validaciones a nivel de DTO:</p>
     * <ul>
     *   <li>@NotBlank: No puede estar vacío</li>
     *   <li>@Size(min=6): Debe tener al menos 6 caracteres</li>
     * </ul>
     *
     * <p><b>Seguridad:</b> Este campo NO es expuesto en las respuestas de la API.
     * El UsuarioResponseDTO excluye este campo para proteger la información sensible.</p>
     */
    @Column(nullable = false, length = 255)
    private String password;

}

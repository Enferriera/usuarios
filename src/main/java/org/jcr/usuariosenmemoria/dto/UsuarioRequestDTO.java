package org.jcr.usuariosenmemoria.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) para recibir datos de usuario en las peticiones HTTP.
 *
 * <p>Este record se utiliza para:</p>
 * <ul>
 *   <li>Recibir datos al crear un nuevo usuario (POST /api/usuarios)</li>
 *   <li>Recibir datos al actualizar un usuario existente (PUT /api/usuarios/{id})</li>
 * </ul>
 *
 * <h3>¿Qué es un Record en Java?</h3>
 * <p>Los records (introducidos en Java 14) son clases inmutables diseñadas para
 * transportar datos. Automáticamente generan:</p>
 * <ul>
 *   <li>Constructor con todos los campos</li>
 *   <li>Métodos getter (nombre(), email(), password())</li>
 *   <li>Métodos equals(), hashCode() y toString()</li>
 *   <li>Todos los campos son final (inmutables)</li>
 * </ul>
 *
 * <h3>¿Por qué usar DTOs?</h3>
 * <p>Los DTOs separan la capa de presentación (API) de la capa de persistencia (Entidades).
 * Ventajas:</p>
 * <ul>
 *   <li><b>Seguridad:</b> No expone todos los campos de la entidad</li>
 *   <li><b>Validación:</b> Permite validar datos antes de procesarlos</li>
 *   <li><b>Flexibilidad:</b> La estructura de la API puede diferir de la BD</li>
 *   <li><b>Versionamiento:</b> Facilita mantener múltiples versiones de la API</li>
 * </ul>
 *
 * <h3>Validaciones de Jakarta Bean Validation:</h3>
 * <p>Las anotaciones de validación son procesadas automáticamente por Spring cuando se usa
 * @Valid en el controlador. Si alguna validación falla, Spring lanza una
 * MethodArgumentNotValidException que es capturada por GlobalExceptionHandler.</p>
 *
 * <h3>Ejemplo de uso:</h3>
 * <pre>{@code
 * // JSON recibido en la petición:
 * {
 *   "nombre": "Ana García",
 *   "email": "ana@example.com",
 *   "password": "securePass123"
 * }
 *
 * // Spring lo convierte automáticamente a:
 * UsuarioRequestDTO dto = new UsuarioRequestDTO(
 *     "Ana García",
 *     "ana@example.com",
 *     "securePass123"
 * );
 * }</pre>
 *
 * @param nombre    Nombre completo del usuario
 * @param email     Correo electrónico del usuario
 * @param password  Contraseña del usuario
 *
 * @author Juan Cruz Robledo
 * @version 1.0
 * @since 2025
 */
public record UsuarioRequestDTO(

        /**
         * Nombre completo del usuario.
         *
         * <p><b>Validaciones:</b></p>
         * <ul>
         *   <li><b>@NotBlank:</b> No puede ser null, vacío o contener solo espacios en blanco.
         *       Mensaje de error: "El nombre no puede estar vacío"</li>
         *   <li><b>@Size(min=2, max=100):</b> Debe tener entre 2 y 100 caracteres.
         *       Mensaje de error: "El nombre debe tener entre 2 y 100 caracteres"</li>
         * </ul>
         *
         * <p><b>Ejemplos válidos:</b> "Juan Pérez", "Ana María López García"</p>
         * <p><b>Ejemplos inválidos:</b> "", " ", "A" (muy corto), nombre de 101+ caracteres</p>
         */
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,

        /**
         * Correo electrónico del usuario.
         *
         * <p><b>Validaciones:</b></p>
         * <ul>
         *   <li><b>@NotBlank:</b> No puede ser null, vacío o contener solo espacios en blanco.
         *       Mensaje de error: "El email no puede estar vacío"</li>
         *   <li><b>@Email:</b> Debe tener un formato válido de email (contener @ y dominio).
         *       Mensaje de error: "Formato de email inválido"</li>
         * </ul>
         *
         * <p><b>Validación adicional:</b> El constraint UNIQUE a nivel de base de datos
         * garantiza que no puedan existir dos usuarios con el mismo email. Si se intenta
         * usar un email duplicado, se retorna HTTP 409 CONFLICT.</p>
         *
         * <p><b>Ejemplos válidos:</b> "usuario@example.com", "ana.lopez@gmail.com"</p>
         * <p><b>Ejemplos inválidos:</b> "", "correo", "correo@", "@dominio.com"</p>
         */
        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "Formato de email inválido")
        String email,

        /**
         * Contraseña del usuario.
         *
         * <p><b>Validaciones:</b></p>
         * <ul>
         *   <li><b>@NotBlank:</b> No puede ser null, vacía o contener solo espacios en blanco.
         *       Mensaje de error: "La contraseña no puede estar vacía"</li>
         *   <li><b>@Size(min=6):</b> Debe tener al menos 6 caracteres.
         *       Mensaje de error: "La contraseña debe tener al menos 6 caracteres"</li>
         * </ul>
         *
         * <p><b>NOTA DE SEGURIDAD:</b> En una aplicación real de producción, la contraseña
         * debería cumplir requisitos más estrictos (mayúsculas, minúsculas, números, caracteres
         * especiales) y ser cifrada antes de almacenarse en la base de datos.</p>
         *
         * <p><b>Ejemplos válidos:</b> "password123", "MiClave2024"</p>
         * <p><b>Ejemplos inválidos:</b> "", "12345" (muy corta)</p>
         */
        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password

) { }

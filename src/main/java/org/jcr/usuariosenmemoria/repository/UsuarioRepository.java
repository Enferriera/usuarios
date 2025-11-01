package org.jcr.usuariosenmemoria.repository;

import org.jcr.usuariosenmemoria.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para operaciones de acceso a datos de la entidad Usuario.
 *
 * <p>Esta interface extiende JpaRepository, lo que proporciona automáticamente métodos CRUD
 * sin necesidad de implementación. Spring Data JPA genera la implementación en tiempo de ejecución.</p>
 *
 * <h3>¿Qué es un Repository en Spring Data JPA?</h3>
 * <p>El patrón Repository abstrae la lógica de acceso a datos, proporcionando una interfaz
 * simple para interactuar con la base de datos sin escribir código SQL manual.</p>
 *
 * <h3>¿Por qué usar @Repository?</h3>
 * <ul>
 *   <li><b>Marcador semántico:</b> Indica que esta interface maneja la persistencia de datos</li>
 *   <li><b>Manejo de excepciones:</b> Spring traduce excepciones específicas de la BD a su jerarquía de excepciones</li>
 *   <li><b>Component Scanning:</b> Permite que Spring detecte automáticamente este bean</li>
 * </ul>
 *
 * <h3>Métodos heredados de JpaRepository&lt;Usuario, Long&gt;:</h3>
 * <p>Al extender JpaRepository, esta interface hereda automáticamente métodos como:</p>
 * <ul>
 *   <li><b>save(Usuario u):</b> Inserta o actualiza un usuario en la BD</li>
 *   <li><b>findById(Long id):</b> Busca un usuario por su ID, retorna Optional&lt;Usuario&gt;</li>
 *   <li><b>findAll():</b> Retorna lista de todos los usuarios</li>
 *   <li><b>deleteById(Long id):</b> Elimina un usuario por su ID</li>
 *   <li><b>delete(Usuario u):</b> Elimina un usuario específico</li>
 *   <li><b>existsById(Long id):</b> Verifica si existe un usuario con ese ID</li>
 *   <li><b>count():</b> Retorna el número total de usuarios</li>
 * </ul>
 *
 * <h3>Parámetros Genéricos:</h3>
 * <ul>
 *   <li><b>Usuario:</b> El tipo de entidad que maneja este repositorio</li>
 *   <li><b>Long:</b> El tipo del identificador (ID) de la entidad</li>
 * </ul>
 *
 * <h3>Ejemplo de uso en el Service:</h3>
 * <pre>{@code
 * @Service
 * public class UsuarioService {
 *     private final UsuarioRepository repository;
 *
 *     // Buscar usuario por ID
 *     public Usuario buscarUsuario(Long id) {
 *         return repository.findById(id)
 *             .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
 *     }
 *
 *     // Guardar nuevo usuario
 *     public Usuario crearUsuario(Usuario usuario) {
 *         return repository.save(usuario);
 *     }
 *
 *     // Listar todos
 *     public List<Usuario> listarTodos() {
 *         return repository.findAll();
 *     }
 * }
 * }</pre>
 *
 * <h3>Métodos de Consulta Personalizados (Query Methods):</h3>
 * <p>Spring Data JPA permite agregar métodos personalizados basándose en convenciones de nombres.
 * Ejemplos que podrían agregarse:</p>
 * <pre>{@code
 * // Buscar por email (Spring genera automáticamente la consulta SQL)
 * Optional<Usuario> findByEmail(String email);
 *
 * // Buscar por nombre que contenga un texto
 * List<Usuario> findByNombreContaining(String texto);
 *
 * // Buscar por email y nombre
 * Optional<Usuario> findByEmailAndNombre(String email, String nombre);
 *
 * // Contar usuarios con un nombre específico
 * long countByNombre(String nombre);
 * }</pre>
 *
 * <h3>¿Dónde está la implementación?</h3>
 * <p>Spring Data JPA genera automáticamente la implementación de esta interface en tiempo de
 * ejecución usando proxies dinámicos. No es necesario escribir ninguna clase que implemente
 * esta interface.</p>
 *
 * <h3>Transacciones:</h3>
 * <p>Todos los métodos de JpaRepository son transaccionales por defecto. Spring maneja
 * automáticamente las transacciones, incluyendo rollback en caso de excepciones.</p>
 *
 * @author Juan Cruz Robledo
 * @version 1.0
 * @since 2025
 * @see Usuario
 * @see JpaRepository
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // No se requieren métodos adicionales para esta aplicación simple.
    // JpaRepository ya proporciona todos los métodos CRUD necesarios.

    // Ejemplos de métodos personalizados que podrían agregarse si fueran necesarios:
    //
    // Optional<Usuario> findByEmail(String email);
    // List<Usuario> findByNombreContaining(String texto);
    // boolean existsByEmail(String email);
}

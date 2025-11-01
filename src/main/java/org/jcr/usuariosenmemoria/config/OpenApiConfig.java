package org.jcr.usuariosenmemoria.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI/Swagger para documentación interactiva de la API.
 *
 * <p>Esta clase configura la documentación automática de la API REST usando
 * Swagger/OpenAPI, que genera una interfaz web interactiva donde se pueden
 * visualizar y probar todos los endpoints.</p>
 *
 * <h3>¿Qué es OpenAPI/Swagger?</h3>
 * <ul>
 *   <li><b>OpenAPI:</b> Especificación estándar para describir APIs REST (antes llamada Swagger)</li>
 *   <li><b>Swagger UI:</b> Interfaz web interactiva que visualiza la especificación OpenAPI</li>
 *   <li><b>SpringDoc:</b> Librería que integra OpenAPI con Spring Boot automáticamente</li>
 * </ul>
 *
 * <h3>¿Para qué sirve la documentación interactiva?</h3>
 * <ul>
 *   <li><b>Exploración:</b> Desarrolladores pueden ver todos los endpoints disponibles</li>
 *   <li><b>Pruebas:</b> Permite hacer peticiones HTTP directamente desde el navegador</li>
 *   <li><b>Validación:</b> Muestra los modelos de datos (DTOs) con sus validaciones</li>
 *   <li><b>Ejemplos:</b> Proporciona ejemplos de peticiones y respuestas</li>
 *   <li><b>Comunicación:</b> Facilita la comunicación entre backend y frontend</li>
 * </ul>
 *
 * <h3>¿Cómo acceder a la documentación?</h3>
 * <p>Una vez que la aplicación esté ejecutándose, la documentación estará disponible en:</p>
 * <ul>
 *   <li><b>Swagger UI:</b> <a href="http://localhost:8080/swagger-ui.html">http://localhost:8080/swagger-ui.html</a></li>
 *   <li><b>JSON OpenAPI:</b> <a href="http://localhost:8080/v3/api-docs">http://localhost:8080/v3/api-docs</a></li>
 * </ul>
 *
 * <h3>¿Qué es @Configuration?</h3>
 * <p>@Configuration marca esta clase como fuente de definiciones de beans de Spring.
 * Las clases @Configuration:</p>
 * <ul>
 *   <li>Son detectadas automáticamente por el escaneo de componentes</li>
 *   <li>Pueden contener métodos @Bean que definen objetos gestionados por Spring</li>
 *   <li>Son similares a archivos XML de configuración (versión moderna con código Java)</li>
 * </ul>
 *
 * <h3>¿Qué es @Bean?</h3>
 * <p>@Bean indica que un método produce un objeto que debe ser gestionado por el
 * contenedor de Spring. Spring:</p>
 * <ul>
 *   <li>Invoca el método automáticamente al iniciar la aplicación</li>
 *   <li>Almacena el objeto retornado en su contenedor (Application Context)</li>
 *   <li>Puede inyectar ese bean en otras clases que lo necesiten</li>
 * </ul>
 *
 * <h3>Dependencias necesarias (build.gradle):</h3>
 * <pre>{@code
 * dependencies {
 *     implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0'
 * }
 * }</pre>
 *
 * <h3>Configuración automática de SpringDoc:</h3>
 * <p>SpringDoc escanea automáticamente todos los @RestController y:</p>
 * <ul>
 *   <li>Detecta todos los endpoints (@GetMapping, @PostMapping, etc.)</li>
 *   <li>Extrae los modelos de datos (DTOs) y sus validaciones</li>
 *   <li>Identifica los códigos de respuesta HTTP</li>
 *   <li>Genera la especificación OpenAPI completa</li>
 * </ul>
 *
 * <h3>Ejemplo de visualización en Swagger UI:</h3>
 * <pre>
 * API de Gestión de Usuarios v1.0.0
 *
 * UsuarioController
 *   POST   /api/usuarios          Crear nuevo usuario
 *   GET    /api/usuarios          Listar todos los usuarios
 *   GET    /api/usuarios/{id}     Buscar usuario por ID
 *   PUT    /api/usuarios/{id}     Actualizar usuario
 *   DELETE /api/usuarios/{id}     Eliminar usuario
 *
 * Schemas:
 *   UsuarioRequestDTO   - Campos: nombre, email, password (con validaciones)
 *   UsuarioResponseDTO  - Campos: id, nombre, email
 *   ErrorDTO            - Campos: timestamp, status, error, detalles
 * </pre>
 *
 * <h3>Ventajas de documentar con OpenAPI:</h3>
 * <table border="1">
 *   <tr>
 *     <th>Ventaja</th>
 *     <th>Beneficio</th>
 *   </tr>
 *   <tr>
 *     <td>Actualización automática</td>
 *     <td>La documentación se genera del código, siempre está sincronizada</td>
 *   </tr>
 *   <tr>
 *     <td>Interfaz interactiva</td>
 *     <td>Los desarrolladores pueden probar endpoints sin Postman</td>
 *   </tr>
 *   <tr>
 *     <td>Generación de clientes</td>
 *     <td>Se pueden generar SDKs automáticamente en varios lenguajes</td>
 *   </tr>
 *   <tr>
 *     <td>Estandarización</td>
 *     <td>OpenAPI es un estándar reconocido en la industria</td>
 *   </tr>
 * </table>
 *
 * <h3>Personalización adicional (opcional):</h3>
 * <p>Se pueden agregar más configuraciones:</p>
 * <pre>{@code
 * @Bean
 * public OpenAPI customOpenAPI() {
 *     return new OpenAPI()
 *         .info(new Info()
 *             .title("API de Gestión de Usuarios")
 *             .description("Descripción detallada...")
 *             .version("1.0.0"))
 *         .servers(List.of(
 *             new Server().url("http://localhost:8080").description("Desarrollo"),
 *             new Server().url("https://api.produccion.com").description("Producción")
 *         ))
 *         .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
 *         .components(new Components()
 *             .addSecuritySchemes("bearerAuth", new SecurityScheme()
 *                 .type(SecurityScheme.Type.HTTP)
 *                 .scheme("bearer")
 *                 .bearerFormat("JWT")));
 * }
 * }</pre>
 *
 * @author Juan Cruz Robledo
 * @version 1.0
 * @since 2025
 * @see <a href="https://swagger.io/specification/">OpenAPI Specification</a>
 * @see <a href="https://springdoc.org/">SpringDoc Documentation</a>
 */
@Configuration
public class OpenApiConfig {

    /**
     * Define el bean de configuración de OpenAPI para la documentación de la API.
     *
     * <p>Este método configura los metadatos principales de la API que aparecerán
     * en la interfaz de Swagger UI.</p>
     *
     * <h3>Ciclo de vida del bean:</h3>
     * <ol>
     *   <li>Spring Boot inicia la aplicación</li>
     *   <li>Detecta la clase @Configuration</li>
     *   <li>Invoca este método @Bean</li>
     *   <li>Almacena el objeto OpenAPI retornado</li>
     *   <li>SpringDoc usa este bean para generar la documentación</li>
     * </ol>
     *
     * <h3>Componentes configurados:</h3>
     *
     * <h4>1. Info - Información general de la API:</h4>
     * <ul>
     *   <li><b>title:</b> Nombre de la API mostrado en la parte superior de Swagger UI</li>
     *   <li><b>description:</b> Descripción breve de la funcionalidad de la API</li>
     *   <li><b>version:</b> Versión actual de la API (útil para versionamiento)</li>
     * </ul>
     *
     * <h4>2. Contact - Información de contacto:</h4>
     * <ul>
     *   <li><b>name:</b> Nombre del desarrollador o equipo responsable</li>
     *   <li><b>email:</b> Email de contacto para soporte o consultas</li>
     *   <li><b>url:</b> Sitio web del desarrollador o documentación adicional</li>
     * </ul>
     *
     * <h4>3. License - Información de licencia:</h4>
     * <ul>
     *   <li><b>name:</b> Nombre de la licencia (ej: Apache 2.0, MIT, GPL)</li>
     *   <li><b>url:</b> URL con el texto completo de la licencia</li>
     * </ul>
     *
     * <h3>Visualización en Swagger UI:</h3>
     * <p>Esta configuración genera el siguiente encabezado en Swagger:</p>
     * <pre>
     * +========================================================+
     * |  API de Gestión de Usuarios                    v1.0.0 |
     * |  Esta API permite administrar usuarios con             |
     * |  operaciones CRUD completas.                           |
     * |                                                        |
     * |  Contact: Juan Cruz Robledo                            |
     * |  Email: juan.robledo@tuinstituto.edu.ar               |
     * |  License: Apache 2.0                                   |
     * +========================================================+
     * </pre>
     *
     * <h3>Patrón Fluent API / Builder:</h3>
     * <p>Los métodos de OpenAPI usan el patrón Fluent (cada método retorna 'this')
     * permitiendo encadenar llamadas de forma legible:</p>
     * <pre>{@code
     * new OpenAPI()
     *     .info(...)      // Retorna el mismo objeto OpenAPI
     *     .servers(...)   // Retorna el mismo objeto OpenAPI
     *     .components(...) // etc.
     * }</pre>
     *
     * <h3>¿Dónde se usa este bean?</h3>
     * <ul>
     *   <li>SpringDoc lo inyecta automáticamente</li>
     *   <li>Se usa para generar la especificación OpenAPI JSON</li>
     *   <li>Swagger UI lo consume para renderizar la interfaz web</li>
     * </ul>
     *
     * <h3>Ejemplo de petición a /v3/api-docs:</h3>
     * <pre>{@code
     * GET http://localhost:8080/v3/api-docs
     *
     * Response (JSON):
     * {
     *   "openapi": "3.0.1",
     *   "info": {
     *     "title": "API de Gestión de Usuarios",
     *     "description": "Esta API permite administrar usuarios...",
     *     "version": "1.0.0",
     *     "contact": {
     *       "name": "Juan Cruz Robledo",
     *       "email": "juan.robledo@tuinstituto.edu.ar"
     *     },
     *     "license": {
     *       "name": "Apache 2.0",
     *       "url": "http://springdoc.org"
     *     }
     *   },
     *   "paths": {
     *     "/api/usuarios": { ... },
     *     "/api/usuarios/{id}": { ... }
     *   },
     *   "components": { ... }
     * }
     * }</pre>
     *
     * <h3>Buenas prácticas:</h3>
     * <ul>
     *   <li>Mantener la versión sincronizada con pom.xml o build.gradle</li>
     *   <li>Actualizar la descripción cuando cambien funcionalidades importantes</li>
     *   <li>Proporcionar información de contacto real para soporte</li>
     *   <li>Elegir una licencia apropiada para el proyecto</li>
     * </ul>
     *
     * @return Objeto OpenAPI configurado con metadatos de la API
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Usuarios")
                        .description("Esta API permite administrar usuarios con operaciones CRUD completas.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Juan Cruz Robledo")
                                .email("juan.robledo@tuinstituto.edu.ar")
                                .url("https://juancruz.edu.ar"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
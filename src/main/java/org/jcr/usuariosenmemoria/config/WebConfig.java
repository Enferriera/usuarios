package org.jcr.usuariosenmemoria.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de CORS (Cross-Origin Resource Sharing) para la API.
 *
 * <p>Esta configuración permite que la API sea consumida desde aplicaciones
 * frontend alojadas en diferentes orígenes (dominios, puertos o protocolos).</p>
 *
 * <h3>¿Qué es CORS?</h3>
 * <p>CORS es un mecanismo de seguridad implementado por los navegadores web que
 * restringe las peticiones HTTP entre diferentes orígenes. Sin configuración CORS,
 * un frontend ejecutándose en http://localhost:3000 no podría consumir una API
 * en http://localhost:8080 debido a la política del mismo origen (Same-Origin Policy).</p>
 *
 * <h3>Política del Mismo Origen (Same-Origin Policy):</h3>
 * <p>Dos URLs tienen el mismo origen solo si coinciden en:</p>
 * <ul>
 *   <li><b>Protocolo:</b> http vs https</li>
 *   <li><b>Dominio:</b> localhost vs example.com</li>
 *   <li><b>Puerto:</b> 3000 vs 8080</li>
 * </ul>
 *
 * <p>Ejemplos de orígenes diferentes:</p>
 * <table border="1">
 *   <tr>
 *     <th>URL Frontend</th>
 *     <th>URL API</th>
 *     <th>¿Mismo origen?</th>
 *   </tr>
 *   <tr>
 *     <td>http://localhost:3000</td>
 *     <td>http://localhost:8080</td>
 *     <td>NO (puerto diferente)</td>
 *   </tr>
 *   <tr>
 *     <td>http://localhost:8080</td>
 *     <td>https://localhost:8080</td>
 *     <td>NO (protocolo diferente)</td>
 *   </tr>
 *   <tr>
 *     <td>http://localhost:8080</td>
 *     <td>http://localhost:8080</td>
 *     <td>SI</td>
 *   </tr>
 * </table>
 *
 * <h3>¿Cómo funciona CORS?</h3>
 * <ol>
 *   <li>El navegador detecta que la petición es cross-origin</li>
 *   <li>Envía una petición OPTIONS (preflight) para verificar permisos</li>
 *   <li>El servidor responde con headers CORS indicando qué está permitido</li>
 *   <li>Si el servidor permite el origen, el navegador ejecuta la petición real</li>
 * </ol>
 *
 * <h3>Configuración actual:</h3>
 * <ul>
 *   <li><b>Rutas permitidas:</b> /api/** (todos los endpoints de la API)</li>
 *   <li><b>Orígenes permitidos:</b>
 *     <ul>
 *       <li>http://localhost:3000 - Servidor de desarrollo React/Vue/Angular</li>
 *       <li>http://localhost:4200 - Servidor de desarrollo Angular CLI</li>
 *       <li>https://miapp.com - Dominio de producción (cambiar según necesidad)</li>
 *     </ul>
 *   </li>
 *   <li><b>Métodos HTTP:</b> GET, POST, PUT, DELETE, OPTIONS</li>
 *   <li><b>Headers permitidos:</b> Todos (*) - Content-Type, Authorization, etc.</li>
 *   <li><b>Credenciales:</b> Habilitadas (permite envío de cookies y headers de autenticación)</li>
 *   <li><b>Max Age:</b> 3600 segundos (caché de preflight requests por 1 hora)</li>
 * </ul>
 *
 * <h3>¿Qué es @Configuration?</h3>
 * <p>@Configuration marca esta clase como una clase de configuración de Spring.
 * Spring detectará esta clase automáticamente y procesará sus beans y métodos
 * de configuración durante el inicio de la aplicación.</p>
 *
 * <h3>¿Qué es WebMvcConfigurer?</h3>
 * <p>WebMvcConfigurer es una interfaz que permite personalizar la configuración
 * de Spring MVC sin deshabilitar la configuración automática. Proporciona métodos
 * de callback como:</p>
 * <ul>
 *   <li>addCorsMappings() - Configurar CORS</li>
 *   <li>addInterceptors() - Agregar interceptores</li>
 *   <li>addResourceHandlers() - Configurar recursos estáticos</li>
 *   <li>configureMessageConverters() - Configurar conversores JSON/XML</li>
 * </ul>
 *
 * <h3>Ejemplo de uso desde JavaScript (Frontend):</h3>
 * <pre>{@code
 * // React/Vue/Angular en localhost:3000 puede consumir la API:
 * fetch('http://localhost:8080/api/usuarios', {
 *     method: 'POST',
 *     credentials: 'include',  // Incluye cookies si es necesario
 *     headers: {
 *         'Content-Type': 'application/json',
 *         'Authorization': 'Bearer token123'
 *     },
 *     body: JSON.stringify({
 *         nombre: 'Juan',
 *         email: 'juan@example.com',
 *         password: 'pass123'
 *     })
 * })
 * .then(response => response.json())
 * .then(data => console.log('Usuario creado:', data))
 * .catch(error => console.error('Error:', error));
 * }</pre>
 *
 * <h3>Ejemplo con Axios:</h3>
 * <pre>{@code
 * // Configuración global de Axios
 * import axios from 'axios';
 *
 * const api = axios.create({
 *     baseURL: 'http://localhost:8080/api',
 *     withCredentials: true,  // Habilita envío de cookies
 *     headers: {
 *         'Content-Type': 'application/json'
 *     }
 * });
 *
 * // Crear usuario
 * api.post('/usuarios', {
 *     nombre: 'Juan',
 *     email: 'juan@example.com',
 *     password: 'pass123'
 * })
 * .then(response => console.log(response.data))
 * .catch(error => console.error(error));
 * }</pre>
 *
 * <h3>Seguridad - Consideraciones importantes:</h3>
 * <ul>
 *   <li><b>⚠️ NUNCA usar "*" en producción:</b> Especificar siempre orígenes concretos</li>
 *   <li><b>allowCredentials(true):</b> Solo usar si realmente se necesitan cookies/auth</li>
 *   <li><b>Listar orígenes explícitamente:</b> No permitir orígenes arbitrarios</li>
 *   <li><b>Limitar métodos:</b> Solo permitir los métodos HTTP que la API realmente usa</li>
 * </ul>
 *
 * <h3>Configuración para diferentes entornos:</h3>
 * <p>Para configurar diferentes orígenes según el entorno (dev/prod), usar properties:</p>
 * <pre>{@code
 * // application-dev.properties
 * cors.allowed-origins=http://localhost:3000,http://localhost:4200
 *
 * // application-prod.properties
 * cors.allowed-origins=https://miapp.com,https://www.miapp.com
 *
 * // WebConfig.java
 * @Value("${cors.allowed-origins}")
 * private String[] allowedOrigins;
 *
 * @Override
 * public void addCorsMappings(CorsRegistry registry) {
 *     registry.addMapping("/api/**")
 *             .allowedOrigins(allowedOrigins)
 *             ...
 * }
 * }</pre>
 *
 * <h3>¿Cuándo no se necesita CORS?</h3>
 * <ul>
 *   <li>Si el frontend y backend están en el mismo origen (mismo dominio y puerto)</li>
 *   <li>Si se usa un proxy inverso (nginx, Apache) que sirve ambos bajo el mismo origen</li>
 *   <li>Si se accede a la API solo desde servidor (Node.js, Java, Python), no desde navegador</li>
 * </ul>
 *
 * <h3>Debugging CORS:</h3>
 * <p>Para verificar que CORS está funcionando, inspeccionar los headers HTTP:</p>
 * <pre>
 * Request Headers (enviados por el navegador):
 *   Origin: http://localhost:3000
 *   Access-Control-Request-Method: POST
 *   Access-Control-Request-Headers: content-type
 *
 * Response Headers (enviados por el servidor):
 *   Access-Control-Allow-Origin: http://localhost:3000
 *   Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
 *   Access-Control-Allow-Headers: *
 *   Access-Control-Allow-Credentials: true
 *   Access-Control-Max-Age: 3600
 * </pre>
 *
 * <h3>Errores comunes y soluciones:</h3>
 * <table border="1">
 *   <tr>
 *     <th>Error</th>
 *     <th>Causa</th>
 *     <th>Solución</th>
 *   </tr>
 *   <tr>
 *     <td>No 'Access-Control-Allow-Origin' header</td>
 *     <td>CORS no configurado</td>
 *     <td>Agregar esta configuración</td>
 *   </tr>
 *   <tr>
 *     <td>Origin not allowed</td>
 *     <td>Origen no está en allowedOrigins</td>
 *     <td>Agregar origen a la lista</td>
 *   </tr>
 *   <tr>
 *     <td>Credentials flag is true, but Access-Control-Allow-Credentials is not</td>
 *     <td>allowCredentials no configurado</td>
 *     <td>Agregar .allowCredentials(true)</td>
 *   </tr>
 *   <tr>
 *     <td>Method not allowed</td>
 *     <td>Método HTTP no está en allowedMethods</td>
 *     <td>Agregar método a la lista</td>
 *   </tr>
 * </table>
 *
 * @author Juan Cruz Robledo
 * @version 1.0
 * @since 2025
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS">MDN - CORS</a>
 * @see <a href="https://spring.io/guides/gs/rest-service-cors/">Spring CORS Guide</a>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura las reglas CORS para los endpoints de la API.
     *
     * <p>Este método es invocado automáticamente por Spring durante el inicio
     * de la aplicación para configurar el manejo de peticiones cross-origin.</p>
     *
     * <h3>Configuración aplicada:</h3>
     * <ul>
     *   <li><b>addMapping("/api/**"):</b> Aplica CORS a todos los endpoints bajo /api/</li>
     *   <li><b>allowedOrigins:</b> Lista blanca de orígenes permitidos</li>
     *   <li><b>allowedMethods:</b> Métodos HTTP permitidos</li>
     *   <li><b>allowedHeaders("*"):</b> Permite cualquier header en las peticiones</li>
     *   <li><b>allowCredentials(true):</b> Permite envío de cookies y headers de autenticación</li>
     *   <li><b>maxAge(3600):</b> Caché de preflight requests por 1 hora</li>
     * </ul>
     *
     * <h3>¿Qué es una petición Preflight?</h3>
     * <p>Antes de ejecutar peticiones POST, PUT o DELETE, el navegador envía una
     * petición OPTIONS automática para verificar permisos. El parámetro maxAge
     * indica cuánto tiempo el navegador puede cachear esta respuesta, reduciendo
     * el número de peticiones preflight.</p>
     *
     * <h3>Orígenes permitidos en esta configuración:</h3>
     * <ul>
     *   <li><b>http://localhost:3000</b> - Create React App, Vue CLI, Next.js dev server</li>
     *   <li><b>http://localhost:4200</b> - Angular CLI dev server</li>
     *   <li><b>https://miapp.com</b> - Dominio de producción (CAMBIAR según tu dominio real)</li>
     * </ul>
     *
     * <h3>⚠️ IMPORTANTE para Producción:</h3>
     * <p>Antes de desplegar a producción, actualizar el origen https://miapp.com
     * con tu dominio real. Ejemplos:</p>
     * <pre>{@code
     * // Si tu app está en:
     * .allowedOrigins(
     *     "https://www.tudominio.com",
     *     "https://tudominio.com"  // Con y sin www
     * )
     *
     * // Si usas subdominio:
     * .allowedOrigins(
     *     "https://app.tudominio.com",
     *     "https://admin.tudominio.com"
     * )
     * }</pre>
     *
     * <h3>Alternativa con allowedOriginPatterns:</h3>
     * <p>Para permitir múltiples subdominios dinámicamente:</p>
     * <pre>{@code
     * registry.addMapping("/api/**")
     *     .allowedOriginPatterns("https://*.midominio.com")  // Cualquier subdominio
     *     .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
     *     .allowCredentials(true);
     * }</pre>
     *
     * <h3>Ejemplo de flujo de petición CORS exitosa:</h3>
     * <pre>
     * 1. Frontend (localhost:3000) hace fetch a API (localhost:8080/api/usuarios)
     * 2. Navegador detecta cross-origin
     * 3. Navegador envía OPTIONS a /api/usuarios (preflight)
     * 4. Spring responde con headers CORS indicando que localhost:3000 está permitido
     * 5. Navegador ejecuta la petición real (GET, POST, etc.)
     * 6. Spring responde con datos + headers CORS
     * 7. Frontend recibe la respuesta exitosamente
     * </pre>
     *
     * @param registry Registro de configuraciones CORS proporcionado por Spring
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(
                        "http://localhost:3000",    // React, Vue, Next.js dev
                        "http://localhost:4200",    // Angular dev
                        "https://miapp.com"         // Producción - CAMBIAR por tu dominio
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
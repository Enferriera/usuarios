package org.jcr.usuariosenmemoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot para gestión de usuarios.
 *
 * <p>Esta es la clase de arranque de la aplicación que contiene el método main.
 * La anotación @SpringBootApplication es una anotación de conveniencia que combina:</p>
 *
 * <ul>
 *   <li>@Configuration: Indica que la clase puede ser usada como fuente de definiciones de beans</li>
 *   <li>@EnableAutoConfiguration: Habilita la configuración automática de Spring Boot</li>
 *   <li>@ComponentScan: Habilita el escaneo de componentes en el paquete actual y subpaquetes</li>
 * </ul>
 *
 * <p>Spring Boot configurará automáticamente:</p>
 * <ul>
 *   <li>Servidor web embebido (Tomcat)</li>
 *   <li>Configuración de Spring MVC para endpoints REST</li>
 *   <li>Configuración de JPA/Hibernate</li>
 *   <li>Base de datos H2 en memoria</li>
 *   <li>Escaneo de componentes (@Controller, @Service, @Repository)</li>
 * </ul>
 *
 * @author Juan Cruz Robledo
 * @version 1.0
 * @since 2025
 */
@SpringBootApplication
public class UsuariosEnMemoriaApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     *
     * <p>Este método utiliza SpringApplication.run() que:</p>
     * <ol>
     *   <li>Crea el ApplicationContext de Spring</li>
     *   <li>Registra todos los beans necesarios</li>
     *   <li>Inicia el servidor web embebido (Tomcat en puerto 8080)</li>
     *   <li>Inicializa la base de datos H2</li>
     *   <li>Despliega los endpoints REST</li>
     * </ol>
     *
     * @param args argumentos de línea de comandos (pueden usarse para pasar propiedades)
     *             Ejemplo: --spring.profiles.active=dev
     */
    public static void main(String[] args) {
        SpringApplication.run(UsuariosEnMemoriaApplication.class, args);

        System.out.println("funcionando el back");
    }

}

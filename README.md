# API REST de Gestión de Usuarios con Spring Boot

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen?style=for-the-badge&logo=spring-boot)
![Gradle](https://img.shields.io/badge/Gradle-8.14-02303A?style=for-the-badge&logo=gradle)
![H2](https://img.shields.io/badge/H2-Database-blue?style=for-the-badge)
![License](https://img.shields.io/badge/License-Educational-yellow?style=for-the-badge)

**API REST educativa con arquitectura en tres capas, validaciones robustas, documentación OpenAPI automática y manejo global de excepciones**

[Características](#características) •
[Instalación](#instalación-rápida) •
[Uso](#guía-de-uso) •
[API](#documentación-de-la-api) •
[Arquitectura](#arquitectura-técnica)

</div>

---

## Tabla de Contenidos

- [Descripción del Proyecto](#descripción-del-proyecto)
- [Características Principales](#características-principales)
- [Stack Tecnológico](#stack-tecnológico)
- [Requisitos Previos](#requisitos-previos)
- [Instalación Rápida](#instalación-rápida)
- [Guía de Uso](#guía-de-uso)
  - [Ejecutar la Aplicación](#ejecutar-la-aplicación)
  - [Compilar y Generar JAR](#compilar-y-generar-jar)
  - [Ejecutar Tests](#ejecutar-tests)
  - [Generar Documentación JavaDoc](#generar-documentación-javadoc)
- [Documentación de la API](#documentación-de-la-api)
  - [Endpoints Disponibles](#endpoints-disponibles)
  - [Ejemplos de Peticiones](#ejemplos-de-peticiones)
- [Arquitectura Técnica](#arquitectura-técnica)
  - [Patrón de Tres Capas](#patrón-de-tres-capas)
  - [Flujo de Datos](#flujo-de-datos)
  - [Modelo de Dominio](#modelo-de-dominio)
- [Configuración CORS](#configuración-cors)
- [Manejo de Errores](#manejo-de-errores)
- [Base de Datos](#base-de-datos)
- [Seguridad](#consideraciones-de-seguridad)
- [Documentación Adicional](#documentación-adicional)
- [Autor](#autor)

---

## Descripción del Proyecto

Este proyecto es una **API REST educativa** desarrollada con **Spring Boot 3.5.6** que implementa un sistema completo de gestión de usuarios (CRUD - Create, Read, Update, Delete). Está diseñado específicamente para **estudiantes de informática** que desean aprender las mejores prácticas de desarrollo backend con Spring Framework mediante un ejemplo práctico y completamente documentado.

### 🎯 ¿Qué hace este proyecto?

Esta API proporciona un **backend completo y funcional** que simula un sistema real de gestión de usuarios, permitiendo:

- ✅ **Crear nuevos usuarios** con validación automática de datos (nombre, email, password)
- ✅ **Listar todos los usuarios** registrados en el sistema
- ✅ **Consultar un usuario específico** por su identificador único (ID)
- ✅ **Actualizar información** de usuarios existentes de forma completa
- ✅ **Eliminar usuarios** del sistema de manera segura
- ✅ **Documentación interactiva** automática con Swagger UI para probar endpoints
- ✅ **Manejo centralizado de errores** con respuestas estandarizadas en formato JSON
- ✅ **Soporte CORS** configurado para integración con aplicaciones frontend (React, Angular, Vue)

### 📚 ¿Por qué es educativo?

Este proyecto va más allá de un simple CRUD. Está diseñado como una **herramienta de aprendizaje completa** que cubre:

#### 1. Arquitectura Profesional
- **Patrón de tres capas** (Controller → Service → Repository) que separa responsabilidades claramente
- **Inyección de dependencias** con Spring para bajo acoplamiento
- **Separación de DTOs y Entities** para mantener contratos de API independientes de la lógica interna

#### 2. Prácticas Modernas de Java
- **Java 17** con características modernas como Records para DTOs inmutables
- **Lombok** para reducir código boilerplate (@Data, @Builder, @RequiredArgsConstructor)
- **Streams API** y programación funcional (map, filter, Optional, Method References)
- **Pattern Builder** para construcción elegante de objetos

#### 3. Spring Boot en Acción
- **Spring Data JPA** con Hibernate para persistencia automática
- **Bean Validation** (@NotBlank, @Email, @Size) para validación declarativa
- **@Transactional** para gestión automática de transacciones
- **@ControllerAdvice** para manejo global de excepciones
- **Profiles** de configuración (dev/prod) para diferentes entornos

#### 4. Seguridad y Robustez
- **Prevención de race conditions** (TOCTOU - Time-Of-Check-Time-Of-Use)
- **Validación en múltiples capas** (DTOs + Service + Database constraints)
- **Códigos HTTP semánticos** correctos (200, 201, 204, 400, 409, 500)
- **Exclusión de datos sensibles** en respuestas (password nunca se retorna)

#### 5. Documentación Profesional
- **+2000 líneas de JavaDoc en español** explicando cada concepto
- **OpenAPI/Swagger** generado automáticamente
- **README completo** con ejemplos de uso en múltiples lenguajes
- **CLAUDE.md** con arquitectura técnica detallada

### 🎓 Propósito Educativo

El código incluye **documentación JavaDoc exhaustiva en español** que explica paso a paso:

| Tema | Descripción | Ubicación |
|------|-------------|-----------|
| **Arquitectura en Capas** | Separación de responsabilidades entre Controller, Service, Repository | Todas las clases |
| **Inyección de Dependencias** | Cómo Spring gestiona dependencias con @Autowired y constructores | UsuarioController, UsuarioService |
| **Patrones de Diseño** | Builder, Factory Method, DTO, Repository | Usuario (Builder), UsuarioResponseDTO (Factory) |
| **Anotaciones Spring** | @Service, @Transactional, @Valid, @ControllerAdvice, @RestController | Cada clase del proyecto |
| **Gestión de Transacciones** | Cuándo y por qué usar @Transactional, atomicidad de operaciones | UsuarioService.java |
| **Prevención de Race Conditions** | Patrón TOCTOU y validación atómica en operaciones concurrentes | UsuarioService.eliminarUsuario() |
| **API Funcional de Java** | Optional, Streams, map(), Method References (::) | UsuarioService.listarUsuarios() |
| **Bean Validation** | Validación declarativa con annotations (@NotBlank, @Email) | UsuarioRequestDTO.java |
| **Manejo de Excepciones** | Captura global con @ControllerAdvice, respuestas estandarizadas | GlobalExceptionHandler.java |
| **HTTP Status Codes** | Semántica correcta de códigos HTTP (200 vs 201 vs 204) | UsuarioController.java |
| **Seguridad de Datos** | Por qué excluir passwords de respuestas, DTOs vs Entities | UsuarioResponseDTO.java |

### 🔍 Ejemplo de Flujo Completo

Para ilustrar mejor cómo funciona el proyecto, veamos qué sucede cuando un cliente crea un usuario:

```
1. Cliente envía petición HTTP:
   POST http://localhost:8080/api/usuarios
   {
     "nombre": "María García",
     "email": "maria@example.com",
     "password": "secreta123"
   }

2. UsuarioController recibe la petición:
   - Jackson convierte JSON a UsuarioRequestDTO
   - @Valid activa validaciones Bean Validation
   - Si nombre vacío o email inválido → ERROR 400
   - Si validación OK → Llama a UsuarioService

3. UsuarioService procesa la lógica de negocio:
   - @Transactional inicia transacción de BD
   - Convierte DTO a Entity usando Builder:
     Usuario.builder()
       .nombre("María García")
       .email("maria@example.com")
       .password("secreta123")
       .build()
   - Llama a UsuarioRepository

4. UsuarioRepository persiste en base de datos:
   - JpaRepository.save() ejecuta INSERT SQL
   - Hibernate genera: INSERT INTO usuarios (nombre, email, password) VALUES (...)
   - Si email ya existe → ERROR 409 CONFLICT
   - Si OK → Retorna Usuario con ID auto-generado

5. UsuarioService completa el proceso:
   - Convierte Entity a DTO con método factory:
     UsuarioResponseDTO.fromEntity(usuario)
   - ¡IMPORTANTE! El password NO se incluye en el DTO de respuesta
   - @Transactional confirma transacción (COMMIT)

6. UsuarioController construye respuesta HTTP:
   - Envuelve DTO en ResponseEntity con status 201 CREATED
   - Spring Boot serializa DTO a JSON

7. Cliente recibe respuesta:
   HTTP/1.1 201 Created
   {
     "id": 1,
     "nombre": "María García",
     "email": "maria@example.com"
     // Nota: password NO incluido por seguridad
   }
```

### 💡 ¿Qué aprenderás usando este proyecto?

Al estudiar y experimentar con este código, los estudiantes aprenderán:

1. **Cómo estructurar** una aplicación Spring Boot profesional
2. **Por qué separar** la aplicación en capas (Controller, Service, Repository)
3. **Cuándo usar** DTOs vs Entities
4. **Cómo validar** datos de entrada de forma declarativa
5. **Por qué usar** transacciones en operaciones de escritura
6. **Cómo manejar** errores de forma centralizada y elegante
7. **Qué códigos HTTP** usar en cada situación
8. **Cómo documentar** APIs con OpenAPI/Swagger
9. **Por qué es importante** la prevención de race conditions
10. **Cómo integrar** un backend con frontends mediante CORS

### 🚀 Ideal para...

- ✅ Estudiantes de ingeniería en sistemas/informática
- ✅ Desarrolladores aprendiendo Spring Boot por primera vez
- ✅ Bootcamps y cursos de desarrollo backend
- ✅ Proyectos académicos de ejemplo
- ✅ Referencia para arquitectura de microservicios
- ✅ Base para proyectos más complejos (añadiendo autenticación, roles, etc.)

---

## Características Principales

### 🎯 Funcionalidades Core

| Característica | Descripción |
|----------------|-------------|
| **CRUD Completo** | Operaciones Create, Read, Update, Delete totalmente funcionales |
| **Validación Automática** | Jakarta Bean Validation con anotaciones (@NotBlank, @Email, @Size) |
| **Códigos HTTP Semánticos** | Uso correcto de 200, 201, 204, 400, 409, 500 |
| **Manejo Global de Excepciones** | Respuestas de error estandarizadas mediante @ControllerAdvice |
| **Transacciones Explícitas** | @Transactional en todas las operaciones de escritura |
| **Prevención de Race Conditions** | Patrón TOCTOU prevention con validación atómica |
| **Separación DTO/Entity** | DTOs para API contracts, Entities para persistencia |
| **Seguridad de Datos** | Contraseñas excluidas de respuestas JSON |

### 📚 Características Técnicas

- **Base de Datos H2** en memoria con schema auto-generado
- **OpenAPI 3.0** con Swagger UI integrado
- **CORS configurado** para integración con frontends (React, Angular, Vue)
- **Perfiles de Spring** (producción/desarrollo)
- **JAR ejecutable** standalone de 60MB
- **Lombok** para reducción de boilerplate
- **Java Records** para DTOs inmutables
- **Streams API** para programación funcional

### 📖 Documentación

- **JavaDoc exhaustivo** en español (260+ líneas por clase principal)
- **Swagger UI interactivo** para probar endpoints
- **README completo** con ejemplos de uso
- **CLAUDE.md** con arquitectura técnica detallada

---

## Stack Tecnológico

### Tecnologías Core

| Tecnología | Versión | Propósito | Documentación |
|------------|---------|-----------|---------------|
| **Java** | 17 LTS | Lenguaje de programación | [Oracle Docs](https://docs.oracle.com/en/java/javase/17/) |
| **Spring Boot** | 3.5.6 | Framework principal | [Spring Docs](https://spring.io/projects/spring-boot) |
| **Spring Data JPA** | 3.3.x | Capa de persistencia | [JPA Docs](https://spring.io/projects/spring-data-jpa) |
| **Hibernate** | 6.6.x | ORM (Object-Relational Mapping) | [Hibernate Docs](https://hibernate.org/) |
| **H2 Database** | 2.3.x | Base de datos en memoria | [H2 Docs](https://www.h2database.com/) |
| **Gradle** | 8.14 | Sistema de construcción | [Gradle Docs](https://docs.gradle.org/) |

### Librerías Adicionales

| Librería | Versión | Propósito |
|----------|---------|-----------|
| **Spring Validation** | - | Validaciones con Jakarta Bean Validation |
| **Lombok** | - | Reducción de código boilerplate |
| **SpringDoc OpenAPI** | 2.8.8 | Generación automática de documentación API |
| **JUnit 5** | - | Framework de testing |

### Anotaciones Principales Utilizadas

**Spring Framework:**
- `@SpringBootApplication` - Configuración principal
- `@RestController` - Controlador REST
- `@Service` - Capa de servicio
- `@Repository` - Capa de acceso a datos
- `@Configuration` - Clases de configuración
- `@Transactional` - Gestión de transacciones
- `@ControllerAdvice` - Manejo global de excepciones

**Jakarta Bean Validation:**
- `@Valid` - Activar validación
- `@NotBlank` - Campo no vacío
- `@Email` - Formato de email válido
- `@Size` - Longitud mínima/máxima

**Lombok:**
- `@Data` - Getters/setters/toString/equals/hashCode
- `@Builder` - Patrón Builder
- `@RequiredArgsConstructor` - Constructor de dependencias
- `@AllArgsConstructor` / `@NoArgsConstructor` - Constructores

**JPA:**
- `@Entity` - Entidad de persistencia
- `@Table` - Mapeo de tabla
- `@Id` - Clave primaria
- `@GeneratedValue` - Generación automática de ID
- `@Column` - Constraints de columna

---

## Requisitos Previos

### Software Necesario

1. **Java Development Kit (JDK) 17 o superior**
   ```bash
   # Verificar instalación
   java -version
   # Debe mostrar: java version "17.x.x"
   ```

2. **Gradle 8.x** (incluido como wrapper en el proyecto)
   ```bash
   # No requiere instalación manual, usar ./gradlew
   ./gradlew --version
   ```

3. **Sistema Operativo Compatible**
   - ✅ Windows 10/11
   - ✅ Linux (Ubuntu 20.04+, Fedora, etc.)
   - ✅ macOS 11+

### Herramientas Recomendadas (Opcionales)

- **IDE:** IntelliJ IDEA, Eclipse, VS Code
- **Cliente API:** Postman, Insomnia, cURL
- **Navegador Web:** Chrome, Firefox, Edge (para Swagger UI)

---

## Instalación Rápida

### Paso 1: Clonar o Descargar el Proyecto

```bash
# Si tienes Git instalado
git clone <url-del-repositorio>
cd usuarios-en-memoria-api-documentada-main

# O simplemente descomprime el ZIP y navega al directorio
cd usuarios-en-memoria-api-documentada-main
```

### Paso 2: Verificar Requisitos

```bash
# Verificar Java
java -version

# Verificar Gradle Wrapper
./gradlew --version  # Linux/Mac
gradlew.bat --version  # Windows
```

### Paso 3: Compilar el Proyecto

```bash
# Linux/Mac
./gradlew build

# Windows
gradlew.bat build
```

### Paso 4: Ejecutar la Aplicación

```bash
# Linux/Mac
./gradlew bootRun

# Windows
gradlew.bat bootRun
```

**¡Listo!** La aplicación estará corriendo en: **http://localhost:8080**

### Verificar que Funciona

```bash
# Probar el endpoint de listar usuarios
curl http://localhost:8080/api/usuarios

# Debería retornar: []
```

---

## Guía de Uso

### Ejecutar la Aplicación

#### Modo Producción (sin logs SQL)

```bash
# Linux/Mac
./gradlew bootRun

# Windows
gradlew.bat bootRun
```

#### Modo Desarrollo (con logs SQL y consola H2)

```bash
# Linux/Mac
./gradlew bootRun --args='--spring.profiles.active=dev'

# Windows
gradlew.bat bootRun --args="--spring.profiles.active=dev"
```

#### Usando Variables de Entorno

```bash
# Linux/Mac
export JPA_SHOW_SQL=true
export H2_CONSOLE_ENABLED=true
./gradlew bootRun

# Windows (PowerShell)
$env:JPA_SHOW_SQL="true"
$env:H2_CONSOLE_ENABLED="true"
gradlew.bat bootRun

# Windows (CMD)
set JPA_SHOW_SQL=true
set H2_CONSOLE_ENABLED=true
gradlew.bat bootRun
```

### Compilar y Generar JAR

#### Compilar sin Tests

```bash
./gradlew build -x test
```

#### Compilar con Tests

```bash
./gradlew build
```

#### Generar JAR Ejecutable

```bash
./gradlew bootJar
```

El archivo JAR se genera en: **`build/libs/usuarios-api.jar`** (±60MB)

#### Ejecutar el JAR

```bash
# Modo producción
java -jar build/libs/usuarios-api.jar

# Modo desarrollo
java -jar build/libs/usuarios-api.jar --spring.profiles.active=dev

# Con variables de entorno
JPA_SHOW_SQL=true java -jar build/libs/usuarios-api.jar
```

### Ejecutar Tests

```bash
# Ejecutar todos los tests
./gradlew test

# Con salida detallada
./gradlew test --info

# Ver reporte HTML
# Ubicación: build/reports/tests/test/index.html
```

### Generar Documentación JavaDoc

```bash
# Generar JavaDoc HTML
./gradlew javadoc

# Ubicación: build/docs/javadoc/index.html
```

Abrir en navegador:
```bash
# Linux
xdg-open build/docs/javadoc/index.html

# Mac
open build/docs/javadoc/index.html

# Windows
start build/docs/javadoc/index.html
```

### Limpiar Build

```bash
# Eliminar archivos compilados
./gradlew clean

# Limpiar y recompilar
./gradlew clean build
```

---

## Documentación de la API

### URLs Importantes

| Recurso | URL | Descripción |
|---------|-----|-------------|
| **API Base** | http://localhost:8080/api/usuarios | Base path de la API |
| **Swagger UI** | http://localhost:8080/swagger-ui.html | Documentación interactiva |
| **OpenAPI JSON** | http://localhost:8080/v3/api-docs | Especificación OpenAPI |
| **H2 Console** | http://localhost:8080/h2-console | Consola BD (solo modo dev) |

### Endpoints Disponibles

#### Resumen de Endpoints

| Método | Endpoint | Descripción | Código Éxito |
|--------|----------|-------------|--------------|
| **POST** | `/api/usuarios` | Crear nuevo usuario | 201 CREATED |
| **GET** | `/api/usuarios` | Listar todos los usuarios | 200 OK |
| **GET** | `/api/usuarios/{id}` | Obtener usuario por ID | 200 OK |
| **PUT** | `/api/usuarios/{id}` | Actualizar usuario | 200 OK |
| **DELETE** | `/api/usuarios/{id}` | Eliminar usuario | 204 NO CONTENT |

---

### 1️⃣ Crear Usuario

**Endpoint:** `POST /api/usuarios`

**Descripción:** Crea un nuevo usuario en el sistema con validación automática de datos.

**Request Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "nombre": "Juan Pérez",
  "email": "juan.perez@example.com",
  "password": "miPassword123"
}
```

**Validaciones Aplicadas:**

| Campo | Validaciones | Descripción |
|-------|-------------|-------------|
| `nombre` | @NotBlank, @Size(min=2, max=100) | Obligatorio, entre 2-100 caracteres |
| `email` | @NotBlank, @Email | Obligatorio, formato válido |
| `password` | @NotBlank, @Size(min=6) | Obligatorio, mínimo 6 caracteres |

**Respuesta Exitosa (201 CREATED):**
```json
{
  "id": 1,
  "nombre": "Juan Pérez",
  "email": "juan.perez@example.com"
}
```

**Notas:**
- ✅ El ID se genera automáticamente
- ✅ La contraseña NO se retorna en la respuesta (seguridad)
- ✅ El email debe ser único en el sistema

**Errores Posibles:**

| Código | Descripción | Ejemplo |
|--------|-------------|---------|
| **400** | Validación fallida | Email inválido, nombre vacío |
| **409** | Email ya existe | Conflicto de email duplicado |
| **500** | Error del servidor | Error inesperado |

**Ejemplo de Error (400 BAD REQUEST):**
```json
{
  "timestamp": "2025-10-31T14:30:00",
  "status": 400,
  "error": "Error de validación",
  "detalles": [
    "nombre: El nombre no puede estar vacío",
    "email: Formato de email inválido",
    "password: La contraseña debe tener al menos 6 caracteres"
  ]
}
```

**Ejemplo de Error (409 CONFLICT):**
```json
{
  "timestamp": "2025-10-31T14:30:00",
  "status": 409,
  "error": "Error de integridad de datos",
  "detalles": [
    "El email ya está registrado"
  ]
}
```

---

### 2️⃣ Listar Todos los Usuarios

**Endpoint:** `GET /api/usuarios`

**Descripción:** Retorna la lista completa de usuarios registrados.

**Request Headers:** Ninguno requerido

**Respuesta Exitosa (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Juan Pérez",
    "email": "juan.perez@example.com"
  },
  {
    "id": 2,
    "nombre": "María García",
    "email": "maria.garcia@example.com"
  },
  {
    "id": 3,
    "nombre": "Carlos López",
    "email": "carlos.lopez@example.com"
  }
]
```

**Respuesta cuando no hay usuarios:**
```json
[]
```

**Notas:**
- ✅ Retorna un array vacío si no hay usuarios (no error)
- ✅ Las contraseñas NUNCA se incluyen en las respuestas
- ✅ Los usuarios se retornan en orden de inserción

**Errores Posibles:**

| Código | Descripción |
|--------|-------------|
| **500** | Error del servidor (muy raro) |

---

### 3️⃣ Obtener Usuario por ID

**Endpoint:** `GET /api/usuarios/{id}`

**Descripción:** Busca y retorna un usuario específico por su identificador único.

**Parámetros de Path:**
- `id` (Long): Identificador único del usuario

**Ejemplo de URL:** `GET /api/usuarios/1`

**Respuesta Exitosa (200 OK):**
```json
{
  "id": 1,
  "nombre": "Juan Pérez",
  "email": "juan.perez@example.com"
}
```

**Errores Posibles:**

| Código | Descripción | Ejemplo |
|--------|-------------|---------|
| **400** | Usuario no encontrado | ID no existe en BD |
| **500** | Error del servidor | Error inesperado |

**Ejemplo de Error (400 BAD REQUEST):**
```json
{
  "timestamp": "2025-10-31T14:30:00",
  "status": 400,
  "error": "Argumento inválido",
  "detalles": [
    "No existe un usuario con el ID 999"
  ]
}
```

---

### 4️⃣ Actualizar Usuario

**Endpoint:** `PUT /api/usuarios/{id}`

**Descripción:** Actualiza todos los campos de un usuario existente.

**Parámetros de Path:**
- `id` (Long): Identificador del usuario a actualizar

**Request Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "nombre": "Juan Pérez Actualizado",
  "email": "juan.nuevo@example.com",
  "password": "nuevaPassword456"
}
```

**Validaciones:** Las mismas que en crear usuario.

**Respuesta Exitosa (200 OK):**
```json
{
  "id": 1,
  "nombre": "Juan Pérez Actualizado",
  "email": "juan.nuevo@example.com"
}
```

**Notas:**
- ✅ Todos los campos son obligatorios (actualización completa)
- ✅ El ID no se puede cambiar
- ✅ La contraseña se puede actualizar
- ✅ El nuevo email no debe estar en uso por otro usuario

**Errores Posibles:**

| Código | Descripción | Ejemplo |
|--------|-------------|---------|
| **400** | Usuario no encontrado o validación fallida | ID inexistente, datos inválidos |
| **409** | Email ya existe en otro usuario | Conflicto de email |
| **500** | Error del servidor | Error inesperado |

**Ejemplo de Error (409 CONFLICT):**
```json
{
  "timestamp": "2025-10-31T14:30:00",
  "status": 409,
  "error": "Error de integridad de datos",
  "detalles": [
    "El email ya está registrado"
  ]
}
```

---

### 5️⃣ Eliminar Usuario

**Endpoint:** `DELETE /api/usuarios/{id}`

**Descripción:** Elimina permanentemente un usuario del sistema.

**Parámetros de Path:**
- `id` (Long): Identificador del usuario a eliminar

**Respuesta Exitosa (204 NO CONTENT):**
```
(Sin contenido en el body)
```

**Notas:**
- ✅ Retorna 204 sin contenido si es exitoso
- ✅ La eliminación es permanente (no hay papelera)
- ✅ Valida que el usuario exista antes de eliminar

**Errores Posibles:**

| Código | Descripción | Ejemplo |
|--------|-------------|---------|
| **400** | Usuario no encontrado | ID no existe |
| **500** | Error del servidor | Error inesperado |

**Ejemplo de Error (400 BAD REQUEST):**
```json
{
  "timestamp": "2025-10-31T14:30:00",
  "status": 400,
  "error": "Argumento inválido",
  "detalles": [
    "No existe un usuario con el ID 999"
  ]
}
```

---

## Ejemplos de Peticiones

### Con cURL

#### Crear Usuario
```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Ana López",
    "email": "ana.lopez@example.com",
    "password": "password123"
  }'
```

#### Listar Usuarios
```bash
curl http://localhost:8080/api/usuarios
```

#### Obtener Usuario por ID
```bash
curl http://localhost:8080/api/usuarios/1
```

#### Actualizar Usuario
```bash
curl -X PUT http://localhost:8080/api/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Ana López Actualizada",
    "email": "ana.nueva@example.com",
    "password": "newpass456"
  }'
```

#### Eliminar Usuario
```bash
curl -X DELETE http://localhost:8080/api/usuarios/1
```

### Con JavaScript (Fetch API)

#### Crear Usuario
```javascript
const crearUsuario = async () => {
  const response = await fetch('http://localhost:8080/api/usuarios', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      nombre: 'Carlos Ruiz',
      email: 'carlos.ruiz@example.com',
      password: 'password123'
    })
  });

  if (response.status === 201) {
    const usuario = await response.json();
    console.log('Usuario creado:', usuario);
  } else {
    const error = await response.json();
    console.error('Error:', error);
  }
};
```

#### Listar Usuarios
```javascript
const listarUsuarios = async () => {
  const response = await fetch('http://localhost:8080/api/usuarios');
  const usuarios = await response.json();
  console.log('Usuarios:', usuarios);
};
```

#### Actualizar Usuario
```javascript
const actualizarUsuario = async (id) => {
  const response = await fetch(`http://localhost:8080/api/usuarios/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      nombre: 'Nuevo Nombre',
      email: 'nuevo.email@example.com',
      password: 'newpass123'
    })
  });

  if (response.ok) {
    const usuario = await response.json();
    console.log('Usuario actualizado:', usuario);
  }
};
```

#### Eliminar Usuario
```javascript
const eliminarUsuario = async (id) => {
  const response = await fetch(`http://localhost:8080/api/usuarios/${id}`, {
    method: 'DELETE'
  });

  if (response.status === 204) {
    console.log('Usuario eliminado exitosamente');
  }
};
```

### Con Axios (React/Vue/Angular)

```javascript
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json'
  }
});

// Crear usuario
const crearUsuario = async () => {
  try {
    const response = await api.post('/usuarios', {
      nombre: 'Pedro Martínez',
      email: 'pedro.martinez@example.com',
      password: 'pass123456'
    });
    console.log('Usuario creado:', response.data);
  } catch (error) {
    console.error('Error:', error.response.data);
  }
};

// Listar usuarios
const listarUsuarios = async () => {
  const response = await api.get('/usuarios');
  console.log('Usuarios:', response.data);
};

// Actualizar usuario
const actualizarUsuario = async (id) => {
  const response = await api.put(`/usuarios/${id}`, {
    nombre: 'Pedro Actualizado',
    email: 'pedro.nuevo@example.com',
    password: 'newpass789'
  });
  console.log('Usuario actualizado:', response.data);
};

// Eliminar usuario
const eliminarUsuario = async (id) => {
  await api.delete(`/usuarios/${id}`);
  console.log('Usuario eliminado');
};
```

---

## Arquitectura Técnica

### Patrón de Tres Capas

La aplicación sigue el patrón arquitectónico de **tres capas** (Three-Tier Architecture) que separa las responsabilidades en:

```
┌─────────────────────────────────────────────────────────┐
│                     CAPA 1: PRESENTACIÓN                 │
│                    (Controller Layer)                    │
│                                                          │
│  📍 Responsabilidades:                                   │
│    • Exponer endpoints REST                             │
│    • Validar request bodies con @Valid                  │
│    • Mapear HTTP requests/responses                     │
│    • Retornar códigos HTTP semánticos                   │
│                                                          │
│  📂 Ubicación: controller/UsuarioController.java        │
│  🔧 Anotaciones: @RestController, @RequestMapping       │
│                                                          │
└──────────────────────┬──────────────────────────────────┘
                       │
                       ↓ @Autowired (inyección)
┌─────────────────────────────────────────────────────────┐
│                   CAPA 2: LÓGICA DE NEGOCIO             │
│                     (Service Layer)                      │
│                                                          │
│  📍 Responsabilidades:                                   │
│    • Implementar reglas de negocio                      │
│    • Gestionar transacciones con @Transactional         │
│    • Validar existencia de entidades                    │
│    • Convertir entre DTOs y Entities                    │
│    • Prevenir race conditions                           │
│                                                          │
│  📂 Ubicación: service/UsuarioService.java              │
│  🔧 Anotaciones: @Service, @Transactional               │
│                                                          │
└──────────────────────┬──────────────────────────────────┘
                       │
                       ↓ @Autowired (inyección)
┌─────────────────────────────────────────────────────────┐
│                  CAPA 3: ACCESO A DATOS                 │
│                   (Repository Layer)                     │
│                                                          │
│  📍 Responsabilidades:                                   │
│    • Abstraer operaciones CRUD                          │
│    • Interactuar con la base de datos                   │
│    • Ejecutar queries JPA/SQL                           │
│                                                          │
│  📂 Ubicación: repository/UsuarioRepository.java        │
│  🔧 Anotaciones: @Repository                            │
│  🔗 Extiende: JpaRepository<Usuario, Long>              │
│                                                          │
└──────────────────────┬──────────────────────────────────┘
                       │
                       ↓ JPA/Hibernate
┌─────────────────────────────────────────────────────────┐
│                    BASE DE DATOS H2                      │
│                   (In-Memory Database)                   │
│                                                          │
│  🗄️ jdbc:h2:mem:usuariosdb                              │
│  📊 Tabla: usuarios                                      │
│  🔑 Constraints: UNIQUE(email), NOT NULL                │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

### Flujo de Datos Completo

#### Ejemplo: Crear Usuario

```
1. 🌐 Cliente HTTP
   ↓
   POST /api/usuarios
   { "nombre": "Juan", "email": "juan@example.com", "password": "pass123" }
   ↓
2. 🎮 UsuarioController
   ↓
   @Valid activa validaciones (@NotBlank, @Email, @Size)
   ↓
   Si validación OK → Convierte JSON a UsuarioRequestDTO
   Si validación FALLA → Lanza MethodArgumentNotValidException (400)
   ↓
3. 🔧 UsuarioService.crearUsuario(dto)
   ↓
   @Transactional inicia transacción
   ↓
   Convierte DTO a Entity usando Builder:
   Usuario.builder()
       .nombre(dto.nombre())
       .email(dto.email())
       .password(dto.password())
       .build()
   ↓
4. 💾 UsuarioRepository.save(usuario)
   ↓
   Hibernate ejecuta: INSERT INTO usuarios (nombre, email, password) VALUES (...)
   ↓
   Si email duplicado → Lanza DataIntegrityViolationException (409)
   Si OK → Retorna Usuario con ID generado
   ↓
5. 🔄 UsuarioService
   ↓
   Convierte Entity a DTO:
   UsuarioResponseDTO.fromEntity(usuario)
   ↓
   @Transactional confirma transacción (commit)
   ↓
6. 📤 UsuarioController
   ↓
   Envuelve en ResponseEntity<>:
   ResponseEntity.status(HttpStatus.CREATED).body(dto)
   ↓
7. 🌐 Cliente HTTP recibe:
   HTTP/1.1 201 Created
   { "id": 1, "nombre": "Juan", "email": "juan@example.com" }
```

### Patrón DTO (Data Transfer Object)

La aplicación utiliza **DTOs** para separar la representación interna (Entity) de la representación externa (API):

```
┌─────────────────────────────────────────────────────────┐
│                    UsuarioRequestDTO                     │
│                    (Input - Validado)                    │
│                                                          │
│  • nombre: String (2-100 chars, no vacío)               │
│  • email: String (formato válido, no vacío)             │
│  • password: String (min 6 chars, no vacío)             │
│                                                          │
│  ✅ Incluye anotaciones de validación                   │
│  ✅ Java Record (inmutable)                             │
│  ✅ Usado en POST y PUT                                 │
└─────────────────────────────────────────────────────────┘
                            ↓
                  Conversión manual en Service
                            ↓
┌─────────────────────────────────────────────────────────┐
│                        Usuario                           │
│                    (Entity - JPA)                        │
│                                                          │
│  • id: Long (auto-generado)                             │
│  • nombre: String                                       │
│  • email: String (UNIQUE constraint)                    │
│  • password: String                                     │
│                                                          │
│  ✅ Anotaciones JPA (@Entity, @Table, @Column)          │
│  ✅ Lombok @Data, @Builder                              │
│  ✅ Usado internamente en Service/Repository            │
└─────────────────────────────────────────────────────────┘
                            ↓
            Conversión con fromEntity() static factory
                            ↓
┌─────────────────────────────────────────────────────────┐
│                   UsuarioResponseDTO                     │
│                   (Output - Seguro)                      │
│                                                          │
│  • id: Long                                             │
│  • nombre: String                                       │
│  • email: String                                        │
│                                                          │
│  ❌ NO incluye password (seguridad)                     │
│  ✅ Java Record (inmutable)                             │
│  ✅ Retornado en todas las respuestas                   │
└─────────────────────────────────────────────────────────┘
```

**¿Por qué usar DTOs?**
- 🔒 **Seguridad:** Excluir campos sensibles (password) de respuestas
- 🔄 **Flexibilidad:** Cambiar Entity sin afectar API contract
- ✅ **Validación:** Aplicar validaciones específicas en input
- 📝 **Documentación:** API clara y predecible

### Modelo de Dominio

#### Entidad Usuario

```java
@Entity
@Table(name = "usuarios")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;
}
```

#### Tabla en Base de Datos

```sql
CREATE TABLE usuarios (
    id       BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre   VARCHAR(100)   NOT NULL,
    email    VARCHAR(255)   NOT NULL UNIQUE,
    password VARCHAR(255)   NOT NULL
);
```

#### Constraints de Integridad

| Constraint | Tipo | Aplicado a | Propósito |
|------------|------|------------|-----------|
| **PRIMARY KEY** | PK | `id` | Identificador único |
| **AUTO_INCREMENT** | Sequence | `id` | Generación automática |
| **NOT NULL** | Check | Todos | Campos obligatorios |
| **UNIQUE** | Index | `email` | Un email por usuario |
| **LENGTH** | Check | Todos | Límites de caracteres |

### Gestión de Transacciones

#### Métodos Transaccionales

```java
@Service
public class UsuarioService {

    // ✅ @Transactional en TODAS las operaciones de escritura

    @Transactional
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {
        // Garantiza atomicidad: build + save + toDTO
    }

    @Transactional
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        // Garantiza atomicidad: find + modify + save
    }

    @Transactional
    public void eliminarUsuario(Long id) {
        // Previene TOCTOU: find + delete atómico
    }

    // ❌ listarUsuarios() y buscarUsuario() NO tienen @Transactional
    // (solo lectura, no necesitan transacción explícita)
}
```

#### Prevención de Race Conditions (TOCTOU)

**Problema:**
```java
// ❌ INCORRECTO: Race condition
if (repository.existsById(id)) {
    // Otro thread podría eliminar el usuario aquí
    repository.deleteById(id);  // Falla silenciosamente
}
```

**Solución:**
```java
// ✅ CORRECTO: Validación atómica con @Transactional
@Transactional
public void eliminarUsuario(Long id) {
    Usuario usuario = repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("No existe usuario"));
    repository.delete(usuario);  // Usa la misma instancia obtenida
}
```

---

## Configuración CORS

La aplicación incluye configuración **CORS (Cross-Origin Resource Sharing)** para permitir peticiones desde aplicaciones frontend en diferentes orígenes.

### ¿Qué es CORS?

CORS es un mecanismo de seguridad del navegador que restringe peticiones HTTP entre diferentes orígenes (dominio, puerto o protocolo). Sin CORS, un frontend en `http://localhost:3000` no podría consumir una API en `http://localhost:8080`.

### Configuración Actual

**Archivo:** `src/main/java/org/jcr/usuariosenmemoria/config/WebConfig.java`

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(
                    "http://localhost:3000",    // React/Vue/Next.js
                    "http://localhost:4200",    // Angular
                    "https://miapp.com"         // Producción
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
```

### Orígenes Permitidos

| Origen | Framework | Propósito |
|--------|-----------|-----------|
| `http://localhost:3000` | React, Vue, Next.js | Servidor de desarrollo |
| `http://localhost:4200` | Angular | Angular CLI dev server |
| `https://miapp.com` | Cualquiera | Producción (CAMBIAR) |

### ⚠️ Importante para Producción

Antes de desplegar a producción:
1. Reemplazar `https://miapp.com` con tu dominio real
2. Eliminar orígenes de desarrollo (localhost)
3. Usar dominios específicos (NO wildcards)

### Probar CORS desde Frontend

```javascript
// React/Vue/Angular en localhost:3000
fetch('http://localhost:8080/api/usuarios', {
    method: 'POST',
    credentials: 'include',  // Incluir cookies si es necesario
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        nombre: 'Test',
        email: 'test@example.com',
        password: 'pass123'
    })
})
.then(response => response.json())
.then(data => console.log(data));
```

---

## Manejo de Errores

### Estructura de Respuesta de Error

Todos los errores retornan un objeto estandarizado:

```json
{
  "timestamp": "2025-10-31T14:30:00",
  "status": 400,
  "error": "Descripción del tipo de error",
  "detalles": [
    "Mensaje específico 1",
    "Mensaje específico 2"
  ]
}
```

### Códigos HTTP Utilizados

| Código | Nombre | Descripción | Cuándo se usa |
|--------|--------|-------------|---------------|
| **200** | OK | Petición exitosa | GET, PUT exitosos |
| **201** | CREATED | Recurso creado | POST exitoso |
| **204** | NO CONTENT | Sin contenido | DELETE exitoso |
| **400** | BAD REQUEST | Petición inválida | Validación fallida, ID inexistente |
| **409** | CONFLICT | Conflicto de datos | Email duplicado |
| **500** | INTERNAL ERROR | Error del servidor | Error inesperado |

### Excepciones Capturadas

#### 1. MethodArgumentNotValidException (400)

**Causa:** Falló la validación de Bean Validation (@NotBlank, @Email, @Size)

**Ejemplo de Respuesta:**
```json
{
  "timestamp": "2025-10-31T14:30:00",
  "status": 400,
  "error": "Error de validación",
  "detalles": [
    "nombre: El nombre no puede estar vacío",
    "email: Formato de email inválido",
    "password: La contraseña debe tener al menos 6 caracteres"
  ]
}
```

#### 2. IllegalArgumentException (400)

**Causa:** Usuario no encontrado (ID inexistente)

**Ejemplo de Respuesta:**
```json
{
  "timestamp": "2025-10-31T14:30:00",
  "status": 400,
  "error": "Argumento inválido",
  "detalles": [
    "No existe un usuario con el ID 999"
  ]
}
```

#### 3. DataIntegrityViolationException (409)

**Causa:** Violación de constraint UNIQUE (email duplicado)

**Ejemplo de Respuesta:**
```json
{
  "timestamp": "2025-10-31T14:30:00",
  "status": 409,
  "error": "Error de integridad de datos",
  "detalles": [
    "El email ya está registrado"
  ]
}
```

#### 4. Exception Genérica (500)

**Causa:** Error inesperado del servidor

**Ejemplo de Respuesta:**
```json
{
  "timestamp": "2025-10-31T14:30:00",
  "status": 500,
  "error": "Error interno del servidor",
  "detalles": [
    "Ha ocurrido un error inesperado. Por favor, contacte al administrador."
  ]
}
```

### GlobalExceptionHandler

**Ubicación:** `src/main/java/org/jcr/usuariosenmemoria/exception/GlobalExceptionHandler.java`

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    // Captura errores de validación Bean Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationErrors(...)

    // Captura errores de ID no encontrado
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgument(...)

    // Captura errores de email duplicado
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> handleDataIntegrityViolation(...)

    // Captura cualquier error no manejado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericException(...)
}
```

**Ventajas:**
- ✅ Centralizado en una sola clase
- ✅ Respuestas estandarizadas
- ✅ No expone stack traces
- ✅ Mensajes en español

---

## Base de Datos

### H2 In-Memory Database

La aplicación usa **H2**, una base de datos relacional en memoria escrita en Java.

#### Características

| Característica | Valor |
|----------------|-------|
| **Tipo** | In-Memory (datos se pierden al cerrar) |
| **Modo** | Embedded (incluida en el JAR) |
| **URL JDBC** | `jdbc:h2:mem:usuariosdb` |
| **Usuario** | `sa` |
| **Contraseña** | (vacía) |
| **Schema** | Auto-generado por JPA |
| **Consola Web** | Habilitada en modo dev |

#### Acceder a la Consola H2

1. **Ejecutar en modo desarrollo:**
   ```bash
   ./gradlew bootRun --args='--spring.profiles.active=dev'
   ```

2. **Abrir navegador en:** http://localhost:8080/h2-console

3. **Configurar conexión:**
   ```
   Driver Class: org.h2.Driver
   JDBC URL: jdbc:h2:mem:usuariosdb
   User Name: sa
   Password: (dejar vacío)
   ```

4. **Clic en "Connect"**

5. **Ejecutar queries SQL:**
   ```sql
   -- Ver todos los usuarios
   SELECT * FROM usuarios;

   -- Buscar por email
   SELECT * FROM usuarios WHERE email = 'juan@example.com';

   -- Contar usuarios
   SELECT COUNT(*) FROM usuarios;

   -- Insertar usuario manualmente
   INSERT INTO usuarios (nombre, email, password)
   VALUES ('Test', 'test@example.com', 'pass123');
   ```

### Schema de Base de Datos

```sql
CREATE TABLE usuarios (
    id       BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre   VARCHAR(100)   NOT NULL,
    email    VARCHAR(255)   NOT NULL UNIQUE,
    password VARCHAR(255)   NOT NULL
);

-- Índice automático en email por constraint UNIQUE
CREATE UNIQUE INDEX IF NOT EXISTS UK_email ON usuarios(email);
```

### Configuración JPA

**Archivo:** `src/main/resources/application.properties`

```properties
# Datasource H2
spring.datasource.url=jdbc:h2:mem:usuariosdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=${JPA_SHOW_SQL:false}
spring.jpa.properties.hibernate.format_sql=true

# Consola H2
spring.h2.console.enabled=${H2_CONSOLE_ENABLED:false}
spring.h2.console.path=/h2-console
```

**Modos DDL:**
- `update`: Actualiza schema sin borrar datos
- `create`: Crea schema desde cero (borra datos)
- `validate`: Solo valida (no modifica)
- `none`: No hace nada

### Perfiles de Configuración

#### Perfil: default (Producción)

- ❌ Logs SQL deshabilitados
- ❌ Consola H2 deshabilitada
- ✅ Modo seguro

#### Perfil: dev (Desarrollo)

**Archivo:** `src/main/resources/application-dev.properties`

```properties
JPA_SHOW_SQL=true
H2_CONSOLE_ENABLED=true
```

- ✅ Logs SQL habilitados
- ✅ Consola H2 habilitada
- 🔍 Ver queries ejecutadas

---

## Consideraciones de Seguridad

### ⚠️ Advertencia Importante

**Esta es una aplicación de demostración educativa. NO está lista para producción.**

### Limitaciones Actuales

| Limitación | Impacto | Solución Recomendada |
|------------|---------|----------------------|
| **Contraseñas en texto plano** | 🔴 CRÍTICO | BCrypt/Argon2 con Spring Security |
| **Sin autenticación** | 🔴 CRÍTICO | JWT o Spring Security Basic Auth |
| **Sin autorización** | 🔴 CRÍTICO | Roles y permisos con Spring Security |
| **Sin HTTPS** | 🟡 ALTO | Certificado SSL/TLS |
| **CORS permisivo** | 🟡 ALTO | Restringir a dominios específicos |
| **Sin rate limiting** | 🟡 MEDIO | Bucket4j o Spring Cloud Gateway |
| **BD en memoria** | 🟡 MEDIO | PostgreSQL, MySQL, MongoDB |
| **Sin audit logging** | 🟡 MEDIO | Spring Data Envers o AOP |

### Recomendaciones para Producción

#### 1. Cifrado de Contraseñas

```java
// Agregar dependencia
implementation 'org.springframework.boot:spring-boot-starter-security'

// Configurar BCrypt
@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

// En UsuarioService
@Autowired
private PasswordEncoder passwordEncoder;

public Usuario crearUsuario(UsuarioRequestDTO dto) {
    Usuario usuario = Usuario.builder()
        .nombre(dto.nombre())
        .email(dto.email())
        .password(passwordEncoder.encode(dto.password()))  // Cifrar
        .build();
    return repository.save(usuario);
}
```

#### 2. Autenticación con JWT

```java
// Agregar dependencias
implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
implementation 'io.jsonwebtoken:jjwt-impl:0.11.5'
implementation 'io.jsonwebtoken:jjwt-jackson:0.11.5'

// Implementar JwtTokenProvider
// Implementar UserDetailsService
// Configurar SecurityFilterChain
```

#### 3. Base de Datos Persistente

```yaml
# application.yml con PostgreSQL
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/usuariosdb
    username: ${DB_USER}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate  # Usar migrations en producción
```

#### 4. Variables de Entorno

```bash
# NO hardcodear credenciales
export DB_USER=admin
export DB_PASSWORD=secretpassword
export JWT_SECRET=my-super-secret-key
```

#### 5. CORS Restrictivo

```java
registry.addMapping("/api/**")
    .allowedOrigins("https://miapp.com")  // Solo dominio real
    .allowedMethods("GET", "POST", "PUT", "DELETE")
    .allowedHeaders("Authorization", "Content-Type")
    .allowCredentials(false);  // Deshabilitar si no se usan cookies
```

---

## Documentación Adicional

### JavaDoc

La aplicación incluye **documentación JavaDoc exhaustiva en español** con más de 2000 líneas de comentarios explicativos.

**Generar HTML:**
```bash
./gradlew javadoc
```

**Abrir:** `build/docs/javadoc/index.html`

**Contenido:**
- Explicaciones arquitectónicas
- Patrones de diseño
- Conceptos de Spring Framework
- Gestión de transacciones
- Prevención de race conditions
- API funcional de Java

### Swagger UI

**URL:** http://localhost:8080/swagger-ui.html

**Características:**
- 📖 Documentación interactiva
- 🧪 Probar endpoints desde el navegador
- 📝 Ver esquemas de DTOs
- 🔍 Códigos de respuesta documentados
- 💾 Guardar configuraciones

### Archivos de Referencia

| Archivo | Propósito |
|---------|-----------|
| **CLAUDE.md** | Arquitectura técnica para desarrolladores |
| **README.md** | Este archivo |
| **build.gradle** | Configuración de dependencias |
| **application.properties** | Configuración de Spring Boot |

---

## Estructura del Proyecto

```
usuarios-en-memoria-api-documentada-main/
│
├── src/
│   ├── main/
│   │   ├── java/org/jcr/usuariosenmemoria/
│   │   │   │
│   │   │   ├── config/                         # Configuración
│   │   │   │   ├── OpenApiConfig.java         # Swagger/OpenAPI
│   │   │   │   └── WebConfig.java             # CORS
│   │   │   │
│   │   │   ├── controller/                     # Capa de Presentación
│   │   │   │   └── UsuarioController.java     # REST endpoints
│   │   │   │
│   │   │   ├── dto/                            # Data Transfer Objects
│   │   │   │   ├── ErrorDTO.java              # Respuesta de error
│   │   │   │   ├── UsuarioRequestDTO.java     # Input DTO
│   │   │   │   └── UsuarioResponseDTO.java    # Output DTO
│   │   │   │
│   │   │   ├── exception/                      # Manejo de Excepciones
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   │
│   │   │   ├── model/                          # Entidades JPA
│   │   │   │   └── Usuario.java               # Entity Usuario
│   │   │   │
│   │   │   ├── repository/                     # Capa de Datos
│   │   │   │   └── UsuarioRepository.java     # JPA Repository
│   │   │   │
│   │   │   ├── service/                        # Capa de Negocio
│   │   │   │   └── UsuarioService.java        # Business logic
│   │   │   │
│   │   │   └── UsuariosEnMemoriaApplication.java  # Main class
│   │   │
│   │   └── resources/
│   │       ├── application.properties          # Config producción
│   │       └── application-dev.properties      # Config desarrollo
│   │
│   └── test/                                   # Tests
│       └── java/org/jcr/usuariosenmemoria/
│           └── UsuariosEnMemoriaApplicationTests.java
│
├── gradle/                                     # Gradle Wrapper
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
│
├── build/                                      # Archivos compilados
│   ├── classes/                                # .class files
│   ├── docs/javadoc/                          # JavaDoc HTML
│   ├── libs/usuarios-api.jar                  # JAR ejecutable
│   └── reports/                                # Reportes de tests
│
├── build.gradle                                # Configuración Gradle
├── settings.gradle                             # Configuración proyecto
├── gradlew                                     # Gradle wrapper (Linux/Mac)
├── gradlew.bat                                 # Gradle wrapper (Windows)
│
├── CLAUDE.md                                   # Documentación técnica
└── README.md                                   # Este archivo
```

---

## Comandos Rápidos

### Desarrollo

```bash
# Ejecutar aplicación (modo desarrollo)
./gradlew bootRun --args='--spring.profiles.active=dev'

# Compilar sin ejecutar tests
./gradlew build -x test

# Ejecutar tests con reporte
./gradlew test

# Generar JavaDoc
./gradlew javadoc

# Limpiar y recompilar
./gradlew clean build
```

### Producción

```bash
# Generar JAR
./gradlew bootJar

# Ejecutar JAR
java -jar build/libs/usuarios-api.jar

# Con profile dev
java -jar build/libs/usuarios-api.jar --spring.profiles.active=dev
```

### Verificación

```bash
# Probar aplicación
curl http://localhost:8080/api/usuarios

# Ver Swagger UI
open http://localhost:8080/swagger-ui.html

# Ver consola H2 (modo dev)
open http://localhost:8080/h2-console
```

---

## Troubleshooting

### Error: "Port 8080 is already in use"

**Solución:**
```bash
# Linux/Mac
lsof -ti:8080 | xargs kill -9

# Windows (PowerShell)
Get-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess | Stop-Process

# O cambiar el puerto
./gradlew bootRun --args='--server.port=8081'
```

### Error: "Java version not compatible"

**Solución:**
```bash
# Verificar versión instalada
java -version

# Debe ser Java 17 o superior
# Descargar desde: https://adoptium.net/
```

### Error: "Gradle not found"

**Solución:**
```bash
# Usar el wrapper incluido
./gradlew --version  # Linux/Mac
gradlew.bat --version  # Windows
```

### H2 Console no accesible

**Solución:**
```bash
# Verificar que esté en modo dev
./gradlew bootRun --args='--spring.profiles.active=dev'

# O habilitar variable
export H2_CONSOLE_ENABLED=true
./gradlew bootRun
```

### CORS Error desde Frontend

**Solución:**
1. Verificar que el origen está en `WebConfig.java`
2. Verificar que el navegador no cachea OPTIONS
3. Probar desde Swagger UI primero

---

## Preguntas Frecuentes (FAQ)

### ¿Puedo usar esta API en producción?

❌ **NO.** Esta es una aplicación educativa con limitaciones de seguridad:
- Contraseñas sin cifrar
- Sin autenticación
- Base de datos en memoria
- Sin rate limiting

### ¿Cómo agrego más campos al Usuario?

1. Modificar `Usuario.java` (agregar campo con `@Column`)
2. Modificar `UsuarioRequestDTO.java` (agregar validaciones)
3. Modificar `UsuarioResponseDTO.java` (agregar campo)
4. Actualizar conversiones en `UsuarioService.java`
5. Regenerar JavaDoc

### ¿Cómo cambio el puerto?

```bash
# Temporal
./gradlew bootRun --args='--server.port=9090'

# Permanente: editar application.properties
server.port=9090
```

### ¿Cómo persisten los datos?

❌ **No persisten.** H2 está en modo `mem:` (memoria).

Para persistir datos:
1. Cambiar a H2 file-based: `jdbc:h2:file:./data/usuariosdb`
2. O migrar a PostgreSQL/MySQL

### ¿Puedo usar PostgreSQL en lugar de H2?

✅ **Sí.** Modificar `build.gradle` y `application.properties`:

```gradle
// build.gradle
runtimeOnly 'org.postgresql:postgresql'
```

```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/usuariosdb
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

---

## Autor

**Juan Cruz Robledo**

- 📧 Email: juan.robledo@tuinstituto.edu.ar
- 🌐 Web: https://juancruz.edu.ar
- 📚 Proyecto educativo para enseñanza de Spring Boot

---

## Licencia

Este proyecto es **código de demostración educativa** sin licencia específica.

---

## Agradecimientos

Proyecto desarrollado con fines educativos para estudiantes de informática que desean aprender:
- Arquitectura en capas
- Spring Boot y Spring Data JPA
- Validaciones con Bean Validation
- Patrones de diseño (DTO, Factory, Builder)
- Gestión de transacciones
- Manejo de errores
- Documentación con OpenAPI

---

<div align="center">

**¿Tienes preguntas o sugerencias?**

Consulta el archivo [CLAUDE.md](CLAUDE.md) para detalles técnicos avanzados

**⭐ Si este proyecto te ayudó a aprender, considera darle una estrella!**

</div>
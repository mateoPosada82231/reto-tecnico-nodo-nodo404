# Reto Técnico Nodo

Aplicación Spring Boot desarrollada como parte de un reto técnico para EAFIT.

## 📋 Descripción

Este proyecto es una aplicación backend construida con Spring Boot 4.0.3 y Java 21, que utiliza PostgreSQL como base de datos y JPA para la persistencia de datos.

## 🛠️ Tecnologías

- **Java**: 21
- **Spring Boot**: 4.0.3
- **Spring Data JPA**: Para persistencia de datos
- **Spring Web MVC**: Para crear APIs REST
- **PostgreSQL**: Base de datos relacional
- **Lombok**: Para reducir código boilerplate
- **Maven**: Gestor de dependencias

## 📦 Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- Java 21 o superior
- Maven 3.6+
- PostgreSQL 12+
- Git

## 🚀 Configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/mateoPosada82231/reto-tecnico-nodo-nodo404.git
cd reto-tecnico-nodo
```

### 2. Configurar la base de datos

Crea una base de datos PostgreSQL con las siguientes credenciales (o modifica el archivo `application.yaml`):

```yaml
Base de datos: XXXXX
Usuario: XXXX
Contraseña: XXXXXX
Puerto: XXXXX
Host: XXXXXX
```

### 3. Configuración de la aplicación

La configuración se encuentra en `src/main/resources/application.yaml`. Puedes modificar los siguientes parámetros según tu entorno:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:XXXX/XXXXX
    username: XXXXXX
    password: XXXXXX
```

## 🏃 Ejecución

### Usando Maven Wrapper (recomendado)

**Windows:**
```bash
mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

### Usando Maven instalado

```bash
mvn spring-boot:run
```

La aplicación se iniciará en `http://localhost:8080`

## 🧪 Pruebas

Para ejecutar las pruebas:

```bash
mvnw.cmd test
```

## 📁 Estructura del Proyecto

```
reto-tecnico-nodo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/nodo/retotecnico/
│   │   │       └── RetoTecnicoApplication.java
│   │   └── resources/
│   │       └── application.yaml
│   └── test/
│       └── java/
│           └── com/nodo/retotecnico/
│               └── RetoTecnicoApplicationTests.java
├── pom.xml
└── README.md
```

## 🔧 Compilación

Para compilar el proyecto y generar el archivo JAR:

```bash
mvnw.cmd clean package
```

El archivo JAR se generará en `target/reto-tecnico-0.0.1-SNAPSHOT.jar`

## 📝 Notas Adicionales

- La aplicación utiliza Hibernate con la estrategia `ddl-auto: update`, lo que significa que las tablas se crearán/actualizarán automáticamente al iniciar la aplicación.
- El modo `show-sql: true` está habilitado para visualizar las consultas SQL en la consola durante el desarrollo.
- Lombok está configurado como procesador de anotaciones para generar código automáticamente.

## 👤 Autor

Proyecto desarrollado para EAFIT - Reto Técnico Nodo

## 📄 Licencia

Este proyecto es parte de un reto técnico educativo.

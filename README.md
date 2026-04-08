# Reto Técnico Nodo

Backend REST para gestión de usuarios, catálogo de extensiones, carrito y compras.

## Estado del proyecto

- API funcional con módulos:
  - `Auth` (`/api/auth`)
  - `Users` (`/api/users`)
  - `Extensions` (`/api/extensions`)
  - `Cart` (`/api/cart`)
  - `Buys` (`/api/buys`)
- Seguridad con JWT y OAuth2 (Google/Facebook)
- Reglas de ownership por email para carrito y compras
- Persistencia con JPA/Hibernate

## Stack

- Java 21
- Spring Boot 4.0.3
- Spring Security + OAuth2 Client
- JWT (`jjwt 0.12.6`)
- Spring Data JPA
- PostgreSQL
- Maven Wrapper

## Requisitos

- Java 21+
- PostgreSQL

## Configuración local

1. Clona el repositorio:

```bash
git clone https://github.com/mateoPosada82231/reto-tecnico-nodo-nodo404.git
cd reto-tecnico-nodo-nodo404
```

2. Crea variables de entorno:

```bash
cp .env.example .env
```

3. Completa en `.env`:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION_MS`
- `GOOGLE_CLIENT_ID` y `GOOGLE_CLIENT_SECRET` (opcional)
- `FACEBOOK_CLIENT_ID` y `FACEBOOK_CLIENT_SECRET` (opcional)

Base URL local: `http://localhost:8080`

## Ejecutar proyecto

Linux/macOS:

```bash
bash mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

## Ejecutar pruebas

Linux/macOS:

```bash
bash mvnw test
```

Windows:

```bash
mvnw.cmd test
```

## Documentación de endpoints

- [`API_ENDPOINTS.md`](./API_ENDPOINTS.md)

## Estructura

```text
src/main/java/com/nodo/retotecnico/
├── controllers
├── dto
├── models
├── repositories
├── security
├── serviceImpl
└── services
```

## Licencia

Proyecto de carácter académico/técnico.

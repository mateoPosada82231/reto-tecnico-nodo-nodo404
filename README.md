# Reto Técnico Nodo

Backend REST para gestión de usuarios, catálogo de extensiones, carrito y compras, con autenticación JWT y OAuth2.

## 📋 Estado actual del proyecto

- API implementada con controladores para:
  - `Auth` (`/api/auth`)
  - `Users` (`/api/users`)
  - `Extensions` (`/api/extensions`)
  - `Cart` (`/api/cart`)
  - `Buys` (`/api/buys`)
- Seguridad activa con:
  - JWT para endpoints protegidos
  - OAuth2 (Google/Facebook)
  - validación de ownership en operaciones de carrito/compra por email
- Persistencia con JPA/Hibernate sobre PostgreSQL
- Pruebas de integración en `src/test/java/.../security/SecurityIntegrationTests.java`

## 🛠️ Stack tecnológico

- **Java**: 21 (requerido por `pom.xml`)
- **Spring Boot**: 4.0.3
- **Spring Security** + **OAuth2 Client**
- **JWT** (`jjwt 0.12.6`)
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Maven** / **Maven Wrapper**

## 📦 Requisitos

- Java 21+
- PostgreSQL 12+
- Git

> Nota: si usas una versión de Java menor a 21, Maven fallará en compilación con `release version 21 not supported`.

## ⚙️ Configuración local

### 1) Clonar repositorio

```bash
git clone https://github.com/mateoPosada82231/reto-tecnico-nodo-nodo404.git
cd reto-tecnico-nodo-nodo404
```

### 2) Crear archivo de variables

```bash
cp .env.example .env
```

Completa en `.env`:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `GOOGLE_CLIENT_ID` / `GOOGLE_CLIENT_SECRET` (opcional si no probarás OAuth2)
- `FACEBOOK_CLIENT_ID` / `FACEBOOK_CLIENT_SECRET` (opcional si no probarás OAuth2)
- `JWT_SECRET`
- `JWT_EXPIRATION_MS`

### 3) Configuración de aplicación

La app carga variables desde `src/main/resources/application.yaml` con fallback local.

Puerto por defecto:

- `http://localhost:8080`

## ▶️ Ejecución

### Linux / macOS

```bash
bash mvnw spring-boot:run
```

### Windows

```bash
mvnw.cmd spring-boot:run
```

## 🧪 Pruebas

### Linux / macOS

```bash
bash mvnw test
```

### Windows

```bash
mvnw.cmd test
```

## 📚 Documentación disponible

- `API_ENDPOINTS.md`  
  Referencia de rutas HTTP, seguridad, headers y respuestas comunes.

- `FLUJO_TRABAJO_COMPRA.md`  
  Flujo end-to-end de compra (registro, login, carrito, checkout, compra directa).

- `FLUJOS_TRABAJO_4_CATEGORIAS.md`  
  Flujos completos por 4 categorías con caminos de éxito/error endpoint por endpoint y ejemplos.

## 🧱 Estructura general

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

## 📄 Licencia

Proyecto de carácter académico/técnico.

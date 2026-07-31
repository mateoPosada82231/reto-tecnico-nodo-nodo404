# Reto Técnico Nodo

Backend REST para gestión de usuarios, catálogo de extensiones, carrito, compras y CMS de contenido.

## Estado del proyecto

- API funcional con módulos:
  - `Auth` (`/api/auth`) — registro, login, logout, validación de emails y OAuth2 (Google/Facebook)
  - `Users` (`/api/users`)
  - `Extensions` (`/api/extensions`)
  - `Cart` (`/api/cart`)
  - `Buys` (`/api/buys`)
  - `SiteConfig` (`/api/config`) y `SiteContent` (`/api/content`) — CMS ligero
- Seguridad con JWT y OAuth2 (Google/Facebook)
- Reglas de ownership por email para carrito y compras
- Cifrado opcional de payloads sensibles con AES-256-GCM
- Persistencia con JPA/Hibernate

## Stack

- Java 21
- Spring Boot 4.0.3 + Spring Modulith
- Spring Security + OAuth2 Client
- JWT (`jjwt 0.12.6`)
- Spring Data JPA
- PostgreSQL
- Resend (envío de emails de bienvenida)
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
- `RESEND_API_KEY` (opcional, para emails de bienvenida)
- `ENCRYPTION_KEY` y `HMAC_KEY` (Base64 32 bytes — obligatorios para cifrado)
- `FRONTEND_URL` (URL del frontend para el redirect tras OAuth2)

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

- [`API_ENDPOINTS.md`](./API_ENDPOINTS.md) — referencia completa de rutas, payloads, seguridad, cifrado y rate limiting.

## Estructura

```text
src/main/java/com/nodo/retotecnico/
├── controllers          # REST endpoints (Auth, Users, Extensions, Buys, Cart, SiteConfig, SiteContent)
├── dto                  # DTOs de request/response (LoginRequest, RegisterRequest, AuthResponse, UserResponseDTO...)
├── models               # Entidades JPA (Users, Extensions, Buys, CartItem, SiteContent, SiteConfig, AuthProvider)
├── repositories         # Spring Data JPA
├── security             # JWT, filtros, OAuth2, rate limit, cifrado
│   ├── config/          # SecurityConfig, FilterRegistrationConfig
│   └── handlers/        # JSON error handlers (entry point, access denied, OAuth2 failure)
├── serviceImpl          # Implementaciones de servicios
└── services             # Interfaces de servicios
```

## Endpoints de Auth (`/api/auth`)

| Método | Ruta                  | Descripción                                                          | Acceso       |
|--------|-----------------------|----------------------------------------------------------------------|--------------|
| GET    | `/api/auth/emails`    | Lista de emails ya registrados (para validación de duplicados en el cliente) | Público      |
| POST   | `/api/auth/register`  | Crea usuario local (`provider=FORM`, `betaTester=false`)             | Público      |
| POST   | `/api/auth/login`     | Autentica con email/password y retorna JWT (`type=USER`)             | Público      |
| POST   | `/api/auth/logout`    | Revoca el token enviado en `Authorization`                          | Autenticado  |
| POST   | `/api/auth/beta/register` | Crea usuario beta tester (`betaTester=true`)                    | Público      |
| POST   | `/api/auth/beta/login`    | Autentica beta tester, retorna JWT (`type=BETA`)                | Público      |

> El endpoint `GET /api/auth/emails` se diseñó para que el frontend cargue la lista de emails una sola vez y valide duplicados en el store (zustand) sin hacer peticiones a la BD por cada intento de registro.

## OAuth2

- `GET /oauth2/authorization/google` (y `google-beta`)
- `GET /oauth2/authorization/facebook` (y `facebook-beta`)
- En éxito, `OAuth2SuccessHandler` genera un JWT y redirige a `${FRONTEND_URL}/oauth2/callback?token=...&email=...`
- Los registros `*-beta` emiten un JWT con `type=BETA`

## Modelos principales

- **`Users`** — PK: `email`. Campos cifrados con AES-256-GCM: `identification`, `fullName`, `mobileNumber`. Campo `password` hasheado con BCrypt (`@JsonProperty(access = WRITE_ONLY)` para no exponerlo). `provider` (`FORM`/`GOOGLE`/`FACEBOOK`), `providerId`, `betaTester`.
- **`SiteContent`** — CMS de textos de UI (unique constraint por `section_key + content_key + language`).
- **`SiteConfig`** — CMS de configuraciones estructuradas (JSON).

## Licencia

Proyecto de carácter académico/técnico.

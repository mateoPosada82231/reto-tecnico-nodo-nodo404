# API Endpoints - Reto Tecnico Nodo

Documento de referencia de rutas HTTP de la aplicacion, basado en los controladores y la configuracion de seguridad actual.

## Base URL y headers

- Base URL local: `http://localhost:8080`
- Header para rutas protegidas:

```http
Authorization: Bearer <JWT>
Content-Type: application/json
```

## Seguridad global

Segun `SecurityConfig` y `JwtAuthFilter`:

- Publico (sin token):
  - `POST /api/auth/register`
  - `POST /api/auth/login`
  - `/oauth2/**`
  - `/login/**`
  - `/error`
- Requiere autenticacion:
  - `GET/POST/PUT/DELETE /api/users/**`
  - `GET/POST/PUT/DELETE /api/extensions/**`
  - `GET/POST/DELETE /api/cart/**`
  - `GET/POST /api/buys/**`
  - Cualquier otra ruta no listada como publica
- Regla extra de propietario (aplicada en controladores de carrito y compras):
  - Si el email del request no coincide con el email del token: `403 Forbidden`
  - Si no hay autenticacion valida: `401 Unauthorized`

## Formato de error de seguridad

Respuesta JSON estandar para `401`/`403`:

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid or expired token",
  "path": "/api/cart/user@nodo.com",
  "timestamp": "2026-03-24T16:00:00Z"
}
```

Para `403`, `error` llega como `Forbidden`.

---

## 1) Auth - `/api/auth`

### POST `/api/auth/register`
Crea usuario local (provider `FORM`).

- Auth requerida: No
- Body:

```json
{
  "email": "user@nodo.com",
  "password": "Secret123!",
  "country": "CO",
  "identification": "123456789",
  "fullName": "Usuario Demo",
  "mobileNumber": "3001234567",
  "dateOfBirth": "1998-10-20"
}
```

- Respuestas comunes:
  - `200 OK`: `"Usuario creado con exito"`
  - `400 Bad Request`: `"El email ya esta registrado"`

### POST `/api/auth/login`
Autentica por email/password y devuelve JWT.

- Auth requerida: No
- Body:

```json
{
  "email": "user@nodo.com",
  "password": "Secret123!"
}
```

- Respuestas comunes:
  - `200 OK`:

```json
{
  "token": "<JWT>"
}
```

  - `401 Unauthorized`:

```json
{
  "message": "Credenciales invalidas"
}
```

---

## 2) OAuth2 (rutas del framework)

Estas rutas no estan en un controlador propio, pero existen por configuracion de Spring Security.

### GET `/oauth2/authorization/google`
Inicia login OAuth2 con Google.

### GET `/oauth2/authorization/facebook`
Inicia login OAuth2 con Facebook.

### GET `/login/oauth2/code/{registrationId}`
Callback OAuth2. En exito, responde JSON con JWT:

```json
{
  "token": "<JWT>"
}
```

---

## 3) Users - `/api/users`

Todas requieren JWT.

### GET `/api/users`
Lista usuarios.

### GET `/api/users/{email}`
Obtiene un usuario por email.

### POST `/api/users`
Crea usuario a partir de entidad `Users`.

### PUT `/api/users/{email}`
Actualiza usuario por email.

### DELETE `/api/users/{email}`
Elimina usuario por email.

Respuestas comunes del modulo:

- `200 OK`, `201 Created`, `204 No Content`
- `404 Not Found` en consulta/actualizacion/borrado de usuario inexistente
- `401 Unauthorized` sin token o token invalido

---

## 4) Extensions - `/api/extensions`

Todas requieren JWT.

### GET `/api/extensions`
Lista extensiones.

### GET `/api/extensions/{id}`
Obtiene extension por id.

### GET `/api/extensions/category/{category}`
Filtra por categoria.

### GET `/api/extensions/distributor/{distributor}`
Filtra por distribuidor.

### GET `/api/extensions/age/{age}`
Devuelve extensiones con `requiredAge <= age`.

### GET `/api/extensions/trending`
Devuelve una lista de 0 o 1 extension con mas compras.

### GET `/api/extensions/random`
Devuelve una lista de 0 o 1 extension aleatoria.

### POST `/api/extensions`
Crea extension.

Body de ejemplo:

```json
{
  "price": 19.99,
  "requiredAge": 16,
  "name": "Expansion Pack",
  "aboutGame": "Contenido adicional",
  "platforms": "PC",
  "languages": "ES,EN",
  "distributor": "Nodo Games",
  "publicationDate": "2026-01-10",
  "category": "Accion"
}
```

### PUT `/api/extensions/{id}`
Actualiza extension.

### DELETE `/api/extensions/{id}`
Elimina extension.

Respuestas comunes del modulo:

- `200 OK`, `201 Created`, `204 No Content`
- `404 Not Found` en id inexistente
- `401 Unauthorized` sin token o token invalido

---

## 5) Cart - `/api/cart`

Todas requieren JWT y validan ownership por email (token vs request).

### GET `/api/cart/{email}`
Lista items del carrito de un usuario junto con el total de la compra actual.

- `200 OK`:

```json
{
  "items": [
    {
      "id": 10,
      "language": "ES",
      "platform": "PC",
      "extension": { "id": 1, "price": 19.99 }
    }
  ],
  "itemsCount": 1,
  "totalPrice": 19.99
}
```

- `200 OK`
- `401 Unauthorized`
- `403 Forbidden` si el email no coincide con el token

### POST `/api/cart`
Agrega un item al carrito.

Body:

```json
{
  "email": "user@nodo.com",
  "extensionId": 1,
  "language": "ES",
  "platform": "PC"
}
```

Respuestas comunes:

- `200 OK`: item creado
- `400 Bad Request`: validaciones de negocio (usuario no encontrado, extension no encontrada, item repetido)
- `401 Unauthorized`
- `403 Forbidden`

### DELETE `/api/cart/item/{cartItemId}?email={email}`
Elimina un item puntual del carrito del usuario.

- `200 OK`: `"Producto eliminado del carrito"`
- `401 Unauthorized`
- `403 Forbidden`

### DELETE `/api/cart/clear/{email}`
Limpia todo el carrito del usuario.

- `200 OK`: `"Carrito limpiado correctamente"`
- `401 Unauthorized`
- `403 Forbidden`

---

## 6) Buys - `/api/buys`

Todas requieren JWT. En endpoints de compra, tambien se valida ownership por email.

### GET `/api/buys`
Lista compras.

### GET `/api/buys/{id}`
Obtiene compra por id.

Respuestas comunes en consultas:

- `200 OK`
- `404 Not Found` (solo por id)
- `401 Unauthorized`

### POST `/api/buys`
Compra una extension (crea un registro en `buys`).

Body:

```json
{
  "userEmail": "user@nodo.com",
  "extensionId": 1,
  "paymentMethod": "CARD"
}
```

Respuestas comunes:

- `201 Created`
- `401 Unauthorized`
- `403 Forbidden` si `userEmail` no coincide con el token

### POST `/api/buys/direct`
Compra directa sin carrito (crea un unico registro en `buys`).

Body:

```json
{
  "email": "user@nodo.com",
  "extensionId": 1,
  "paymentMethod": "CARD",
  "language": "ES",
  "platform": "PC"
}
```

Respuestas comunes:

- `201 Created`:

```json
{
  "buy": {
    "id": 101,
    "paymentMethod": "CARD",
    "language": "ES",
    "platform": "PC",
    "extension": { "id": 1, "price": 19.99 }
  },
  "totalPrice": 19.99,
  "message": "Compra directa realizada con exito"
}
```
- `401 Unauthorized`
- `403 Forbidden` si `email` no coincide con el token

### POST `/api/buys/checkout`
Convierte todos los items del carrito del usuario en compras y luego limpia el carrito.

Body:

```json
{
  "userEmail": "user@nodo.com",
  "paymentMethod": "CARD"
}
```

Notas:

- Se crea 1 registro en `buys` por cada item del carrito.
- Si el carrito esta vacio, el servicio lanza error de negocio.
- `extensionId` no se usa para checkout.

Respuestas comunes:

- `200 OK`:

```json
{
  "buys": [
    {
      "id": 201,
      "paymentMethod": "CARD",
      "language": "ES",
      "platform": "PC",
      "extension": { "id": 1, "price": 19.99 }
    }
  ],
  "itemsCount": 1,
  "totalPrice": 19.99,
  "message": "Compra realizada con exito y carrito vaciado."
}
```
- `401 Unauthorized`
- `403 Forbidden`

---

## Lista rapida de endpoints

### Publicos

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /oauth2/authorization/google`
- `GET /oauth2/authorization/facebook`
- `GET /login/oauth2/code/{registrationId}`

### Protegidos

- `GET /api/users`
- `GET /api/users/{email}`
- `POST /api/users`
- `PUT /api/users/{email}`
- `DELETE /api/users/{email}`
- `GET /api/extensions`
- `GET /api/extensions/{id}`
- `GET /api/extensions/category/{category}`
- `GET /api/extensions/distributor/{distributor}`
- `GET /api/extensions/age/{age}`
- `GET /api/extensions/trending`
- `GET /api/extensions/random`
- `POST /api/extensions`
- `PUT /api/extensions/{id}`
- `DELETE /api/extensions/{id}`
- `GET /api/cart/{email}`
- `POST /api/cart`
- `DELETE /api/cart/item/{cartItemId}?email={email}`
- `DELETE /api/cart/clear/{email}`
- `GET /api/buys`
- `GET /api/buys/{id}`
- `POST /api/buys`
- `POST /api/buys/direct`
- `POST /api/buys/checkout`


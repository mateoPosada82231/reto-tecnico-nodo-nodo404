# Flujos de trabajo de la aplicación (4 categorías)

Este documento divide todos los flujos funcionales de la API en 4 categorías y explica los caminos posibles endpoint por endpoint, incluyendo ejemplos de éxito y error.

## Convenciones

- Base URL local: `http://localhost:8080`
- Header para endpoints protegidos:

```http
Authorization: Bearer <JWT>
Content-Type: application/json
```

- Errores de seguridad (401/403) se devuelven en JSON.

---

## 1) Categoría: Autenticación y sesión

Incluye registro, login, logout y OAuth2.

### 1.1 POST `/api/auth/register`

**Qué hace:** crea usuario local (`provider=FORM`).

#### Camino de éxito
- Si el email no existe, crea usuario.
- **Respuesta:** `200 OK`

```json
"Usuario creado con éxito"
```

#### Camino de error
- Si el email ya existe.
- **Respuesta:** `400 Bad Request`

```json
"El email ya está registrado"
```

#### Ejemplo request

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

---

### 1.2 POST `/api/auth/login`

**Qué hace:** autentica email/password y retorna JWT.

#### Camino de éxito
- Credenciales válidas.
- **Respuesta:** `200 OK`

```json
{
  "token": "<JWT>"
}
```

#### Camino de error
- Credenciales inválidas.
- **Respuesta:** `401 Unauthorized`

```json
{
  "message": "Credenciales inválidas"
}
```

#### Ejemplo request

```json
{
  "email": "user@nodo.com",
  "password": "Secret123!"
}
```

---

### 1.3 POST `/api/auth/logout` (protegido)

**Qué hace:** revoca el token actual.

#### Camino de éxito
- Token presente, formato correcto y válido.
- **Respuesta:** `200 OK`

```json
{
  "message": "Sesion cerrada con exito"
}
```

#### Caminos de error
- Sin token o formato inválido.
- Token inválido/expirado.
- **Respuesta:** `401 Unauthorized`

```json
{
  "message": "Token ausente o con formato invalido"
}
```

o

```json
{
  "message": "Token invalido o expirado"
}
```

---

### 1.4 GET `/oauth2/authorization/google` (público)
### 1.5 GET `/oauth2/authorization/facebook` (público)

**Qué hacen:** inician handshake OAuth2 con proveedor.

#### Camino de éxito
- Redirección al proveedor.

#### Camino de error
- Cliente OAuth mal configurado.
- **Respuesta esperada:** `401 Unauthorized` al fallar autenticación OAuth2.

---

### 1.6 GET `/login/oauth2/code/{registrationId}` (público)

**Qué hace:** callback OAuth2.

#### Camino de éxito
- OAuth2 correcto.
- **Respuesta:** `200 OK`

```json
{
  "token": "<JWT>"
}
```

#### Camino de error
- OAuth2 rechazado/fallido.
- **Respuesta:** `401 Unauthorized` con JSON de error de seguridad.

---

## 2) Categoría: Gestión de usuarios y perfiles

Incluye CRUD de usuarios (`/api/users`). Todos requieren JWT.

### 2.1 GET `/api/users`

#### Éxito
- **Respuesta:** `200 OK` lista de usuarios.

#### Error
- Sin token/token inválido.
- **Respuesta:** `401 Unauthorized`.

---

### 2.2 GET `/api/users/{email}`

#### Éxito
- Usuario existe.
- **Respuesta:** `200 OK` con usuario.

#### Error
- Usuario no existe: `404 Not Found`.
- Sin token/token inválido: `401 Unauthorized`.

---

### 2.3 POST `/api/users`

#### Éxito
- Crea usuario.
- **Respuesta:** `201 Created`.

#### Error
- Sin token/token inválido: `401 Unauthorized`.
- Si el cuerpo incumple restricciones de BD, puede fallar en persistencia (error del servidor/BD).

#### Ejemplo request

```json
{
  "email": "nuevo@nodo.com",
  "password": "Secret123!",
  "country": "CO",
  "identification": "99887766",
  "fullName": "Nuevo Usuario",
  "mobileNumber": "3009876543",
  "dateOfBirth": "1996-03-15"
}
```

---

### 2.4 PUT `/api/users/{email}`

#### Éxito
- Usuario existe y se actualiza.
- **Respuesta:** `200 OK`.

#### Error
- Usuario no existe: `404 Not Found`.
- Sin token/token inválido: `401 Unauthorized`.

---

### 2.5 DELETE `/api/users/{email}`

#### Éxito
- Usuario existe y se elimina.
- **Respuesta:** `204 No Content`.

#### Error
- Usuario no existe: `404 Not Found`.
- Sin token/token inválido: `401 Unauthorized`.

---

## 3) Categoría: Catálogo de extensiones y exploración

Incluye consultas y administración de extensiones (`/api/extensions`). Todos requieren JWT.

### 3.1 GET `/api/extensions`

#### Éxito
- **Respuesta:** `200 OK` (lista; puede estar vacía).

#### Error
- `401 Unauthorized` sin token/token inválido.

---

### 3.2 GET `/api/extensions/{id}`

#### Éxito
- ID existe: `200 OK`.

#### Error
- ID no existe: `404 Not Found`.
- Sin token/token inválido: `401 Unauthorized`.

---

### 3.3 GET `/api/extensions/category/{category}`
### 3.4 GET `/api/extensions/distributor/{distributor}`
### 3.5 GET `/api/extensions/age/{age}`
### 3.6 GET `/api/extensions/trending`
### 3.7 GET `/api/extensions/random`

#### Éxito
- `200 OK` con lista filtrada/calculada (puede venir vacía).

#### Error
- `401 Unauthorized` sin token/token inválido.

---

### 3.8 POST `/api/extensions`

#### Éxito
- Crea extensión.
- **Respuesta:** `201 Created`.

#### Error
- `401 Unauthorized` sin token/token inválido.
- Si faltan campos requeridos por BD, puede fallar persistencia.

#### Ejemplo request

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

---

### 3.9 PUT `/api/extensions/{id}`

#### Éxito
- ID existe y se actualiza: `200 OK`.

#### Error
- ID no existe: `404 Not Found`.
- Sin token/token inválido: `401 Unauthorized`.

---

### 3.10 DELETE `/api/extensions/{id}`

#### Éxito
- ID existe y se elimina: `204 No Content`.

#### Error
- ID no existe: `404 Not Found`.
- Sin token/token inválido: `401 Unauthorized`.

---

## 4) Categoría: Carrito, checkout y compras

Incluye `/api/cart` y `/api/buys`.  
Todos requieren JWT y además validan ownership por email en operaciones sensibles (debe coincidir con el email del token).

### 4.1 GET `/api/cart/{email}`

#### Éxito
- Email coincide con token.
- **Respuesta:** `200 OK` con resumen de carrito.

```json
{
  "items": [],
  "itemsCount": 0,
  "totalPrice": 0
}
```

#### Error
- Sin token/token inválido: `401 Unauthorized`.
- Email distinto al del token: `403 Forbidden`.

---

### 4.2 POST `/api/cart`

#### Éxito
- Email coincide con token.
- Usuario y extensión existen.
- `language` y `platform` no vacíos.
- Item no repetido (misma combinación user+extension+language+platform).
- **Respuesta:** `200 OK` con item creado.

#### Error
- Sin token/token inválido: `401 Unauthorized`.
- Email distinto: `403 Forbidden`.
- Datos de negocio inválidos: `400 Bad Request`.
  - `"Los campos language y platform son obligatorios"`
  - `"Usuario no encontrado"`
  - `"Producto no encontrado"`
  - `"El producto ya está en el carrito"`

#### Ejemplo request

```json
{
  "email": "user@nodo.com",
  "extensionId": 1,
  "language": "ES",
  "platform": "PC"
}
```

---

### 4.3 DELETE `/api/cart/item/{cartItemId}?email={email}`

#### Éxito
- Ownership válido.
- **Respuesta:** `200 OK`

```json
"Producto eliminado del carrito"
```

#### Error
- Sin token/token inválido: `401 Unauthorized`.
- Email distinto: `403 Forbidden`.

---

### 4.4 DELETE `/api/cart/clear/{email}`

#### Éxito
- Ownership válido.
- **Respuesta:** `200 OK`

```json
"Carrito limpiado correctamente"
```

#### Error
- Sin token/token inválido: `401 Unauthorized`.
- Email distinto: `403 Forbidden`.

---

### 4.5 GET `/api/buys`

#### Éxito
- **Respuesta:** `200 OK` lista de compras.

#### Error
- Sin token/token inválido: `401 Unauthorized`.

---

### 4.6 GET `/api/buys/{id}`

#### Éxito
- ID existe: `200 OK`.

#### Error
- ID no existe: `404 Not Found`.
- Sin token/token inválido: `401 Unauthorized`.

---

### 4.7 POST `/api/buys`

#### Éxito
- Ownership válido (`userEmail` = email del token).
- Usuario y extensión existen.
- **Respuesta:** `201 Created`.

#### Error
- Sin token/token inválido: `401 Unauthorized`.
- Ownership inválido: `403 Forbidden`.
- Usuario/extensión inexistente: error de negocio (runtime).

#### Ejemplo request

```json
{
  "userEmail": "user@nodo.com",
  "extensionId": 1,
  "paymentMethod": "CARD"
}
```

---

### 4.8 POST `/api/buys/direct`

#### Éxito
- Ownership válido (`email` = email token).
- `language` y `platform` no vacíos.
- Usuario y extensión existen.
- **Respuesta:** `201 Created`.

```json
{
  "buy": {
    "id": 101,
    "paymentMethod": "CARD",
    "language": "ES",
    "platform": "PC"
  },
  "totalPrice": 19.99,
  "message": "Compra directa realizada con exito"
}
```

#### Error
- Sin token/token inválido: `401 Unauthorized`.
- Ownership inválido: `403 Forbidden`.
- `language` o `platform` vacíos: `400 Bad Request` (`"language y platform son obligatorios"`).
- Usuario/extensión inexistente: error de negocio (runtime).

#### Ejemplo request

```json
{
  "email": "user@nodo.com",
  "extensionId": 1,
  "language": "ES",
  "platform": "PC",
  "paymentMethod": "PSE"
}
```

---

### 4.9 POST `/api/buys/checkout`

#### Éxito
- Ownership válido (`userEmail` = email token).
- Carrito con al menos 1 item.
- **Respuesta:** `200 OK` con resumen.

```json
{
  "buys": [],
  "itemsCount": 2,
  "totalPrice": 34.49,
  "message": "Compra realizada con exito y carrito vaciado."
}
```

#### Error
- Sin token/token inválido: `401 Unauthorized`.
- Ownership inválido: `403 Forbidden`.
- Usuario no existe.
- Carrito vacío (`"El carrito está vacío, no hay nada que comprar."`).

#### Ejemplo request

```json
{
  "userEmail": "user@nodo.com",
  "paymentMethod": "CARD"
}
```

---

## Resumen de categorías vs endpoints

1. **Autenticación y sesión**  
   `/api/auth/register`, `/api/auth/login`, `/api/auth/logout`, `/oauth2/**`, `/login/oauth2/code/{registrationId}`

2. **Gestión de usuarios y perfiles**  
   `/api/users` (GET, POST), `/api/users/{email}` (GET, PUT, DELETE)

3. **Catálogo de extensiones y exploración**  
   `/api/extensions` y subrutas de filtros/recomendaciones

4. **Carrito, checkout y compras**  
   `/api/cart/**`, `/api/buys/**`

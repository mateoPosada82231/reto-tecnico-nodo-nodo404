# Flujo de trabajo completo (usuario, extensiones, carrito y compra)

Este documento muestra un flujo end-to-end usando los endpoints reales de la API.

## Objetivo del flujo

Cubrir de punta a punta:

1. Crear usuario
2. Loguearse y obtener JWT
3. Crear dos extensiones
4. Consultar extensiones
5. Agregar extensiones al carrito
6. Comprar desde carrito (checkout)
7. Comprar directo sin carrito
8. Validar respuestas y seguridad (`401` y `403`)

## Convenciones

- Base URL: `http://localhost:8080`
- Header protegido:

```http
Authorization: Bearer <JWT>
Content-Type: application/json
```

---

## Paso 1 - Registrar usuario

**Endpoint:** `POST /api/auth/register`

```json
{
  "email": "demo.user@nodo.com",
  "password": "Secret123!",
  "country": "CO",
  "identification": "1099001122",
  "fullName": "Demo User",
  "mobileNumber": "3001112233",
  "dateOfBirth": "1999-04-21"
}
```

**Esperado:** `200 OK` con mensaje de creacion.

---

## Paso 2 - Login para obtener token JWT

**Endpoint:** `POST /api/auth/login`

```json
{
  "email": "demo.user@nodo.com",
  "password": "Secret123!"
}
```

**Esperado:** `200 OK`

```json
{
  "token": "<JWT>"
}
```

Guarda ese valor para todos los endpoints protegidos.

---

## Paso 3 - Crear dos extensiones

**Endpoint:** `POST /api/extensions` (x2)

> Requiere `Authorization: Bearer <JWT>`.

### Extension 1

```json
{
  "price": 24.99,
  "requiredAge": 12,
  "name": "Sky Realms",
  "aboutGame": "Mapa y misiones adicionales",
  "platforms": "PC,PS5",
  "languages": "ES,EN",
  "distributor": "Nodo Studio",
  "publicationDate": "2026-03-01",
  "category": "Adventure"
}
```

### Extension 2

```json
{
  "price": 14.5,
  "requiredAge": 16,
  "name": "Night Arena",
  "aboutGame": "Modo competitivo",
  "platforms": "PC,XBOX",
  "languages": "ES,EN,PT",
  "distributor": "Nodo Studio",
  "publicationDate": "2026-03-10",
  "category": "Action"
}
```

**Esperado:** `201 Created` en cada una. Guarda los `id` retornados.

---

## Paso 4 - Consultar extensiones creadas

Endpoints utiles:

- `GET /api/extensions`
- `GET /api/extensions/{id}`
- `GET /api/extensions/category/{category}`

**Esperado:** `200 OK` con lista/objeto de extensiones.

---

## Paso 5 - Agregar extensiones al carrito

**Endpoint:** `POST /api/cart` (x2)

> Requiere token y que `email` coincida con el usuario autenticado.

### Agregar extension 1

```json
{
  "email": "demo.user@nodo.com",
  "extensionId": 1,
  "language": "ES",
  "platform": "PC"
}
```

### Agregar extension 2

```json
{
  "email": "demo.user@nodo.com",
  "extensionId": 2,
  "language": "EN",
  "platform": "PC"
}
```

**Esperado:** `200 OK` por cada item agregado.

Luego verificar carrito:

- `GET /api/cart/demo.user@nodo.com` -> `200 OK`
- La respuesta incluye `items`, `itemsCount` y `totalPrice`

---

## Paso 6 - Comprar todo el carrito (checkout)

**Endpoint:** `POST /api/buys/checkout`

```json
{
  "userEmail": "demo.user@nodo.com",
  "paymentMethod": "CARD"
}
```

**Esperado:**

- `200 OK` con resumen: `buys`, `itemsCount`, `totalPrice`, `message`
- Se crea 1 registro en `buys` por cada item en carrito
- El carrito queda vacio

Validacion sugerida:

- `GET /api/cart/demo.user@nodo.com` -> `itemsCount: 0`, `totalPrice: 0`, `items: []`
- `GET /api/buys` -> aparecen nuevas compras

---

## Paso 7 - Compra directa (sin carrito)

**Endpoint:** `POST /api/buys/direct`

```json
{
  "email": "demo.user@nodo.com",
  "extensionId": 1,
  "paymentMethod": "PSE",
  "language": "ES",
  "platform": "PC"
}
```

**Esperado:**

- `201 Created` con resumen: `buy`, `totalPrice`, `message`
- Se crea un unico registro en `buys`
- No depende del carrito

---

## Paso 8 - Pruebas de seguridad obligatorias

### Caso A: sin token

Probar cualquier endpoint protegido (ejemplo: `POST /api/cart`).

**Esperado:** `401 Unauthorized`.

### Caso B: token valido pero email de otra cuenta

Usar token de `demo.user@nodo.com` y enviar body:

```json
{
  "email": "otro.user@nodo.com",
  "extensionId": 1
}
```

**Esperado:** `403 Forbidden` por regla de ownership.

Esto aplica a operaciones de `cart` y `buys` donde se envia email.

---

## Flujo resumido

1. `POST /api/auth/register`
2. `POST /api/auth/login` -> obtener JWT
3. `POST /api/extensions` (crear extension A)
4. `POST /api/extensions` (crear extension B)
5. `POST /api/cart` (agregar A)
6. `POST /api/cart` (agregar B)
7. `GET /api/cart/{email}` (validar carrito)
8. `POST /api/buys/checkout` (compra desde carrito)
9. `POST /api/buys/direct` (compra directa alternativa)

Con esto queda cubierto el recorrido completo solicitado, incluyendo autenticacion, carrito, checkout, compra directa y validaciones de seguridad global.

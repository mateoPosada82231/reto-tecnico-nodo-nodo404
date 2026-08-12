package com.nodo.retotecnico.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nodo.retotecnico.models.AuthProvider;
import com.nodo.retotecnico.models.Extensions;
import com.nodo.retotecnico.models.ExtensionTranslation;
import com.nodo.retotecnico.models.Users;
import com.nodo.retotecnico.repositories.BuysRepository;
import com.nodo.retotecnico.repositories.CartItemRepository;
import com.nodo.retotecnico.repositories.ExtensionsRepository;
import com.nodo.retotecnico.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.defer-datasource-initialization=true",
        "spring.sql.init.mode=always",
        "spring.jpa.show-sql=false",
        "spring.security.oauth2.client.registration.google.client-id=test",
        "spring.security.oauth2.client.registration.google.client-secret=test",
        "encryption.key=R4VhZzxNzz9gTs3CJ23LH0ZpCvCm74EScFsvgvtMOss=",
        "encryption.hmac-key=gO0Z1+VvgTxdqhARAM0lyHkHjrESyyiyxVuuXCdUe1Y=",
        "rate-limit.enabled=false"
})
class SecurityIntegrationTests {

    private final RestTemplate restTemplate = new RestTemplate();

    @LocalServerPort
    private int port;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ExtensionsRepository extensionsRepository;

    @Autowired
    private BuysRepository buysRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TEST_EMAIL = "tester@nodo.com";
    private static final String TEST_PASSWORD = "secret";
    private static final String OTHER_EMAIL = "other@nodo.com";

    private Integer extensionId;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
        buysRepository.deleteAll();
        cartItemRepository.deleteAll();
        extensionsRepository.deleteAll();
        usersRepository.deleteAll();

        Users user = new Users();
        user.setEmail(TEST_EMAIL);
        user.setPassword(passwordEncoder.encode(TEST_PASSWORD));
        user.setFullName("Tester Nodo");
        user.setProvider(AuthProvider.FORM);
        usersRepository.save(user);

        Users otherUser = new Users();
        otherUser.setEmail(OTHER_EMAIL);
        otherUser.setPassword(passwordEncoder.encode(TEST_PASSWORD));
        otherUser.setFullName("Other Nodo");
        otherUser.setProvider(AuthProvider.FORM);
        usersRepository.save(otherUser);

        Extensions extension = new Extensions();
        extension.setPrice(BigDecimal.valueOf(9.99));
        extension.setRequiredAge(13);
        extension.setPublicationDate(LocalDate.now());
        extension.setImage("https://example.com/ext-dlc-test.png");
        extension.setPublic(true);
        ExtensionTranslation trEs = new ExtensionTranslation();
        trEs.setLanguage("es");
        trEs.setName("DLC Test");
        trEs.setAboutGame("Test");
        trEs.setPlatforms("PC");
        trEs.setLanguages("ES");
        trEs.setDistributor("Nodo");
        trEs.setCategory("Accion");
        trEs.setExtension(extension);
        extension.getTranslations().add(trEs);
        extensionId = extensionsRepository.save(extension).getId();
    }

    @Test
    void loginShouldReturnJwtToken() throws Exception {
        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl + "/api/auth/login",
                jsonEntity(Map.of("email", TEST_EMAIL, "password", TEST_PASSWORD)),
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JsonNode body = objectMapper.readTree(response.getBody());
        assertNotNull(body.get("token").asText());
    }

    @Test
    void protectedEndpointWithoutTokenShouldFailWithJson() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/api/users", String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        JsonNode body = objectMapper.readTree(response.getBody());
        assertEquals("Unauthorized", body.get("error").asText());
    }

    @Test
    void protectedEndpointWithValidTokenShouldSucceed() throws Exception {
        String token = obtainJwt();

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/api/users/" + TEST_EMAIL,
                HttpMethod.GET,
                authEntity(token, null),
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void usersListEndpointWithNonAdminTokenShouldFailWithForbidden() throws Exception {
        String token = obtainJwt();

        HttpClientErrorException.Forbidden forbidden = assertThrows(
                HttpClientErrorException.Forbidden.class,
                () -> restTemplate.exchange(
                        baseUrl + "/api/users",
                        HttpMethod.GET,
                        authEntity(token, null),
                        String.class
                )
        );
        assertEquals(HttpStatus.FORBIDDEN, forbidden.getStatusCode());
    }

    @Test
    void logoutShouldInvalidateCurrentToken() throws Exception {
        String token = obtainJwt();

        ResponseEntity<String> logoutResponse = restTemplate.exchange(
                baseUrl + "/api/auth/logout",
                HttpMethod.POST,
                authEntity(token, null),
                String.class
        );

        assertEquals(HttpStatus.OK, logoutResponse.getStatusCode());

        ResponseEntity<String> afterLogoutResponse = restTemplate.exchange(
                baseUrl + "/api/users",
                HttpMethod.GET,
                authEntity(token, null),
                String.class
        );

        assertEquals(HttpStatus.UNAUTHORIZED, afterLogoutResponse.getStatusCode());
    }

    @Test
    void cartEndpointWithoutTokenShouldReturn401() throws Exception {
        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl + "/api/cart",
                jsonEntity(Map.of("email", TEST_EMAIL, "extensionId", extensionId, "language", "ES", "platform", "PC")),
                String.class
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void extensionsGetWithoutTokenShouldReturn200() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/api/extensions", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JsonNode body = objectMapper.readTree(response.getBody());
        assertTrue(body.isArray());
        assertTrue(body.size() >= 1);
        assertEquals(extensionId, body.get(0).path("id").asInt());
    }

    @Test
    void extensionByIdGetWithoutTokenShouldReturn200() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/api/extensions/" + extensionId, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JsonNode body = objectMapper.readTree(response.getBody());
        assertEquals(extensionId, body.path("id").asInt());
        assertEquals("https://example.com/ext-dlc-test.png", body.path("image").asText());
    }

    @Test
    void extensionResponseShouldIncludeIsPublic() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/api/extensions/" + extensionId, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JsonNode body = objectMapper.readTree(response.getBody());
        assertTrue(body.has("isPublic"));
        assertEquals(true, body.path("isPublic").asBoolean());
    }

    @Test
    void updateExtensionWithImageShouldPersistImage() throws Exception {
        String token = obtainJwt();

        java.util.Map<String, Object> translation = new java.util.HashMap<>();
        translation.put("language", "es");
        translation.put("name", "DLC Updated");
        translation.put("aboutGame", "Updated");
        translation.put("platforms", "PC");
        translation.put("languages", "EN");
        translation.put("distributor", "Nodo");
        translation.put("category", "Accion");

        java.util.Map<String, Object> payload = new java.util.HashMap<>();
        payload.put("price", 14.99);
        payload.put("requiredAge", 16);
        payload.put("publicationDate", LocalDate.now().toString());
        payload.put("image", "https://example.com/ext-dlc-updated.png");
        payload.put("translations", java.util.List.of(translation));

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/api/extensions/" + extensionId,
                HttpMethod.PUT,
                authEntity(token, payload),
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JsonNode body = objectMapper.readTree(response.getBody());
        assertEquals("https://example.com/ext-dlc-updated.png", body.path("image").asText());
        assertEquals("https://example.com/ext-dlc-updated.png", extensionsRepository.findById(extensionId).orElseThrow().getImage());
    }

    @Test
    void deleteExtensionWithValidTokenShouldReturnSuccessMessage() throws Exception {
        String token = obtainJwt();

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/api/extensions/" + extensionId,
                HttpMethod.DELETE,
                authEntity(token, null),
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JsonNode body = objectMapper.readTree(response.getBody());
        assertEquals("Extension eliminada con exito", body.path("message").asText());
    }

    @Test
    void buysDirectWithoutTokenShouldReturn401() throws Exception {
        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl + "/api/buys/direct",
                jsonEntity(Map.of("email", TEST_EMAIL, "extensionId", extensionId, "paymentMethod", "CARD", "language", "ES", "platform", "PC")),
                String.class
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void cartEndpointWithMismatchedEmailShouldReturn403() throws Exception {
        String token = obtainJwt();

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/api/cart/clear/" + OTHER_EMAIL,
                HttpMethod.DELETE,
                authEntity(token, null),
                String.class
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void buysDirectWithMismatchedEmailShouldReturn403() throws Exception {
        String token = obtainJwt();

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/api/buys/direct",
                HttpMethod.POST,
                authEntity(token, Map.of("email", OTHER_EMAIL, "extensionId", extensionId, "paymentMethod", "CARD", "language", "ES", "platform", "PC")),
                String.class
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void buysDirectWithValidTokenShouldCreateSingleBuy() throws Exception {
        String token = obtainJwt();

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/api/buys/direct",
                HttpMethod.POST,
                authEntity(token, Map.of("email", TEST_EMAIL, "extensionId", extensionId, "paymentMethod", "CARD", "language", "ES", "platform", "PC")),
                String.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JsonNode body = objectMapper.readTree(response.getBody());
        assertNotNull(body.path("buy").get("id"));
        assertEquals(TEST_EMAIL, body.path("buy").path("user").path("email").asText());
        assertEquals(extensionId, body.path("buy").path("extension").path("id").asInt());
        assertEquals("ES", body.path("buy").path("language").asText());
        assertEquals("PC", body.path("buy").path("platform").asText());
        assertEquals("9.99", body.path("totalPrice").asText());

        assertEquals(1, buysRepository.findByUserEmail(TEST_EMAIL).size());
    }

    @Test
    void buysDirectBetaOnlyExtensionWithNonBetaUserShouldReturn403() throws Exception {
        String token = obtainJwt();

        Integer betaExtensionId = createExtension("https://example.com/ext-beta-test.png", "DLC Beta", false);

        HttpClientErrorException.Forbidden forbidden = assertThrows(
                HttpClientErrorException.Forbidden.class,
                () -> restTemplate.exchange(
                        baseUrl + "/api/buys/direct",
                        HttpMethod.POST,
                        authEntity(token, Map.of("email", TEST_EMAIL, "extensionId", betaExtensionId, "paymentMethod", "CARD", "language", "ES", "platform", "PC")),
                        String.class
                )
        );
        assertEquals(HttpStatus.FORBIDDEN, forbidden.getStatusCode());
        assertEquals(0, buysRepository.findByUserEmail(TEST_EMAIL).size());
    }

    @Test
    void buysDirectBetaOnlyExtensionWithBetaUserShouldSucceed() throws Exception {
        Users betaUser = new Users();
        betaUser.setEmail("beta@nodo.com");
        betaUser.setPassword(passwordEncoder.encode(TEST_PASSWORD));
        betaUser.setFullName("Beta Nodo");
        betaUser.setProvider(AuthProvider.FORM);
        betaUser.setBetaTester(true);
        usersRepository.save(betaUser);

        ResponseEntity<String> loginResponse = restTemplate.postForEntity(
                baseUrl + "/api/auth/login",
                jsonEntity(Map.of("email", "beta@nodo.com", "password", TEST_PASSWORD)),
                String.class
        );
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        JsonNode loginBody = objectMapper.readTree(loginResponse.getBody());
        String betaToken = loginBody.get("token").asText();

        Integer betaExtensionId = createExtension("https://example.com/ext-beta-user.png", "DLC Beta 2", false);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/api/buys/direct",
                HttpMethod.POST,
                authEntity(betaToken, Map.of("email", "beta@nodo.com", "extensionId", betaExtensionId, "paymentMethod", "CARD", "language", "ES", "platform", "PC")),
                String.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, buysRepository.findByUserEmail("beta@nodo.com").size());
    }

    @Test
    void addToCartBetaOnlyExtensionWithNonBetaUserShouldBeRejected() throws Exception {
        String token = obtainJwt();

        Integer betaExtensionId = createExtension("https://example.com/ext-beta-cart.png", "DLC Beta 3", false);

        HttpClientErrorException.BadRequest badRequest = assertThrows(
                HttpClientErrorException.BadRequest.class,
                () -> restTemplate.exchange(
                        baseUrl + "/api/cart",
                        HttpMethod.POST,
                        authEntity(token, Map.of(
                                "email", TEST_EMAIL,
                                "extensionId", betaExtensionId,
                                "language", "ES",
                                "platform", "PC"
                        )),
                        String.class
                )
        );
        assertEquals(HttpStatus.BAD_REQUEST, badRequest.getStatusCode());
        assertEquals(0, cartItemRepository.findByUserEmail(TEST_EMAIL).size());
    }

    @Test
    void buysByUserWithValidTokenShouldReturnOnlyOwnerBuys() throws Exception {
        String token = obtainJwt();

        addItemToCart(token, "ES", "PC");
        restTemplate.exchange(
                baseUrl + "/api/buys/checkout",
                HttpMethod.POST,
                authEntity(token, Map.of("userEmail", TEST_EMAIL, "paymentMethod", "CARD")),
                String.class
        );

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/api/buys/user/" + TEST_EMAIL,
                HttpMethod.GET,
                authEntity(token, null),
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JsonNode body = objectMapper.readTree(response.getBody());
        assertTrue(body.isArray());
        assertEquals(1, body.size());
        assertEquals(TEST_EMAIL, body.get(0).path("userEmail").asText());
        assertEquals("CARD", body.get(0).path("paymentMethod").asText());
    }

    @Test
    void buysByUserWithMismatchedEmailShouldReturn403() throws Exception {
        String token = obtainJwt();

        HttpClientErrorException.Forbidden forbidden = assertThrows(
                HttpClientErrorException.Forbidden.class,
                () -> restTemplate.exchange(
                        baseUrl + "/api/buys/user/" + OTHER_EMAIL,
                        HttpMethod.GET,
                        authEntity(token, null),
                        String.class
                )
        );
        assertEquals(HttpStatus.FORBIDDEN, forbidden.getStatusCode());
    }

    @Test
    void cartGetShouldReturnSummaryWithTotalPrice() throws Exception {
        String token = obtainJwt();

        addItemToCart(token, "ES", "PC");
        addItemToCart(token, "EN", "PC");

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/api/cart/" + TEST_EMAIL,
                HttpMethod.GET,
                authEntity(token, null),
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JsonNode body = objectMapper.readTree(response.getBody());
        assertEquals(2, body.path("itemsCount").asInt());
        assertEquals("19.98", body.path("totalPrice").asText());
        assertEquals(2, body.path("items").size());
    }

    @Test
    void checkoutShouldReturnSummaryAndClearCart() throws Exception {
        String token = obtainJwt();

        addItemToCart(token, "ES", "PC");
        addItemToCart(token, "EN", "PC");

        ResponseEntity<String> checkoutResponse = restTemplate.exchange(
                baseUrl + "/api/buys/checkout",
                HttpMethod.POST,
                authEntity(token, Map.of("userEmail", TEST_EMAIL, "paymentMethod", "CARD")),
                String.class
        );

        assertEquals(HttpStatus.OK, checkoutResponse.getStatusCode());
        JsonNode checkoutBody = objectMapper.readTree(checkoutResponse.getBody());
        assertEquals(2, checkoutBody.path("itemsCount").asInt());
        assertEquals("19.98", checkoutBody.path("totalPrice").asText());
        assertEquals(2, checkoutBody.path("buys").size());
        assertEquals("Compra realizada con exito y carrito vaciado.", checkoutBody.path("message").asText());
        assertEquals(2, buysRepository.findByUserEmail(TEST_EMAIL).size());

        ResponseEntity<String> cartResponse = restTemplate.exchange(
                baseUrl + "/api/cart/" + TEST_EMAIL,
                HttpMethod.GET,
                authEntity(token, null),
                String.class
        );

        assertEquals(HttpStatus.OK, cartResponse.getStatusCode());
        JsonNode cartBody = objectMapper.readTree(cartResponse.getBody());
        assertEquals(0, cartBody.path("itemsCount").asInt());
        assertEquals("0", cartBody.path("totalPrice").asText());
        assertEquals(0, cartBody.path("items").size());
    }

    private String obtainJwt() throws Exception {
        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl + "/api/auth/login",
                jsonEntity(Map.of("email", TEST_EMAIL, "password", TEST_PASSWORD)),
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JsonNode node = objectMapper.readTree(response.getBody());
        return node.get("token").asText();
    }

    private HttpEntity<String> jsonEntity(Map<String, Object> payload) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(objectMapper.writeValueAsString(payload), headers);
    }

    private HttpEntity<String> authEntity(String token, Map<String, Object> payload) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        String body = payload == null ? null : objectMapper.writeValueAsString(payload);
        return new HttpEntity<>(body, headers);
    }

    private void addItemToCart(String token, String language, String platform) throws Exception {
        ResponseEntity<String> addResponse = restTemplate.exchange(
                baseUrl + "/api/cart",
                HttpMethod.POST,
                authEntity(token, Map.of(
                        "email", TEST_EMAIL,
                        "extensionId", extensionId,
                        "language", language,
                        "platform", platform
                )),
                String.class
        );
        assertEquals(HttpStatus.OK, addResponse.getStatusCode());
    }

    private Integer createExtension(String image, String name, boolean isPublic) {
        Extensions ext = new Extensions();
        ext.setPrice(BigDecimal.valueOf(14.99));
        ext.setRequiredAge(13);
        ext.setPublicationDate(LocalDate.now());
        ext.setImage(image);
        ext.setPublic(isPublic);
        ExtensionTranslation trEs = new ExtensionTranslation();
        trEs.setLanguage("es");
        trEs.setName(name);
        trEs.setAboutGame("Beta test content");
        trEs.setPlatforms("PC");
        trEs.setLanguages("ES");
        trEs.setDistributor("Nodo");
        trEs.setCategory("Accion");
        trEs.setExtension(ext);
        ext.getTranslations().add(trEs);
        return extensionsRepository.save(ext).getId();
    }
}

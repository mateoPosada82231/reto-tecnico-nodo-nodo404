package com.nodo.retotecnico.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nodo.retotecnico.models.AuthProvider;
import com.nodo.retotecnico.models.Extensions;
import com.nodo.retotecnico.models.Users;
import com.nodo.retotecnico.repositories.BuysRepository;
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
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=false",
        "spring.security.oauth2.client.registration.google.client-id=test",
        "spring.security.oauth2.client.registration.google.client-secret=test"
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
        extension.setName("DLC Test");
        extension.setPrice(BigDecimal.valueOf(9.99));
        extension.setRequiredAge(13);
        extension.setAboutGame("Test");
        extension.setPlatforms("PC");
        extension.setLanguages("ES");
        extension.setDistributor("Nodo");
        extension.setPublicationDate(LocalDate.now());
        extension.setCategory("Accion");
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
                baseUrl + "/api/users",
                HttpMethod.GET,
                authEntity(token, null),
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
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
        assertNotNull(body.get("id"));
        assertEquals(TEST_EMAIL, body.path("user").path("email").asText());
        assertEquals(extensionId, body.path("extension").path("id").asInt());
        assertEquals("ES", body.path("language").asText());
        assertEquals("PC", body.path("platform").asText());

        assertEquals(1, buysRepository.findByUserEmail(TEST_EMAIL).size());
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
}

package com.nodo.retotecnico.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionUtilsTest {

    private EncryptionUtils encryptionUtils;

    private static final String TEST_KEY = "R4VhZzxNzz9gTs3CJ23LH0ZpCvCm74EScFsvgvtMOss=";

    @BeforeEach
    void setUp() {
        encryptionUtils = new EncryptionUtils();
        ReflectionTestUtils.setField(encryptionUtils, "encryptionKeyBase64", TEST_KEY);
    }

    @Test
    void encryptShouldReturnNonNullOriginalPlaintext() {
        String plaintext = "sensitive-data@example.com";
        String encrypted = encryptionUtils.encrypt(plaintext);

        assertNotNull(encrypted);
        assertNotEquals(plaintext, encrypted);
    }

    @Test
    void decryptShouldReturnOriginalPlaintext() {
        String plaintext = "sensitive-data@example.com";
        String encrypted = encryptionUtils.encrypt(plaintext);
        String decrypted = encryptionUtils.decrypt(encrypted);

        assertEquals(plaintext, decrypted);
    }

    @Test
    void encryptDecryptEmptyString() {
        String plaintext = "";
        String encrypted = encryptionUtils.encrypt(plaintext);
        String decrypted = encryptionUtils.decrypt(encrypted);

        assertEquals(plaintext, decrypted);
    }

    @Test
    void encryptDecryptUnicode() {
        String plaintext = "Datos sensibles: ñ, á, é, í, ó, ü, 中文";
        String encrypted = encryptionUtils.encrypt(plaintext);
        String decrypted = encryptionUtils.decrypt(encrypted);

        assertEquals(plaintext, decrypted);
    }

    @Test
    void encryptShouldProduceDifferentCiphertextEachTime() {
        String plaintext = "same-input";
        String encrypted1 = encryptionUtils.encrypt(plaintext);
        String encrypted2 = encryptionUtils.encrypt(plaintext);

        assertNotEquals(encrypted1, encrypted2, "IV should produce different ciphertexts");
    }

    @Test
    void encryptNullShouldReturnNull() {
        assertNull(encryptionUtils.encrypt(null));
    }

    @Test
    void decryptNullShouldReturnNull() {
        assertNull(encryptionUtils.decrypt(null));
    }

    @Test
    void decryptInvalidBase64ShouldThrow() {
        assertThrows(RuntimeException.class, () -> encryptionUtils.decrypt("not-valid-base64!!!"));
    }

    @Test
    void decryptTamperedCiphertextShouldThrow() {
        String plaintext = "data";
        String encrypted = encryptionUtils.encrypt(plaintext);
        String tampered = encrypted.substring(0, encrypted.length() - 4) + "XXXX";

        assertThrows(RuntimeException.class, () -> encryptionUtils.decrypt(tampered));
    }

    @Test
    void deriveKeyShouldReturnSameKey() {
        var key1 = encryptionUtils.deriveKey(TEST_KEY);
        var key2 = encryptionUtils.deriveKey(TEST_KEY);

        assertArrayEquals(key1.getEncoded(), key2.getEncoded());
    }

    @Test
    void validatePayloadSizeShouldThrowOnLargePayload() {
        assertDoesNotThrow(() -> encryptionUtils.validatePayloadSize(1024));
        assertThrows(RuntimeException.class, () -> encryptionUtils.validatePayloadSize(2 * 1024 * 1024));
    }

    @Test
    void encryptDecryptLongString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("Line ").append(i).append(": This is a test string for encryption. ");
        }
        String plaintext = sb.toString();
        String encrypted = encryptionUtils.encrypt(plaintext);
        String decrypted = encryptionUtils.decrypt(encrypted);

        assertEquals(plaintext, decrypted);
    }
}

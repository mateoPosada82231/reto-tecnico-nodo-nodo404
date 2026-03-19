package com.nodo.retotecnico.security.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utilidad mínima para serializar respuestas de error sin duplicar lógica.
 */
final class JsonErrorWriter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonErrorWriter() {
    }

    static String toJson(Object body) {
        try {
            return OBJECT_MAPPER.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            // Fallback simple si la serialización falla
            return "{\"message\":\"Unexpected error\"}";
        }
    }
}

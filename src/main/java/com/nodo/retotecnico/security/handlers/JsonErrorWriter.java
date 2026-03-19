package com.nodo.retotecnico.security.handlers;

import java.util.Map;

/**
 * Utilidad mínima para serializar respuestas de error sin duplicar lógica.
 */
public final class JsonErrorWriter {

    private JsonErrorWriter() {
    }

    public static String toJson(Object body) {
        if (body instanceof Map<?, ?> map) {
            StringBuilder json = new StringBuilder("{");
            boolean first = true;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (!first) {
                    json.append(',');
                }
                first = false;
                json.append('"').append(escape(String.valueOf(entry.getKey()))).append('"').append(':');
                appendValue(json, entry.getValue());
            }
            json.append('}');
            return json.toString();
        }

        return "{\"message\":\"" + escape(String.valueOf(body)) + "\"}";
    }

    private static void appendValue(StringBuilder json, Object value) {
        if (value == null) {
            json.append("null");
        } else if (value instanceof Number || value instanceof Boolean) {
            json.append(value);
        } else {
            json.append('"').append(escape(String.valueOf(value))).append('"');
        }
    }

    private static String escape(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}

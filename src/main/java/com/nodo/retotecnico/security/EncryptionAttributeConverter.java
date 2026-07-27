package com.nodo.retotecnico.security;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Converter
public class EncryptionAttributeConverter implements AttributeConverter<String, String> {

    private static EncryptionUtils encryptionUtils;

    @Autowired
    public EncryptionAttributeConverter(EncryptionUtils encryptionUtils) {
        EncryptionAttributeConverter.encryptionUtils = encryptionUtils;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }
        return encryptionUtils.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return encryptionUtils.decrypt(dbData);
    }
}

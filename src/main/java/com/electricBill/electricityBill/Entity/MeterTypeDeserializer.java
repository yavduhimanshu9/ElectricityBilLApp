package com.electricBill.electricityBill.Entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * This class is a custom deserializer for the MeterType enum.
 * It extends the JsonDeserializer class and overrides the deserialize method.
 * The deserialize method takes a JsonParser and DeserializationContext as input, and returns a MeterType.
 * It reads the value from the JsonParser, and checks if it matches any of the MeterType values (ignoring case).
 * If a match is found, it returns the corresponding MeterType.
 * If no match is found, it throws an IllegalArgumentException.
 */
public class MeterTypeDeserializer extends JsonDeserializer<MeterType> {

    /**
     * This method is used to deserialize a JSON value into a MeterType.
     * It takes a JsonParser and DeserializationContext as input.
     * It reads the value from the JsonParser, and checks if it matches any of the MeterType values (ignoring case).
     * If a match is found, it returns the corresponding MeterType.
     * If no match is found, it throws an IllegalArgumentException.
     *
     * @param jsonParser This is used to read the JSON value.
     * @param deserializationContext This provides context for the deserialization process.
     * @return MeterType This returns the corresponding MeterType if a match is found.
     * @exception IOException On input error.
     * @exception IllegalArgumentException If no match is found for the MeterType.
     * @see IOException
     * @see IllegalArgumentException
     */
    @Override
    public MeterType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, IOException {
        String value = jsonParser.getText();
        for (MeterType meterType : MeterType.values()) {
            if (meterType.name().equalsIgnoreCase(value)) {
                return meterType;
            }
        }
        throw new IllegalArgumentException("Invalid value for MeterType: " + value);
    }
}
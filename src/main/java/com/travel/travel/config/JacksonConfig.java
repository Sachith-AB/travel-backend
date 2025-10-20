package com.travel.travel.config;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Time.class, new SqlTimeDeserializer());
        module.addSerializer(Time.class, new SqlTimeSerializer());
        
        // Add JavaTimeModule for LocalDateTime, LocalDate, LocalTime support
        builder.modules(module, new JavaTimeModule());
        
        return builder;
    }

    // Custom deserializer for java.sql.Time
    public static class SqlTimeDeserializer extends JsonDeserializer<Time> {
        private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        @Override
        public Time deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String timeString = jsonParser.getText();
            
            // Handle null or empty string
            if (timeString == null || timeString.trim().isEmpty()) {
                return null;
            }
            
            try {
                // Ensure format is HH:mm:ss
                if (timeString.split(":").length == 2) {
                    timeString += ":00";
                }
                
                java.util.Date parsedDate = timeFormat.parse(timeString);
                return new Time(parsedDate.getTime());
            } catch (ParseException e) {
                throw new IOException("Failed to parse time: " + timeString, e);
            }
        }
    }

    // Custom serializer for java.sql.Time
    public static class SqlTimeSerializer extends JsonSerializer<Time> {
        private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        @Override
        public void serialize(Time time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (time == null) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeString(timeFormat.format(time));
            }
        }
    }
}

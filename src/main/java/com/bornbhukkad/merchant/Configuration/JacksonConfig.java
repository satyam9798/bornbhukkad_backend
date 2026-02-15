package com.bornbhukkad.merchant.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {

	@Bean
	public ObjectMapper objectMapper() {
	    ObjectMapper mapper = new ObjectMapper();

	    // ✅ Support Java 8 Date/Time (LocalDateTime, etc.)
	    mapper.registerModule(new JavaTimeModule());

	    // ✅ ISO-8601 dates instead of timestamps
	    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	    // ✅ Ignore unknown / malformed fields like "tags "
	    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

	    return mapper;
	}

}


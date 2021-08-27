package com.revature.beans;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MyLocalTimeSerializer extends JsonSerializer<LocalTime> {

	@Override
	public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		// TODO Auto-generated method stub
		gen.writeString(value.format(DateTimeFormatter.ISO_LOCAL_TIME));
	}

}

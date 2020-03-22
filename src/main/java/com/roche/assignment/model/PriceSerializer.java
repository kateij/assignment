package com.roche.assignment.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;

public class PriceSerializer extends JsonSerializer<BigDecimal> {

  @Override
  public void serialize(final BigDecimal value, final JsonGenerator jsonGenerator,
      final SerializerProvider provider)
      throws IOException {
    jsonGenerator.writeString(value.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
  }
}

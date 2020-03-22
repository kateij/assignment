package com.roche.assignment.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;

public class PriceSerializer extends JsonSerializer<BigDecimal> {

  /**
   * Serializer for handling BigDecimal values. This is used by the price value.
   * It ensures that a zero decimal value is not removed when the value is
   * serialized. FOr example a value of 12.00 would be serialized as 12. This
   * method will serialise the value as 12.00.
   *
   * @param value the BigDecimal value to be serialized
   * @param jsonGenerator the JsonGenerator
   * @param provider the SerializerProvider
   * @throws IOException
   */
  @Override
  public void serialize(final BigDecimal value, final JsonGenerator jsonGenerator,
      final SerializerProvider provider)
      throws IOException {
    jsonGenerator.writeString(value.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
  }
}

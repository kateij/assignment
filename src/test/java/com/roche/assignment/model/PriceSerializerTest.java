package com.roche.assignment.model;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roche.assignment.builder.ProductBuilder;
import java.math.BigDecimal;
import org.junit.Test;

public class PriceSerializerTest {

  @Test
  public void whenPriceHasZeroDecimal_thenZeroDecimalIsGiven() throws JsonProcessingException {
    Product product = new ProductBuilder()
        .withPrice(new BigDecimal("1.00"))
        .build();
    final String json = new ObjectMapper().writeValueAsString(product);
    final String expectedPrice = "\"price\":\"1.00\"";

    assertThat(json, containsString(expectedPrice));
  }

  @Test
  public void whenPriceHasZeroDecimal_thenNonZeroDecimalIsGiven() throws JsonProcessingException {
    Product product = new ProductBuilder()
        .withPrice(new BigDecimal("1.01"))
        .build();
    final String json = new ObjectMapper().writeValueAsString(product);
    final String expectedPrice = "\"price\":\"1.01\"";

    assertThat(json, containsString(expectedPrice));
  }
}
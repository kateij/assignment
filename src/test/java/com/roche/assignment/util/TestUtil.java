package com.roche.assignment.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import com.roche.assignment.model.Product;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;

@JsonSerialize(include=Inclusion.NON_NULL)
public final class TestUtil {

  private TestUtil() {

  }

  public static final MediaType APPLICATION_JSON_UTF8 =
      new MediaType(MediaType.APPLICATION_JSON.getType(),
          MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

  public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsBytes(object);
  }

  public static String convertObjectToJsonString(Object object) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }

  public static Product convertToJsonStringToProductObject(String json) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(json, Product.class);
  }
}

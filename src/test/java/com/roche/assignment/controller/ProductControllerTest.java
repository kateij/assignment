package com.roche.assignment.controller;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.hamcrest.CoreMatchers.is;

import com.roche.assignment.util.TestUtil;
import com.roche.assignment.builder.ProductBuilder;
import com.roche.assignment.model.Product;
import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerTest {

  @Autowired
  private ProductController productController;
  @Autowired
  private MockMvc mockMvc;

  @Rollback(true)
  @Test
  public void whenGetRequestToProductsEndPoint_andNoProductsExist_thenValidANdNoProducts() throws Exception {
    final int expectedProducts = 0;

    mockMvc.perform(get("/products")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(expectedProducts)));
  }

  @Rollback(true)
  @Test
  public void whenGetRequestToProductsEndPoint_andProductsExist_thenALlProductsGiven() throws Exception {
    final int expectedProducts = 1;
    final Product product = new ProductBuilder("milk", new BigDecimal("1.50"), true).build();
    Product createdProduct = createProduct(product);

    mockMvc.perform(get("/products")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(expectedProducts)));
  }

  @Rollback(true)
  @Test
  public void whenPostRequestToProductsEndPoint_thenCreateProduct() throws Exception {
    final Product product = new ProductBuilder("milk", new BigDecimal("1.50"), true).build();

    Product createdProduct = createProduct(product);

    assertThat(createdProduct.getName(), is("milk"));
    assertThat(createdProduct.getPrice(), is(new BigDecimal("1.50")));
    assertThat(createdProduct.isActive() ,is(true));
  }

  @Rollback(true)
  @Test
  public void whenPostRequestToProductsEndPoint_andInvalidPrice_thenBadRequest() throws Exception {
    final String jsonRequest = "{\"createdDate\":null,\"sku/\":null,\"name\":\"milk\","
        + "\"price\":\"aa.50\",\"active\":true}";
    final String expectedErrorMessage = "Malformed JSON request";

    mockMvc.perform(post("/products")
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(jsonRequest))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", containsString(expectedErrorMessage)));
  }

  @Rollback(true)
  @Test
  public void whenPutRequestToProductsEndPoint_thenUpdateProduct() throws Exception {
    final Product product = new ProductBuilder("milk", new BigDecimal("1.50"), true).build();
    Product createdProduct = createProduct(product);

    createdProduct.setPrice(new BigDecimal("2.50"));

    mockMvc.perform(put("/products/" + createdProduct.getSku())
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(createdProduct)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("milk")))
        .andExpect(jsonPath("$.price", is("2.50")))
        .andExpect(jsonPath("$.active", is(true)));
  }

  @Rollback(true)
  @Test
  public void whenPutRequest_andProductDoesNotExist_thenThrowsProductNotFoundException() throws Exception {
    final Product product = new ProductBuilder().build();
    final String expectedErrorMessage = "Could not find product with a sku: 1";

    mockMvc.perform(put("/products/1")
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(product)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$", containsString(expectedErrorMessage)));
  }

  @Rollback(true)
  @Test
  public void whenDeleteRequestToProductsEndPoint_thenSoftDeleteProduct() throws Exception {
    final Product product = new ProductBuilder("milk", new BigDecimal("1.50"), true).build();
    Product createdProduct = createProduct(product);

    mockMvc.perform(delete("/products/" + createdProduct.getSku())
        .contentType(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("milk")))
        .andExpect(jsonPath("$.price", is("1.50")))
        .andExpect(jsonPath("$.active", is(false)));
  }

  @Rollback(true)
  @Test
  public void whenDeleteRequest_andProductDoesNotExist_thenThrowsProductNotFoundException() throws Exception {
    final String expectedErrorMessage = "Could not find product with a sku: 1";

    mockMvc.perform(delete("/products/1")
        .contentType(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$", containsString(expectedErrorMessage)));
  }

  private Product createProduct(Product product) throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/products")
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(product)))
        .andExpect(status().isOk())
        .andReturn();

    MockHttpServletResponse response = mvcResult.getResponse();
    return TestUtil.convertToJsonStringToProductObject(response.getContentAsString());
  }
}
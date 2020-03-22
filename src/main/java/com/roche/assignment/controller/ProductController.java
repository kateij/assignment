package com.roche.assignment.controller;

import com.roche.assignment.exception.ProductNotFoundException;
import com.roche.assignment.model.Product;
import com.roche.assignment.repository.ProductRepository;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

  private final ProductRepository productRepository;

  public ProductController(final ProductRepository aProductRepository) {
    this.productRepository = aProductRepository;
  }

  @GetMapping("/products")
  public Iterable<Product> findAllUsers() {
    return productRepository.findAll();
  }

  @PostMapping("/products")
  public Product createProduct(@RequestBody final Product product) {
    return productRepository.save(product);
  }

  @PutMapping("/products/{sku}")
  public ResponseEntity<Product> updateProduct(
      @PathVariable(value = "sku") @Positive final Long sku,
      @Valid @RequestBody final Product updatedProduct) throws ProductNotFoundException {

    Product product = findProduct(sku);

    modifyProduct(product, updatedProduct);

    return ResponseEntity.ok(updateProduct(product));
  }

  @DeleteMapping("/products/{sku}")
  public ResponseEntity<Product> deleteProduct(
      @PathVariable(value = "sku") @Positive final Long sku) throws ProductNotFoundException {

    final Product product = findProduct(sku);

    product.setActive(false);

    return ResponseEntity.ok(updateProduct(product));
  }

  private Product findProduct(final Long sku) {
    return productRepository.findById(sku)
        .orElseThrow(() -> new ProductNotFoundException(sku));
  }

  private void modifyProduct(final Product product, final Product updatedProduct) {
    if (updatedProduct.getName() != null) {
      product.setName(updatedProduct.getName());
    }
    if (updatedProduct.getPrice() != null) {
      product.setPrice(updatedProduct.getPrice());
    }
    if (updatedProduct.isActive() != null) {
      product.setActive(updatedProduct.isActive());
    }
  }

  private Product updateProduct(final Product product) {
    return productRepository.save(product);
  }
}

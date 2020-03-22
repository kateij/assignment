package com.roche.assignment.exception;

public class ProductNotFoundException extends RuntimeException {

  public ProductNotFoundException(final Long sku) {
    super("Could not find product with a sku: " + sku);
  }
}

package com.roche.assignment.builder;

import com.roche.assignment.model.Product;
import java.math.BigDecimal;

public class ProductBuilder {

  private String name;

  private BigDecimal price;

  private Boolean active;

  public ProductBuilder() {

  }

  public ProductBuilder(final String aName, final BigDecimal aPrice, final Boolean aActiveStatus) {
    this.name = aName;
    this.price = aPrice;
    this.active = aActiveStatus;
  }

  public ProductBuilder withName(final String aName) {
    this.name = aName;
    return this;
  }

  public ProductBuilder withPrice(final BigDecimal aPrice) {
    this.price = aPrice;
    return this;
  }

  public ProductBuilder wihActive(final Boolean aActiveStatus) {
    this.active = aActiveStatus;
    return this;
  }
  public Product build() {
    return new Product(name, price, active);
  }
}

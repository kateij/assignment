package com.roche.assignment.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.roche.assignment.auditor.AuditorEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product extends AuditorEntity<String> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long sku;

  private String name;

  @JsonSerialize(using = PriceSerializer.class)
  private BigDecimal price;

  private Boolean active;

  public Product() {

  }

  public Product(final String aName, final BigDecimal aPrice, final Boolean aActiveStatus) {
    this.name = aName;
    this.price = aPrice;
    this.active = aActiveStatus;
  }

  public Long getSku() {
    return sku;
  }

  public String getName() {
    return name;
  }

  public void setName(final String aName) {
    this.name = aName;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(final BigDecimal aPrice) {
    this.price = aPrice;
  }

  public Boolean isActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product entity = (Product) o;
    return Objects.equals(sku, entity.sku)
        && Objects.equals(name, entity.name)
        && Objects.equals(price, entity.price)
        && Objects.equals(active, entity.active);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sku, name, price, active);
  }
}

package com.retailShop.Entity;

import com.retailShop.Page.Module.Forms.EntityType;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Component implements EntityType {
    private int id;
    private String den;
    private Integer price;
    private Double sale;
    private String maker;
    private Integer warranty;
    private Integer stock;
    private Integer specs;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "den", nullable = true, length = 50)
    public String getDen() {
        return den;
    }

    public void setDen(String den) {
        this.den = den;
    }

    @Basic
    @Column(name = "price", nullable = true)
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Basic
    @Column(name = "sale", nullable = true, precision = 0)
    public Double getSale() {
        return sale;
    }

    public void setSale(Double sale) {
        this.sale = sale;
    }

    @Basic
    @Column(name = "maker", nullable = true, length = 50)
    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    @Basic
    @Column(name = "warranty", nullable = true)
    public Integer getWarranty() {
        return warranty;
    }

    public void setWarranty(Integer warranty) {
        this.warranty = warranty;
    }

    @Basic
    @Column(name = "stock", nullable = true)
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Basic
    @Column(name = "specs", nullable = true)
    public Integer getSpecs() {
        return specs;
    }

    public void setSpecs(Integer specs) {
        this.specs = specs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return id == component.id && Objects.equals(den, component.den) && Objects.equals(price, component.price) && Objects.equals(sale, component.sale) && Objects.equals(maker, component.maker) && Objects.equals(warranty, component.warranty) && Objects.equals(stock, component.stock) && Objects.equals(specs, component.specs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, den, price, sale, maker, warranty, stock, specs);
    }
}

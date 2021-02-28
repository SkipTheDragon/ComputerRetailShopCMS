package com.retailShop.entity;

import com.retailShop.page.module.forms.EntityType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Component implements EntityType {
    private int id;
    @Size(min = 2,message = "You need to specify component's name")
    private String den;
    private Integer price;
    @NotNull(message = "You need to specify component's type")
    private ComponentType type;
    @Size(min = 2,message = "You need to specify component's maker")
    private String maker;
    private Integer warranty;
    @NotNull(message = "You need to specify component's stock, 0 if no stock is available")
    private Integer stock;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return id == component.id && Objects.equals(den, component.den) && Objects.equals(price, component.price) && Objects.equals(type, component.type) && Objects.equals(maker, component.maker) && Objects.equals(warranty, component.warranty) && Objects.equals(stock, component.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, den, price, type, maker, warranty, stock);
    }

    @ManyToOne()
    @JoinColumn(name = "type", referencedColumnName = "id")
    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return den;
    }
}

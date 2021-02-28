package com.retailShop.entity;

import com.retailShop.page.module.forms.EntityType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
public class ComponentType implements EntityType {
    private int id;
    @NotEmpty
    private String den;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentType that = (ComponentType) o;
        return id == that.id && Objects.equals(den, that.den);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, den);
    }

    @Override
    public String toString() {
        return den;
    }
}

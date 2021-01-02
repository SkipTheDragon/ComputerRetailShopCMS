package com.retailShop.Entity;

import com.retailShop.Page.Module.Forms.EntityType;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ComponentType implements EntityType {
    private int id;
    private String den;

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
}

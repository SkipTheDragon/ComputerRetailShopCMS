package com.retailShop.Entity;

import com.retailShop.Page.Module.Forms.EntityType;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ComponentSpecification implements EntityType {
    private int id;
    private String den;
    private String content;
    private Component componentByComponent;

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
    @Column(name = "content", nullable = true, length = 255)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentSpecification that = (ComponentSpecification) o;
        return id == that.id && Objects.equals(den, that.den) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, den, content);
    }

    @ManyToOne
    @JoinColumn(name = "component", referencedColumnName = "id")
    public Component getComponentByComponent() {
        return componentByComponent;
    }

    public void setComponentByComponent(Component componentByComponent) {
        this.componentByComponent = componentByComponent;
    }
}

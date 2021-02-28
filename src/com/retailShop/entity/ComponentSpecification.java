package com.retailShop.entity;

import com.retailShop.page.module.forms.EntityType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
public class ComponentSpecification implements EntityType {
    private int id;
    @NotEmpty
    private String den;
    @NotEmpty
    private String content;
    private Component componentByComponent;

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

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "component", referencedColumnName = "id")
    public Component getComponentByComponent() {
        return componentByComponent;
    }

    public void setComponentByComponent(Component componentByComponent) {
        this.componentByComponent = componentByComponent;
    }
}

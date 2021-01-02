package com.retailShop.Entity;

import com.retailShop.Page.Module.Forms.EntityType;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class PurchaseOrderItem implements EntityType {
    private int id;
    private Component componentByComponent;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseOrderItem that = (PurchaseOrderItem) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

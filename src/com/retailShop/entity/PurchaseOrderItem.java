package com.retailShop.entity;

import com.retailShop.page.module.forms.EntityType;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class PurchaseOrderItem implements EntityType {
    private int id;
    private Component componentByComponent;
    private PurchaseOrder order;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "component", referencedColumnName = "id")
    public Component getComponentByComponent() {
        return componentByComponent;
    }

    public void setComponentByComponent(Component componentByComponent) {
        this.componentByComponent = componentByComponent;
    }

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    public PurchaseOrder getOrder() {
        return order;
    }

    public void setOrder(PurchaseOrder order) {
        this.order = order;
    }
}

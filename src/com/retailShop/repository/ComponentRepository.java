package com.retailShop.repository;

import com.retailShop.entity.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComponentRepository extends Repository<Component> {
    public Component findBy(String attribute, Object value) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Component employees = (Component) session.createQuery("FROM Component WHERE " + attribute + "=" + value).getSingleResult();
            tx.commit();

            return employees;

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public ComponentType[] getTypes() {

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List components = session.createQuery("FROM ComponentType").list();
            tx.commit();

            ComponentType[] roles = new ComponentType[components.size()];
            for (int i = 0; i < components.size(); i++) {
                roles[i] = (ComponentType) components.get(i);
            }

            return roles;

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public void sell(ArrayList<Component> components, User user, User seller) {

        if (user.getId() == seller.getId())
            seller = user;

        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseOrder.setBuyer(user);
        purchaseOrder.setSeller(seller);
        purchaseOrder.setOrderDate(new Timestamp(new Date().getTime()));
        insert(purchaseOrder);

        for (Component component : components) {
            PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
            purchaseOrderItem.setComponentByComponent(component);
            purchaseOrderItem.setOrder(purchaseOrder);

            insert(purchaseOrderItem);
            component.setStock(component.getStock() - 1);
            insert(component);
        }
    }

    private void insert(Object object) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            session.saveOrUpdate(object);
            session.flush();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
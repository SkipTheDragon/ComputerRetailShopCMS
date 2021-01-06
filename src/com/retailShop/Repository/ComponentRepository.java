package com.retailShop.Repository;

import com.retailShop.Entity.Component;
import com.retailShop.Entity.ComponentType;
import com.retailShop.Entity.User;
import com.retailShop.Entity.UserRole;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ComponentRepository extends Repository<Component> {
    public Component findBy(String attribute, String value) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Component employees = (Component) session.createQuery("FROM Component WHERE " + attribute + "=" + value).getSingleResult();
            tx.commit();

            return employees;

        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
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
            List components =  session.createQuery("FROM ComponentType").list();
            tx.commit();

            ComponentType[] roles = new ComponentType[components.size()];
            for (int i = 0; i < components.size(); i++) {
                roles[i] = (ComponentType) components.get(i);
            }

            return roles;

        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

}

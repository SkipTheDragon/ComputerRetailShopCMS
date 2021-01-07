package com.retailShop.Repository;

import com.retailShop.Entity.Component;
import com.retailShop.Entity.ComponentSpecification;
import com.retailShop.Entity.ComponentType;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ComponentSpecRepository extends Repository<ComponentSpecification> {

    public ComponentSpecification findBy(String attribute, String value) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            ComponentSpecification employees = (ComponentSpecification) session.createQuery("FROM ComponentSpecification WHERE " + attribute + "=" + value).getSingleResult();
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

    public ComponentSpecification[] getAllSpecsWhereIDis(int id) {

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List components =  session.createQuery("FROM ComponentSpecification WHERE componentByComponent = " + id  ).list();
            tx.commit();

            ComponentSpecification[] roles = new ComponentSpecification[components.size()];
            for (int i = 0; i < components.size(); i++) {
                roles[i] = (ComponentSpecification) components.get(i);
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

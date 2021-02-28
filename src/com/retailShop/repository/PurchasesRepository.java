package com.retailShop.repository;

import com.retailShop.entity.PurchaseOrder;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class PurchasesRepository extends Repository<PurchaseOrder> {

    @Override
    public PurchaseOrder findBy(String attribute, Object value) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query employees = session.createQuery("FROM PurchaseOrder WHERE " + attribute + "= :val");
            employees.setParameter("val", value);

            tx.commit();

            return (PurchaseOrder) employees.getSingleResult();

        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public List soldInThisTimePeriod(Date previousDate, Date now) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Query employees = session.createQuery("FROM PurchaseOrder WHERE  orderDate BETWEEN :prev AND :now");
            employees.setParameter("prev", previousDate);
            employees.setParameter("now", now);
            tx.commit();

            return employees.getResultList();

        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}

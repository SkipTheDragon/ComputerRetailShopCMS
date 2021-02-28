package com.retailShop.repository;

import com.retailShop.entity.PurchaseOrder;
import com.retailShop.entity.PurchaseOrderItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class PurchasesItemRepository extends Repository<PurchaseOrderItem> {
    @Override
    public PurchaseOrderItem findBy(String attribute, Object value) {
        return null;
    }


    public List getAllItems(PurchaseOrder object) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query employees = session.createQuery("FROM PurchaseOrderItem WHERE order = :order");
            employees.setParameter("order", object);
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

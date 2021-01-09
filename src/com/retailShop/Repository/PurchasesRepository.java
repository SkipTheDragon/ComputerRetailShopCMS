package com.retailShop.Repository;

import com.retailShop.Entity.PurchaseOrder;
import com.retailShop.Entity.PurchaseOrderItem;
import com.retailShop.Entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PurchasesRepository extends Repository<PurchaseOrder> {

    @Override
    public PurchaseOrder findBy(String attribute, String value) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            PurchaseOrder employees = (PurchaseOrder) session.createQuery("FROM PurchaseOrder WHERE " + attribute + "=" + value).getSingleResult();
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

}

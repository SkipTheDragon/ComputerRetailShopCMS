package com.retailShop.Repository;

import com.retailShop.Entity.User;
import com.retailShop.Entity.UserRole;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class UserRepository extends Repository<User> {
    public User findBy(String attribute, String value) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            User employees = (User) session.createQuery("FROM User WHERE " + attribute + "=" + value).getSingleResult();
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
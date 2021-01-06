package com.retailShop.Repository;

import com.retailShop.Entity.User;
import com.retailShop.Entity.UserRole;
import com.retailShop.Page.Module.Forms.EntityType;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public abstract class Repository<T extends EntityType> {
    SessionFactory factory = new Configuration().configure().buildSessionFactory();

    public UserRole[] getRoles() {

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List employees =  session.createQuery("FROM UserRole").list();
            tx.commit();

            UserRole[] roles = new UserRole[employees.size()];
            for (int i = 0; i < employees.size(); i++) {
                roles[i] = (UserRole) employees.get(i);
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

    public List<T> getAllData(Class<T> clazz) {
        Session session = factory.openSession();

        return loadAllData(clazz, session);
    }

    public abstract T findBy(String attribute, String value);

    private static <T> List<T> loadAllData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        return session.createQuery(criteria).getResultList();
    }
}

package com.retailShop.page;

import com.retailShop.entity.Log;
import com.retailShop.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Logger {
    public static User loggedUser;
    public static String page;

    public static void log(String action) {
        Log log = new Log();
        log.setAction(action);
        log.setUser(loggedUser);
        log.setPage(page);
        insert(log);
    }

    private static void insert(Log log) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(log);
            session.flush();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

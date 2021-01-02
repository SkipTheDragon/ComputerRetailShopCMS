package com.retailShop.Page;

import com.retailShop.Entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import java.awt.*;

public class Login extends JPanel {
    private final JPasswordField passwordField = new JPasswordField("1234",5);
    private final JTextField usernameField = new JTextField("Manager",5);

    public Login(JFrame window) {
        add(new JLabel("Username"));
        add(usernameField);
        add(new JLabel("Password"));
        add(passwordField);
        JButton submit = new JButton("Submit");

        submit.addActionListener(e -> {
            User user  = getUserByNameAndPassword(usernameField.getText(), passwordField.getPassword());
            if (user != null) {
                Container contentPane =  window.getContentPane();
                contentPane.add(new Core(user,window));
                contentPane.remove(this);
                contentPane.revalidate();
                contentPane.repaint();
            }
        });

        add(submit);
        setSize(600,600);
    }

    private User getUserByNameAndPassword(String name, char[] password) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            return (User) session
                    .createQuery("FROM User WHERE name = :name AND password = :password")
                    .setParameter("name", name)
                    .setParameter("password",  password)
                    .getSingleResult();
        }

        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }
}

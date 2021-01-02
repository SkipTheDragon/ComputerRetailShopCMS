package com.retailShop.Page.Module.Forms;

import com.retailShop.Entity.UserContact;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Form<T extends EntityType>  {

    protected JPanel panel = new JPanel();
    protected T object = getNewInstanceOfObject();
    protected FormBuilder<T> formBuilder = new FormBuilder<>(object, this, panel);
    public FormEventManager formEventManager = new FormEventManager("formRefresh","exportTable");
    protected ArrayList<Form<?>> formArrayList = new ArrayList<>();

    /*
    Every time the object changes we refresh the form
     */
    public void setObject(T object) {
        this.object = object;
        rebuildForm();
    }

    public FormBuilder<T> getFormBuilder() {
        return formBuilder;
    }

    public void resetObject() {
         this.object = getNewInstanceOfObject();
         rebuildForm();
    }

    public void rebuildForm() {
        clearPanel();
        this.formBuilder = new FormBuilder<>(object, this, panel);
        createForm().buildForm();
        formEventManager.notify("formRefresh");

        for (Form<?> form : formArrayList) {
            form.rebuildForm();
        }
    }

    protected void addSubform(Form<?> subform) {
        formArrayList.add(subform);
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    /**
     * Shortcut for repainting the panel.
     */
    private void repaintPanel() {
        panel.repaint();
        panel.revalidate();
    }

    /**
     * Shortcut for clearing the panel.
     */
    private void clearPanel() {
        panel.removeAll();
        panel.repaint();
        panel.revalidate();
    }

    public JPanel getPanel() {
        return panel;
    }

    /**
     * The method in which we create the form.
     */
    public abstract FormBuilder<T> createForm();

    /**
     * The method we execute if the form is valid
     */
    public abstract void processForm();

    /**
     * We cannot instantiate an object's type so we create a new instance of the object this way
     * Used mostly for cleaning form's fields.
     * @return the new object instance
     */
    protected abstract T getNewInstanceOfObject();


    /**
     * Queries for an object by it's id
     * @param objectId object's id that we are querying for.
     * @return true if the object exists
     */
    protected boolean objectExistInDatabase(int objectId) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        try (Session session = factory.openSession()) {
            return session.get(object.getClass(), objectId) != null;
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return false;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Basic operations
    ///////////////////////////////////////////////////////////////////////////
    protected void updateOrInsert() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(object);
            session.flush();
            tx.commit();

        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    protected void delete(int objectId) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (session.get(object.getClass(), objectId) != null){
                session.delete(session.get(object.getClass(), objectId));
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        resetObject();
    }

    ///////////////////////////////////////////////////////////////////////////
    //  Shortcuts for adding very basic buttons
    ///////////////////////////////////////////////////////////////////////////

    protected void addClearButton() {
        formBuilder.addButton(new JButton("Clear"), e -> {
            resetObject();
            repaintPanel();
        });

    }

    protected void addSubmitButton() {
        formBuilder.addButton(new JButton("Submit"), e -> {
            formBuilder.submit();
        });

    }

    protected void addDeleteButton(int objectId, String text) {
        formBuilder.addButton(new JButton("Delete"), e -> {
            if (objectExistInDatabase(objectId)) {
                int response = JOptionPane.showConfirmDialog(null,
                        new JLabel(text),
                        "Delete", JOptionPane.YES_NO_OPTION);

                if (response == 0) {
                    delete(objectId);
                }
            }
        });

    }

    protected void addDeleteButton(int objectId, String text, String title) {
        formBuilder.addButton(new JButton("Delete"), e -> {
            if (objectExistInDatabase(objectId)) {
                int response = JOptionPane.showConfirmDialog(null,
                        new JLabel(text),
                        title, JOptionPane.DEFAULT_OPTION);

                if (response == 0) {
                    delete(objectId);
                }
            }
        });

    }

}

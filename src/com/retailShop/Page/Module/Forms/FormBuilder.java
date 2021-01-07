package com.retailShop.Page.Module.Forms;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.util.ArrayList;


public class FormBuilder<T extends EntityType> {
    /**
     * Contains form's fields.
     */
    public final ArrayList<FormField> formFields = new ArrayList<>();

    private final ArrayList<JButton> buttons = new ArrayList<>();

    private final ArrayList<FormBuilder<?>> forms = new ArrayList<>();

    /**
     * The object that we bind data to.
     */
    private final T object;
    private final Form<T> form;
    private JPanel panel;

    private String mapTo;
    private String name;
    private boolean isRendered = false;
    private boolean isSubForm = false;
    private Relation relation = Relation.ONE_TO_MANY;

    public enum Relation {
        ONE_TO_MANY,MANY_TO_ONE,MANY_TO_MANY
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public boolean isSubForm() {
        return isSubForm;
    }

    public void setSubForm(boolean subForm) {
        isSubForm = subForm;
    }

    /**
     * @param object The object that we bind data to.
     * @param form The class where we build the form and run the after submit instructions.
     */
    public FormBuilder(T object, Form<T> form, JPanel panel) {
        this.object = object;
        this.form = form;
        this.panel = panel;
    }

    public boolean isRendered() {
        return isRendered;
    }

    /*
     * The submit does the following:
     *  1. Binds user's input to object
     *  2. If the form is valid, passes the modified object and automatically processes the logic from Form.processForm() method,
     *  otherwise it displays form's errors.
     */

    public void submit() {
        if (relation == Relation.ONE_TO_MANY) {
            for (FormBuilder<?> subForm : forms) {
                subForm.submit();
            }
        }
        subFormsAsFields();
        // add input from each field
        bindInputToObject();

        if (isFormValid()) {
            form.setObject(this.object);
            if (relation == Relation.ONE_TO_MANY || !isSubForm) {
                form.processForm();
            }

            if (relation == Relation.MANY_TO_ONE) {
                for (FormBuilder<?> subForm : forms) {
                    subForm.submit();
                }
            }

            form.formEventManager.notify("formRefresh");
        } else {
            displayErrors();
        }

        panel.revalidate();
        panel.repaint();
    }


    /**
     * Handles rendering fields and buttons.
     */
    public void buildForm() {

        for (FormField formField : formFields) {
            formField.render(panel);
        }

        for (FormBuilder<?> subForm : forms) {
            subForm.buildForm();
        }

        renderButtons();

        isRendered = true;
    }

    /**
     * Renders buttons
     */
    private void renderButtons() {
        for (JButton button : buttons) {
            panel.add(button);
        }
    }

    /**
     * Adds a button to form, and binds an on press action to it
     */
    public FormBuilder<T> addButton(JButton button, ActionListener actionListener) {
        button.addActionListener(actionListener);
        buttons.add(button);
        return this;
    }

    public T getObject() {
        return object;
    }

    public JPanel getPanel() {
        return panel;
    }

    /**
     *
     */
    public FormBuilder<T> addSubForm(String mapTo, FormBuilder<?> formBuilder, String name) {
        forms.add(formBuilder);
        formBuilder.setMapTo(mapTo);
        formBuilder.setName(name);
        formBuilder.setSubForm(true);

        form.addSubform(formBuilder.getForm());

        return this;
    }

    private void subFormsAsFields() {
        for (FormBuilder<?> subForm : forms) {

            if (subForm.isSubForm) {
                FormField formField = new FormField(subForm.getMapTo(), subForm.getObject(), subForm.getName());
                formField.setHiddenField(true);
                addField(formField);
            }
        }
    }

    public Form<T> getForm() {
        return form;
    }

    public String getMapTo() {
        return mapTo;
    }

    public void setMapTo(String mapTo) {
        this.mapTo = mapTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Iterates over all fields and scans for any field errors if none the form is valid, otherwise is invalid.
     * @return validation status of form.
     */
    private boolean isFormValid() {
        if (areSubFormsValid()) {
            for (FormField formField : formFields) {
                if (formField.hasErrors()) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean areSubFormsValid() {
        if (!forms.isEmpty()) {
            for (FormBuilder<?> subForm : forms) {
                if (!subForm.isFormValid())
                    return false;
            }
        }
        return true;
    }


    /**
     * Adds a new Field object in ArrayList where all fields are stored.
     * @param formField FormField object.
     * @return FormBuilder for method chaining.
     */
    public FormBuilder<T> addField(FormField formField) {
        formField.setObject(object);
        formFields.add(formField);

        return this;
    }

    /**
     * Iterates of all fields running the run method on each.
     */
    private void bindInputToObject() {
        for (FormField formField : formFields) {
            try {
                formField.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Renders errors if any.
     */
    private void displayErrors() {
        for (FormField formField : formFields) {
            if (formField.hasErrors())  {
                for (JLabel errorsAsLabel : formField.getErrorsAsLabels()) {
                    panel.add(errorsAsLabel);
                }
            }
        }
        panel.revalidate();
        panel.repaint();
    }
}

package com.retailShop.page.module.base.components.specification;

import com.retailShop.entity.*;
import com.retailShop.page.module.forms.Form;
import com.retailShop.page.module.forms.FormBuilder;
import com.retailShop.page.module.forms.FormField;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ComponentsSpecForm extends Form<ComponentSpecification> {

    private Component component = null;

    public ComponentsSpecForm(JPanel panel, ComponentSpecification object, Component component) {
        super(panel, object);
        this.component = component;
    }

    public ComponentsSpecForm(JPanel panel, ComponentSpecification object) {
        super(panel, object);
    }

    @Override
    public FormBuilder<ComponentSpecification> createForm() {
        panel.setLayout(new MigLayout("wrap 2","[][]"));

        formBuilder.addField(new FormField("den", new JTextField(5) , "Den"))
                    .addField(new FormField("content", new JTextArea(10,10), "Content"));

        if (component != null) {
            formBuilder.addField(new FormField("componentByComponent", component, "Component")
                    .setHiddenField(true));
        } else {
            addSubmitButton();
            addClearButton();
            addDeleteButton(object.getId(),"Do you really want to delete:" + object.getDen());
            formBuilder.buildForm();
        }
        return formBuilder;
    }

    @Override
    public void processForm() {
        if (!objectExistInDatabase(object.getId()))
            insert();
        else
            update();    }

    @Override
    protected ComponentSpecification getNewInstanceOfObject() {
        return new ComponentSpecification();
    }
}

package com.retailShop.Page.Module.Base.Components.Specification;

import com.retailShop.Entity.*;
import com.retailShop.Page.Module.Forms.Form;
import com.retailShop.Page.Module.Forms.FormBuilder;
import com.retailShop.Page.Module.Forms.FormField;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;

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
        insert();
    }

    @Override
    protected ComponentSpecification getNewInstanceOfObject() {
        return new ComponentSpecification();
    }
}

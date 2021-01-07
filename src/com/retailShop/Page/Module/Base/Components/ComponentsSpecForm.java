package com.retailShop.Page.Module.Base.Components;

import com.retailShop.Entity.*;
import com.retailShop.Page.Module.Forms.Form;
import com.retailShop.Page.Module.Forms.FormBuilder;
import com.retailShop.Page.Module.Forms.FormField;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;

public class ComponentsSpecForm extends Form<ComponentSpecification> {

    private final Component component;

    public ComponentsSpecForm(JPanel panel, ComponentSpecification object, Component component) {
        super(panel, object);
        this.component = component;
    }

    @Override
    public FormBuilder<ComponentSpecification> createForm() {
        panel.setLayout(new MigLayout("wrap 2","[][]"));

        formBuilder.addField(new FormField("den", new JTextField(5) , "Den"))
                    .addField(new FormField("content", new JTextArea(10,10), "Content"))
                    .addField(new FormField("componentByComponent", component, "Component")
                            .setHiddenField(true));
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

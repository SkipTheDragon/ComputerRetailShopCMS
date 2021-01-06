package com.retailShop.Page.Module.Base.Components;

import com.retailShop.Entity.*;
import com.retailShop.Page.Module.Forms.Form;
import com.retailShop.Page.Module.Forms.FormBuilder;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;

public class ComponentsSpecForm extends Form<ComponentSpecification> {
    ArrayList<ComponentsSpecForm> toAdd = new ArrayList<>();

    @Override
    protected void addClearButton() {
        formBuilder.addButton(new JButton("Clear"), e -> {
            toAdd = new ArrayList<>();
            formBuilder.resetForm();
            repaintPanel();
        });
    }

    @Override
    public FormBuilder<ComponentSpecification> createForm() {
        panel.setLayout(new MigLayout("wrap 2","[][]"));

        ComponentsForm componentsForm = new ComponentsForm();

        for (ComponentsSpecForm formField : toAdd) {
            formBuilder.addSubForm("componentByComponent",formField.createForm(), "Component");
        }

        formBuilder.addSubForm("componentByComponent", componentsForm.createForm(), "Component");

        addSubmitButton();
        addClearButton();
        addDeleteButton(object.getId(),"Do you really want to delete:" + object.getDen());

        formBuilder.addButton(new JButton("Add new spec"), e -> {
            toAdd.add(this);
          //  rebuildForm();
        });


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

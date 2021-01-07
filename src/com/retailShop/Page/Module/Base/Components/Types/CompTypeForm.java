package com.retailShop.Page.Module.Base.Components.Types;


import com.retailShop.Entity.*;
import com.retailShop.Page.Module.Forms.Form;
import com.retailShop.Page.Module.Forms.FormBuilder;
import com.retailShop.Page.Module.Forms.FormField;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class CompTypeForm extends Form<ComponentType> {

    int specForms = 0;

    public CompTypeForm(JPanel panel, ComponentType object) {
        super(panel, object);
    }

    @Override
    protected void addClearButton() {
        formBuilder.addButton(new JButton("Clear"), e -> {
            specForms = 0;
            rebuildForm();
            repaintPanel();
        });
    }

    @Override
    public FormBuilder<ComponentType> createForm() {
        panel.setLayout(new MigLayout("wrap 2","[][]"));

        formBuilder.addField(new FormField("den", new JTextField(10), "Type Name"));

        addSubmitButton();
        addClearButton();
        addDeleteButton(object.getId(),"Do you really want to delete:" + object.getDen());

        formBuilder.addButton(new JButton("Export table"), e -> {
            formEventManager.notify("exportTable");
        });

        formBuilder.buildForm();
        return formBuilder;
    }

    @Override
    public void processForm() {
        if (!objectExistInDatabase(object.getId()))
            insert();
        else
            update();    }

    @Override
    protected ComponentType getNewInstanceOfObject() {
        return new ComponentType();
    }
}

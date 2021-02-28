package com.retailShop.page.module.base.components;

import com.retailShop.entity.*;
import com.retailShop.entity.Component;
import com.retailShop.page.module.base.components.specification.ComponentsSpecForm;
import com.retailShop.page.module.forms.Form;
import com.retailShop.page.module.forms.FormBuilder;
import com.retailShop.page.module.forms.FormField;
import com.retailShop.page.Permission;
import com.retailShop.repository.ComponentRepository;
import com.retailShop.repository.ComponentSpecRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ComponentsForm extends Form<Component> {
    int specForms = 0;
    public ComponentsForm(JPanel panel, Component object) {
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
    public FormBuilder<Component> createForm() {
        panel.setLayout(new MigLayout("wrap 2","[][]"));
        formEventManager.addEvent("sellComponent");
        formBuilder.setName("Create Component");
        ComponentRepository componentRepository= new ComponentRepository();
        JComboBox<ComponentType> types = new JComboBox<>(componentRepository.getTypes());
        formBuilder.setRelation(FormBuilder.Relation.MANY_TO_ONE);
        formBuilder
                .addField(new FormField("den", new JTextField(10), "Component Name"))
                .addField(new FormField("price", new JTextField(10), "Price").convertTo(Integer.class))
                .addField(new FormField("maker", new JTextField(10), "Maker"))
                .addField(new FormField("warranty", new JTextField(10), "Warranty").convertTo(Integer.class))
                .addField(new FormField("stock", new JTextField(10), "Stock").convertTo(Integer.class))
                .addField(new FormField("type", types, "Type"))
        ;

        if (objectExistInDatabase(object.getId())) {
            formBuilder.setName("Update Component");

            ComponentSpecRepository componentSpecRepository  = new ComponentSpecRepository();
            for (ComponentSpecification allSpec : componentSpecRepository.getAllSpecsWhereIDis(object.getId())) {
                FormBuilder<ComponentSpecification> formBuilderSpec = new ComponentsSpecForm(getPanel(), allSpec, object).createForm();
                formBuilder.addSubForm(null, formBuilderSpec, "Specification");
            }
        }

        for (int i = 0; i <= specForms; i++) {
            FormBuilder<ComponentSpecification> formBuilderSpec = new ComponentsSpecForm(getPanel(), new ComponentSpecification(), object).createForm();
            formBuilder.addSubForm(null, formBuilderSpec, "Specification");
        }

        addSubmitButton();
        addClearButton();
        if (Permission.userCan("delete", "Components"))
            addDeleteButton(object.getId(),"Do you really want to delete:" + object.getDen());

        formBuilder.addButton(new JButton("Add new spec"), e -> {
            specForms += 1;
            rebuildForm();
        });
        formBuilder.addButton(new JButton("Export table"), e -> {
            formEventManager.notify("exportTable");
        });
        if (Permission.userCan("sell", "Components"))
            formBuilder.addButton(new JButton("Sell"), e -> {
                formEventManager.notify("sellComponent");
            });

        formBuilder.buildForm();
        return formBuilder;
    }

    @Override
    public void processForm() {
        if (!objectExistInDatabase(object.getId()))
            insert();
        else
            update();
    }

    @Override
    protected Component getNewInstanceOfObject() {
        return new Component();
    }
}

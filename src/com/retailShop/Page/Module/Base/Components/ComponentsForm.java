package com.retailShop.Page.Module.Base.Components;

import com.retailShop.Entity.*;
import com.retailShop.Page.Module.Forms.Form;
import com.retailShop.Page.Module.Forms.FormBuilder;
import com.retailShop.Page.Module.Forms.FormField;
import com.retailShop.Page.Module.Manager.EmployeesAddContactForm;
import com.retailShop.Repository.ComponentRepository;
import com.retailShop.Repository.ComponentSpecRepository;
import com.retailShop.Repository.UserRepository;
import net.miginfocom.swing.MigLayout;
import org.hibernate.usertype.UserType;

import javax.swing.*;
import java.util.ArrayList;

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
        addDeleteButton(object.getId(),"Do you really want to delete:" + object.getDen());

        formBuilder.addButton(new JButton("Add new spec"), e -> {
            specForms += 1;
            rebuildForm();
        });
        formBuilder.addButton(new JButton("Export table"), e -> {
            formEventManager.notify("exportTable");
        });
        formBuilder.buildForm();
        return formBuilder;
    }

    @Override
    public void processForm() {
        insert();
    }

    @Override
    protected Component getNewInstanceOfObject() {
        return new Component();
    }
}

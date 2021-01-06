package com.retailShop.Page.Module.Base.Components;

import com.retailShop.Entity.Component;
import com.retailShop.Entity.ComponentType;
import com.retailShop.Entity.UserContact;
import com.retailShop.Entity.UserRole;
import com.retailShop.Page.Module.Forms.Form;
import com.retailShop.Page.Module.Forms.FormBuilder;
import com.retailShop.Page.Module.Forms.FormField;
import com.retailShop.Page.Module.Manager.EmployeesAddContactForm;
import com.retailShop.Repository.ComponentRepository;
import com.retailShop.Repository.UserRepository;
import net.miginfocom.swing.MigLayout;
import org.hibernate.usertype.UserType;

import javax.swing.*;

public class ComponentsForm extends Form<Component> {

    @Override
    public FormBuilder<Component> createForm() {
        panel.setLayout(new MigLayout("wrap 2","[][]"));

        ComponentRepository componentRepository= new ComponentRepository();
        JComboBox<ComponentType> types = new JComboBox<>(componentRepository.getTypes());

        formBuilder
                .addField(new FormField("den", new JTextField(10), "Component Name"))
                .addField(new FormField("price", new JTextField(10), "Price").convertTo(Float.class))
                .addField(new FormField("maker", new JTextField(10), "Maker"))
                .addField(new FormField("warranty", new JTextField(10), "Warranty").convertTo(Integer.class))
                .addField(new FormField("stock", new JTextField(10), "Stock").convertTo(Integer.class))
                .addField(new FormField("type", types, "Type"))
        ;


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

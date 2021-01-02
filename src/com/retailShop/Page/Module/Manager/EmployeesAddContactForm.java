package com.retailShop.Page.Module.Manager;

import com.retailShop.Entity.User;
import com.retailShop.Entity.UserContact;
import com.retailShop.Page.Module.Forms.Form;
import com.retailShop.Page.Module.Forms.FormBuilder;
import com.retailShop.Page.Module.Forms.FormField;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class EmployeesAddContactForm extends Form<UserContact> {

    @Override
    public FormBuilder<UserContact> createForm() {
        panel.setLayout(new MigLayout("wrap 2","[][]"));

        FormField phoneNo = new FormField("phoneNo", new JTextField(10), "Phone Number");
        phoneNo.convertTo(Integer.class);

        formBuilder
                .addField(phoneNo)
                .addField(new FormField("email", new JTextField(10), "Email"))
                .addField(new FormField("address", new JTextField(10), "Address"))
                .addField(new FormField("city", new JTextField(10), "City"));

        return formBuilder;
    }

    @Override
    public void processForm() {
        updateOrInsert();
    }

    @Override
    protected UserContact getNewInstanceOfObject() {
        return new UserContact();
    }
}

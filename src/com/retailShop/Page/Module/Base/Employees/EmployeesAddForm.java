package com.retailShop.Page.Module.Base.Employees;

import com.retailShop.Entity.User;
import com.retailShop.Entity.UserContact;
import com.retailShop.Entity.UserRole;
import com.retailShop.Page.Module.Forms.Form;
import com.retailShop.Page.Module.Forms.FormBuilder;
import com.retailShop.Page.Module.Forms.FormField;
import com.retailShop.Repository.UserRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class EmployeesAddForm extends Form<User> {

    public EmployeesAddForm(JPanel panel, User object) {
        super(panel, object);
    }

    @Override
    public FormBuilder<User> createForm() {
        panel.setLayout(new MigLayout("wrap 2","[][]"));

        UserRepository userRepository= new UserRepository();
        JComboBox<UserRole> roles = new JComboBox<>(userRepository.getRoles());
        UserContact userContact = new UserContact();

        if (object.getUserContactByContact() != null) {
            userContact = object.getUserContactByContact();
        }

        Form<UserContact> userContactForm= new EmployeesAddContactForm(getPanel(),userContact);

        formBuilder
                .addField(new FormField("name", new JTextField(10), "Name"))
                .addField(new FormField("lastName", new JTextField(10), "Last Name"))
                .addField(new FormField("password", new JPasswordField(10), "Password"))
                .addField(new FormField("userRoleByRoleid", roles, "User Role"))
                .addSubForm("userContactByContact", userContactForm.createForm(), "Contact")

        ;

        addSubmitButton();
        addClearButton();
        addDeleteButton(object.getId(), "Do you really wanna delete employee named: " + object.getName());
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
            update();
    }

    @Override
    protected User getNewInstanceOfObject() {
        return new User();
    }
}

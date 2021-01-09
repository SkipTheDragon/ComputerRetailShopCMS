package com.retailShop.Page.Module.Base.Purchases;

import com.retailShop.Entity.PurchaseOrder;
import com.retailShop.Entity.User;
import com.retailShop.Entity.UserContact;
import com.retailShop.Entity.UserRole;
import com.retailShop.Page.Module.Base.Employees.EmployeesAddContactForm;
import com.retailShop.Page.Module.Forms.Form;
import com.retailShop.Page.Module.Forms.FormBuilder;
import com.retailShop.Page.Module.Forms.FormField;
import com.retailShop.Repository.UserRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.sql.Timestamp;
import java.util.Date;

public class PurchasesForm extends Form<PurchaseOrder> {

    public PurchasesForm(JPanel panel, PurchaseOrder object) {
        super(panel, object);
    }

    @Override
    public FormBuilder<PurchaseOrder> createForm() {
       panel.setLayout(new MigLayout("wrap 2","[][]"));

        UserRepository userRepository= new UserRepository();

        JSpinner timeSpinner = new JSpinner( new SpinnerDateModel() );
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "d/M/Y - HH:mm:ss");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date());

        User[] strings = new User[userRepository.getAllData(User.class).size()];
        userRepository.getAllData(User.class).toArray(strings);
        JComboBox<User> roles = new JComboBox<>(strings);

        formBuilder
                .addField(new FormField("orderDate", timeSpinner, "Order Date").convertTo(Timestamp.class))
                .addField(new FormField("buyer", roles, "Buyer"))
        ;

        addSubmitButton();
        addClearButton();
        addDeleteButton(object.getId(), "Do you really wanna delete employee named: " + object.getId());

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
    protected PurchaseOrder getNewInstanceOfObject() {
        return new PurchaseOrder();
    }
}

package com.retailShop.page.module.base.employees;

import com.retailShop.entity.UserContact;
import com.retailShop.page.module.forms.Form;
import com.retailShop.page.module.forms.FormBuilder;
import com.retailShop.page.module.forms.FormField;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.Locale;

public class EmployeesAddContactForm extends Form<UserContact> {

    public EmployeesAddContactForm(JPanel panel, UserContact object) {
        super(panel, object);
    }

    @Override
    public FormBuilder<UserContact> createForm() {
        panel.setLayout(new MigLayout("wrap 2","[][]"));

        FormField phoneNo = new FormField("phoneNo", new JTextField(10), "Phone Number");
        phoneNo.convertTo(Integer.class);

        String[] countryCodes = Locale.getISOCountries();
        JComboBox<String> comboBox = new JComboBox<>();

        for (String countryCode : countryCodes) {
            Locale obj = new Locale("", countryCode);
            comboBox.addItem(obj.getDisplayCountry());
        }

        formBuilder
                .addField(phoneNo)
                .addField(new FormField("email", new JTextField(10), "Email"))
                .addField(new FormField("country", comboBox, "Country"))
                .addField(new FormField("city", new JTextField(10), "City"))
                .addField(new FormField("address", new JTextField(10), "Address"))
        ;
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
    protected UserContact getNewInstanceOfObject() {
        return new UserContact();
    }
}

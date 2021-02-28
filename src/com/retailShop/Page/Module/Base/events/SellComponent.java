package com.retailShop.page.module.base.events;

import com.retailShop.entity.Component;
import com.retailShop.entity.User;
import com.retailShop.page.ContentHandler;
import com.retailShop.page.Logger;
import com.retailShop.page.module.forms.listeners.FormEventListener;
import com.retailShop.page.module.forms.Form;
import com.retailShop.page.module.tables.TableBuilder;
import com.retailShop.repository.ComponentRepository;
import com.retailShop.repository.UserRepository;

import javax.swing.*;
import java.util.ArrayList;

public class SellComponent implements FormEventListener {

    private TableBuilder<Component> tableBuilder;
    private Form componentsForm;
    private ContentHandler contentHandler;

    public SellComponent(TableBuilder<Component> tableBuilder, Form componentsForm, ContentHandler contentHandler) {
        this.tableBuilder = tableBuilder;
        this.componentsForm = componentsForm;
        this.contentHandler = contentHandler;
    }

    @Override
    public void update(String eventType) {
        ArrayList<Component> components = new  ArrayList<>();
        boolean outOfStock = false;

        for (int selectedRow : tableBuilder.getTable().getSelectedRows()) {
            Component component = tableBuilder.getSelectedRowByContentAsObject("den",
                    tableBuilder.getTable().getValueAt(selectedRow, tableBuilder.getColumnNoByName("den")).toString());

            if (component.getStock() == 0) {
                JOptionPane.showMessageDialog(null, component.getDen() + "is out of stock!" , "Sell menu", JOptionPane.ERROR_MESSAGE);
                outOfStock= true;
            } else {
                components.add(component);
            }
        }

        if (!outOfStock) {
            UserRepository userRepository = new UserRepository();
            ComponentRepository componentRepository = new ComponentRepository();
            User[] strings = new User[userRepository.getAllData(User.class).size()];
            userRepository.getAllData(User.class).toArray(strings);

            JComboBox<User> roles = new JComboBox<>(strings);
            JOptionPane.showMessageDialog( null, roles, "Did you really sell: " +
                    components.toString(), JOptionPane.QUESTION_MESSAGE);


            if (roles.getSelectedItem() != null ) {
                componentRepository.sell(components, (User) roles.getSelectedItem(), contentHandler.getUser());
                componentsForm.formEventManager.notify("formRefresh");

                Logger.log("Sold a component to" + roles.getSelectedItem());
            }
        }
    }
}

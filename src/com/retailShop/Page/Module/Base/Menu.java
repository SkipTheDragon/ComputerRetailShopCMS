package com.retailShop.page.module.base;

import com.retailShop.page.ContentHandler;
import com.retailShop.page.module.base.components.ComponentsContent;
import com.retailShop.page.module.base.employees.EmployeesContent;
import com.retailShop.page.module.base.logs.LogsContent;
import com.retailShop.page.module.base.purchases.PurchasesContent;
import com.retailShop.page.Permission;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {
    private ContentHandler contentHandler;

    public Menu(ContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }

    public void buildUi() {
        applyDefaultSettings(contentHandler);

        if (Permission.userCan("access", "EmployeesPage"))
            addButtonToMenu("Manage Employees", contentHandler, new EmployeesContent(contentHandler));
        if (Permission.userCan("access", "ComponentsPage"))
            addButtonToMenu("Manage Components",contentHandler, new ComponentsContent(contentHandler));
        if (Permission.userCan("access","PurchasesPage"))
            addButtonToMenu("Manage Purchases",contentHandler, new PurchasesContent(contentHandler));
        if (Permission.userCan("access","LogsPage"))
            addButtonToMenu("Manage Purchases",contentHandler, new LogsContent(contentHandler));
    }

    public void applyDefaultSettings(ContentHandler contentHandler) {
        setLayout(new MigLayout("","[]",""));
        setBackground(Color.BLACK);
        addButtonToMenu("Home", contentHandler, new HomeContent(contentHandler));
    }

    public void addButtonToMenu(String buttonText, ContentHandler contentHandler, Content linkTo) {

        JButton button = new JButton(buttonText);
        add(button, "w 100%, wrap");

        button.addActionListener(e -> {
            contentHandler.setCurrentContent(linkTo);
        });
    }
}

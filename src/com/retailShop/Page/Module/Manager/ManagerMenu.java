package com.retailShop.Page.Module.Manager;

import com.retailShop.Page.ContentHandler;
import com.retailShop.Page.Module.Base.Components.ComponentsContent;
import com.retailShop.Page.Module.Base.Employees.EmployeesContent;
import com.retailShop.Page.Module.Base.Menu;
import com.retailShop.Page.Module.Base.Purchases.PurchasesContent;

public class ManagerMenu extends Menu {
    private ContentHandler contentHandler;

    public ManagerMenu(ContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }

    @Override
    public void buildUi() {
        applyDefaultSettings(contentHandler);

        addButtonToMenu("Manage Employees", contentHandler, new EmployeesContent(contentHandler));
        addButtonToMenu("Manage Components",contentHandler, new ComponentsContent(contentHandler));
        addButtonToMenu("Manage Purchases",contentHandler, new PurchasesContent(contentHandler));
    }
}

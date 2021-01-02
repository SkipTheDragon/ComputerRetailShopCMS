package com.retailShop.Page.Module.Manager;

import com.retailShop.Page.ContentHandler;
import com.retailShop.Page.Module.Base.Menu;

public class ManagerMenu extends Menu {
    private ContentHandler contentHandler;

    public ManagerMenu(ContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }

    @Override
    public void buildUi() {
        applyDefaultSettings(contentHandler);

        addButtonToMenu("Manage Employees", contentHandler, new EmployeesContent());
    //    addButtonToMenu("Manage Components",);
    }
}

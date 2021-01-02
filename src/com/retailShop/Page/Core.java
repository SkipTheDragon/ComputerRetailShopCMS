package com.retailShop.Page;

import com.retailShop.Entity.User;
import com.retailShop.Page.Module.Base.Menu;
import com.retailShop.Page.Module.Manager.ManagerMenu;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;


public class Core extends JPanel {

    private final User user;
    private final JFrame window;
    private ContentHandler contentHandler;

    public Core(User user, JFrame window) {
       this.user = user;
       this.window = window;
       this.contentHandler = new ContentHandler(user);
       buildUI();
    }

    public void buildUI() {
        setSize(window.getSize());
        setLayout(new MigLayout("fill","[]0[]",""));

        Menu menuBar = getMenuByRoleid(user.getUserRoleByRoleid().getId());
        menuBar.buildUi();
        add(menuBar,"west, w 300!");

        contentHandler.buildUi();
        contentHandler.setBackground(Color.darkGray);
        add(contentHandler,"grow , push , span");
    }

    public Menu getMenuByRoleid(int id) {
        if (id == 1) {
            return new ManagerMenu(contentHandler);
        } else if(id == 2) {

        } else if(id == 3) {

        }
        return null;
    }

}

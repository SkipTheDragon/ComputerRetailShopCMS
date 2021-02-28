package com.retailShop.page;

import com.retailShop.entity.User;
import com.retailShop.page.module.base.Menu;
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

        Menu menuBar = new Menu(contentHandler);
        menuBar.buildUi();
        add(menuBar,"west, w 300!");

        contentHandler.buildUi();
        contentHandler.setBackground(Color.darkGray);
        add(contentHandler,"grow , push , span");
    }

}

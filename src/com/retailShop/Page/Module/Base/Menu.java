package com.retailShop.Page.Module.Base;

import com.retailShop.Page.ContentHandler;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;


public abstract class Menu extends JPanel {
    public abstract void buildUi();

    public void applyDefaultSettings(ContentHandler contentHandler) {
        setLayout(new MigLayout("","[]",""));
        setBackground(Color.BLACK);
        addButtonToMenu("Home", contentHandler, new HomeContent());
    }

    public void addButtonToMenu(String buttonText, ContentHandler contentHandler, Content linkTo) {
        JButton button = new JButton(buttonText);
        add(button, "w 100%, wrap");

        button.addActionListener(e -> {
            contentHandler.setCurrentContent(linkTo);
        });
    }
}

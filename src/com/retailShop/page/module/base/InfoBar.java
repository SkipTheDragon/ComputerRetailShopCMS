package com.retailShop.page.module.base;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class InfoBar extends JPanel {
    private final String username;
    private JLabel locationLabel;

    public InfoBar(String username, String location) {
        this.username = username;
        this.locationLabel = new JLabel(location);

        buildUi();
    }

    public void buildUi() {
        setBackground(Color.gray);
        setLayout(new MigLayout("fillx", "[][]",""));
        add(locationLabel, "");
        add(new JLabel("You are logged in as " + username), "right");
    }

    public void setLocation(String location) {
        this.locationLabel.setText(location);
    }
}

package com.retailShop.page.module.base;

import com.retailShop.page.ContentHandler;

import javax.swing.*;

public abstract class Content extends JPanel {

    protected ContentHandler content;

    public Content(ContentHandler content) {
        this.content = content;
        buildUi();
    }

    public abstract void buildUi();

}

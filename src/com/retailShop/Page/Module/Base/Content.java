package com.retailShop.Page.Module.Base;

import com.retailShop.Page.ContentHandler;
import com.retailShop.Page.Tables.TableBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public abstract class Content extends JPanel {

    protected ContentHandler content;

    public Content(ContentHandler content) {
        this.content = content;
        buildUi();
    }

    public abstract void buildUi();

}

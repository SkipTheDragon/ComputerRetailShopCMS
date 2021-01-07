package com.retailShop.Page.Module.Base;

import com.retailShop.Page.Tables.TableBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public abstract class Content extends JPanel {

    public Content() {
        buildUi();
    }

    public abstract void buildUi();

}

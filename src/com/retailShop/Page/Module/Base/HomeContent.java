package com.retailShop.Page.Module.Base;

import com.retailShop.Page.ContentHandler;

import java.awt.*;

public class HomeContent extends Content{

    public HomeContent(ContentHandler content) {
        super(content);
    }

    @Override
    public void buildUi() {
        setBackground(Color.GREEN);


    }

    @Override
    public String toString() {
        return "Home";
    }


}

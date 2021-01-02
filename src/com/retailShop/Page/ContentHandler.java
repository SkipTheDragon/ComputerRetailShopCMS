package com.retailShop.Page;

import com.retailShop.Entity.User;
import com.retailShop.Page.Module.Base.Content;
import com.retailShop.Page.Module.Base.HomeContent;
import com.retailShop.Page.Module.Base.InfoBar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ContentHandler extends JPanel {
    private InfoBar infoBar;
    private User user;
    private Content currentContent  = new HomeContent();

    public ContentHandler(User user) {
        this.user = user;
        this.infoBar = new InfoBar(user.getName(),currentContent.toString());
    }

    public void buildUi() {
        setLayout(new MigLayout("fill","[]",""));

        add(infoBar,"wrap, grow, h 50!, north");
        add(currentContent,"wrap,grow,push,span");
    }

    public Content getCurrentContent() {
        return currentContent;
    }

    public void setCurrentContent(Content currentContent) {
        remove(this.currentContent);
        this.currentContent = currentContent;
        infoBar.setLocation(currentContent.toString());
        add(currentContent,"wrap,grow,push,span");
        revalidate();
        repaint();
    }

}

package com.retailShop.page;

import com.retailShop.entity.User;
import com.retailShop.page.module.base.Content;
import com.retailShop.page.module.base.HomeContent;
import com.retailShop.page.module.base.InfoBar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ContentHandler extends JPanel {
    private InfoBar infoBar;
    private User user;
    private Content currentContent;

    public ContentHandler(User user) {
        this.user = user;
        currentContent = new HomeContent(this);
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
        
        if (currentContent.toString().split(" ")[1] != null)
            Logger.page = currentContent.toString().split(" ")[1];
        else
            Logger.page = currentContent.toString();

        revalidate();
        repaint();
    }

    public User getUser() {
        return user;
    }
}

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

    protected void exportTable(TableBuilder<?> tableBuilder) {
        JFileChooser fc = new JFileChooser();
        int option = fc.showSaveDialog(this);
        if(option == JFileChooser.APPROVE_OPTION){
            String filename = fc.getSelectedFile().getName();
            String path = fc.getSelectedFile().getParentFile().getPath();

            int len = filename.length();
            String ext = "";
            String file;

            if(len > 4){
                ext = filename.substring(len-4, len);
            }

            if(ext.equals(".xls")){
                file = path + "/" + filename;
            }else{
                file = path + "/" + filename + ".xls";
            }
            tableBuilder.toExcel(new File(file));
        }
    }

}

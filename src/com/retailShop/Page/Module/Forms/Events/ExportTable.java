package com.retailShop.Page.Module.Forms.Events;

import com.retailShop.Page.Module.Forms.FormEventListener;
import com.retailShop.Page.Tables.TableBuilder;

import javax.swing.*;
import java.io.File;

public class ExportTable implements FormEventListener {
    private TableBuilder<?> tableBuilder;
    private JPanel panel;

    public ExportTable(TableBuilder<?> tableBuilder, JPanel panel) {
        this.tableBuilder = tableBuilder;
        this.panel = panel;
    }

    @Override
    public void update(String eventType) {
        JFileChooser fc = new JFileChooser();
        int option = fc.showSaveDialog(panel);
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

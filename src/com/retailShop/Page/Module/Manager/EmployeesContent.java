package com.retailShop.Page.Module.Manager;

import com.retailShop.Entity.User;
import com.retailShop.Page.Module.Base.Content;
import com.retailShop.Page.Tables.TableBuilder;
import com.retailShop.Repository.UserRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.io.File;

public class EmployeesContent extends Content {

    @Override
    public void buildUi() {
        UserRepository userRepository = new UserRepository();
        String[] columns = {"id","password","name", "lastName"};

        EmployeesAddForm employeesAddForm = new EmployeesAddForm();
        TableBuilder<User> tableBuilder = new TableBuilder<>(columns,userRepository.getUsers());

        employeesAddForm.formEventManager.subscribe("formRefresh", e -> {
            tableBuilder.refreshTable(userRepository.getUsers());
        });

        employeesAddForm.formEventManager.subscribe("exportTable", e -> {
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
        });

        employeesAddForm.createForm().buildForm();

        tableBuilder.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (tableBuilder.getSelectedRowAsObject("name") != null) {
                    employeesAddForm.setObject(tableBuilder.getSelectedRowAsObject("name"));
                }
            }
        });

        JTable jt = tableBuilder.getTable();
        JScrollPane sp=new JScrollPane(jt);

        setLayout(new MigLayout("fill","[][]"));
        add(employeesAddForm.getPanel(), "growy");
        add(sp, "grow , push , span");
    }

    @Override
    public String toString() {
        return "Manage Employees";
    }
}

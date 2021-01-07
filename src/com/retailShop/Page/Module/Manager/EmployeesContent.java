package com.retailShop.Page.Module.Manager;

import com.retailShop.Entity.User;
import com.retailShop.Page.Module.Base.Content;
import com.retailShop.Page.Module.Forms.FormBuilder;
import com.retailShop.Page.Tables.TableBuilder;
import com.retailShop.Repository.UserRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;

public class EmployeesContent extends Content {

    @Override
    public void buildUi() {
        UserRepository userRepository = new UserRepository();
        String[] columns = {"id","password","name", "lastName", "userContactByContact_email"};

        EmployeesAddForm employeesAddForm = new EmployeesAddForm(new JPanel(), new User());
        TableBuilder<User> tableBuilder = new TableBuilder<>(columns,userRepository.getAllData(User.class));

        employeesAddForm.formEventManager.subscribe("formRefresh", e -> {
            tableBuilder.refreshTable(userRepository.getAllData(User.class));
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

        employeesAddForm.createForm();

        tableBuilder.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (tableBuilder.getSelectedRowAsObject("name") != null) {
                    employeesAddForm.setObject(tableBuilder.getSelectedRowAsObject("name"));
                    employeesAddForm.refreshForm();

                }
            }
        });

        JTable jt = tableBuilder.getTable();
        JScrollPane sp= new JScrollPane(jt);
        JTextField jtfFilter = new JTextField(5);


        new TableBuilder.Search(jtfFilter,jt).attachSearchHandler();

        setLayout(new MigLayout("fill","[][]"));
        add(employeesAddForm.getPanel(), "growy");
        add(sp, "grow , push , span");
        add(new JLabel());
        add(new JLabel("Specify a word to match:"));
        add(jtfFilter, "w 100%");


    }

    @Override
    public String toString() {
        return "Manage Employees";
    }
}

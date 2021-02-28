package com.retailShop.page.module.base.employees;

import com.retailShop.entity.User;
import com.retailShop.page.ContentHandler;
import com.retailShop.page.module.base.Content;
import com.retailShop.page.module.forms.events.ExportTable;
import com.retailShop.page.module.tables.TableBuilder;
import com.retailShop.page.Permission;
import com.retailShop.repository.UserRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class EmployeesContent extends Content {

    public EmployeesContent(ContentHandler content) {
        super(content);
    }

    @Override
    public void buildUi() {
        UserRepository userRepository = new UserRepository();
        String[] columns = {"id", "password", "name", "lastName", "userContactByContact_email"};

        EmployeesAddForm employeesAddForm = new EmployeesAddForm(new JPanel(), new User());
        TableBuilder<User> tableBuilder = new TableBuilder<>(columns, userRepository.getAllData(User.class));
        if (Permission.userCan("add", "Employees")) {
            employeesAddForm.createForm();
        }
        JTable jt = tableBuilder.getTable();
        JScrollPane sp = new JScrollPane(jt);
        JTextField jtfFilter = new JTextField(5);

        TableBuilder.Search tableSearchHandler = new TableBuilder.Search(jtfFilter, jt);
        tableSearchHandler.attachSearchHandler();

        setLayout(new MigLayout("fill", "[][]"));
        add(employeesAddForm.getPanel(), "growy");
        add(sp, "grow , push , span");
        add(new JLabel());
        add(new JLabel("Specify a word to match:"));
        add(jtfFilter, "w 100%");

        employeesAddForm.formEventManager.subscribe("formRefresh", e -> {
            tableBuilder.refreshTable(userRepository.getAllData(User.class));
            tableSearchHandler.updateTable(jt);
        });

        employeesAddForm.formEventManager.subscribe("exportTable", new ExportTable(tableBuilder, this));

        if (Permission.userCan("update", "Employees")) {
            tableBuilder.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    if (tableBuilder.getSelectedRowAsObject("name") != null) {
                        employeesAddForm.setObject(tableBuilder.getSelectedRowAsObject("name"));
                        employeesAddForm.refreshForm();
                    }
                }
            });
        }
    }

    @Override
    public String toString() {
        return "Manage Employees";
    }
}

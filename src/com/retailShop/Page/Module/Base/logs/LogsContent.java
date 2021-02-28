package com.retailShop.page.module.base.logs;

import com.retailShop.entity.Log;
import com.retailShop.entity.User;
import com.retailShop.page.ContentHandler;
import com.retailShop.page.module.base.Content;
import com.retailShop.page.module.base.employees.EmployeesAddForm;
import com.retailShop.page.module.forms.events.ExportTable;
import com.retailShop.page.module.tables.TableBuilder;
import com.retailShop.page.Permission;
import com.retailShop.repository.LogsRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class LogsContent extends Content {
    public LogsContent(ContentHandler content) {
        super(content);
    }

    @Override
    public void buildUi() {
        LogsRepository userRepository = new LogsRepository();
        String[] columns = {"action", "page","dateA", "user"};

        EmployeesAddForm employeesAddForm = new EmployeesAddForm(new JPanel(), new User());
        TableBuilder<Log> tableBuilder = new TableBuilder<>(columns, userRepository.getAllData(Log.class));
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
         //   tableBuilder.refreshTable(userRepository.getAllData(User.class));
            tableSearchHandler.updateTable(jt);
        });

        employeesAddForm.formEventManager.subscribe("exportTable", new ExportTable(tableBuilder, this));


    }

    @Override
    public String toString() {
        return "Manage Logs";
    }
}

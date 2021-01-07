package com.retailShop.Page.Module.Base.Components;

import com.retailShop.Entity.Component;
import com.retailShop.Entity.ComponentSpecification;
import com.retailShop.Entity.User;
import com.retailShop.Page.Module.Base.Content;
import com.retailShop.Page.Module.Manager.EmployeesAddForm;
import com.retailShop.Page.Tables.TableBuilder;
import com.retailShop.Repository.ComponentRepository;
import com.retailShop.Repository.UserRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ComponentsContent extends Content {

    @Override
    public void buildUi() {
        ComponentRepository componentRepository = new ComponentRepository();
        String[] columns = {"den","price","type", "sale","maker","warranty","stock","specs"};

        ComponentsSpecForm componentsForm = new ComponentsSpecForm(new JPanel(), new ComponentSpecification());
        TableBuilder<Component> tableBuilder = new TableBuilder<>(columns,componentRepository.getAllData(Component.class));

        componentsForm.formEventManager.subscribe("formRefresh", e -> {
            tableBuilder.refreshTable(componentRepository.getAllData(Component.class));
        });

        componentsForm.formEventManager.subscribe("exportTable", e -> {
            exportTable(tableBuilder);
        });

        componentsForm.createForm().buildForm();

        tableBuilder.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (tableBuilder.getSelectedRowAsObject("den") != null) {
                    //componentsForm.setObject(tableBuilder.getSelectedRowAsObject("den"));
                }
            }
        });

        JTable jt = tableBuilder.getTable();
        JScrollPane sp=new JScrollPane(jt);

        setLayout(new MigLayout("fill","[][]"));
        add(componentsForm.getPanel(), "growy");
        add(sp, "grow , push , span");
    }

    @Override
    public String toString() {
        return "Manage Components";
    }
}

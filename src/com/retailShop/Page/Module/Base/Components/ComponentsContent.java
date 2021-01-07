package com.retailShop.Page.Module.Base.Components;

import com.retailShop.Entity.Component;
import com.retailShop.Entity.ComponentSpecification;
import com.retailShop.Entity.User;
import com.retailShop.Page.Module.Base.Content;
import com.retailShop.Page.Module.Forms.Events.ExportTable;
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
        String[] columns = {"den","price","type", "sale","maker","warranty","stock"};

        ComponentsForm componentsForm = new ComponentsForm(new JPanel(), new Component());
        TableBuilder<Component> tableBuilder = new TableBuilder<>(columns,componentRepository.getAllData(Component.class));

        componentsForm.createForm();

        JTable jt = tableBuilder.getTable();
        JScrollPane sp=new JScrollPane(jt);
        JTextField jtfFilter = new JTextField(5);
        TableBuilder.Search tableSearchHandler = new TableBuilder.Search(jtfFilter,jt);
        tableSearchHandler.attachSearchHandler();
        setLayout(new MigLayout("fill","[][]"));
        add(componentsForm.getPanel(), "growy");
        add(sp, "grow , push , span");
        add(new JLabel());
        add(new JLabel("Specify a word to match:"));
        add(jtfFilter, "w 100%");

        componentsForm.formEventManager.subscribe("formRefresh", e -> {
            tableBuilder.refreshTable(componentRepository.getAllData(Component.class));
            tableSearchHandler.updateTable(jt);
        });

        componentsForm.formEventManager.subscribe("exportTable", new ExportTable(tableBuilder,this));

        tableBuilder.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (tableBuilder.getSelectedRowAsObject("den") != null) {
                    componentsForm.setObject(tableBuilder.getSelectedRowAsObject("den"));
                    componentsForm.refreshForm();
                }
            }
        });

    }

    @Override
    public String toString() {
        return "Manage Components";
    }
}

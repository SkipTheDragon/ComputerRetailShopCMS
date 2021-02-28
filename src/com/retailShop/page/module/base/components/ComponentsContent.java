package com.retailShop.page.module.base.components;

import com.retailShop.entity.Component;
import com.retailShop.page.ContentHandler;
import com.retailShop.page.module.base.components.specification.CompSpecificationContent;
import com.retailShop.page.module.base.components.types.CompTypeContent;
import com.retailShop.page.module.base.Content;
import com.retailShop.page.module.base.events.SellComponent;
import com.retailShop.page.module.forms.events.ExportTable;
import com.retailShop.page.module.tables.TableBuilder;
import com.retailShop.page.Permission;
import com.retailShop.repository.ComponentRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ComponentsContent extends Content {

    public ComponentsContent(ContentHandler content) {
        super(content);
    }

    public JPanel buttonbar() {
        JPanel buttonbar = new JPanel();
        JButton components = new JButton("Manage Components");
        JButton types = new JButton("Manage Types");
        JButton specifications = new JButton("Manage Specifications");

        if (Permission.userCan("access", "Components"))
            buttonbar.add(components);
        if (Permission.userCan("access", "Specifications"))
            buttonbar.add(specifications);
        if (Permission.userCan("access", "Types"))
            buttonbar.add(types);

        components.addActionListener(e -> {
            content.setCurrentContent(new ComponentsContent(content));
        });

        types.addActionListener(e -> {
            content.setCurrentContent(new CompTypeContent(content));
        });

        specifications.addActionListener(e -> {
            content.setCurrentContent(new CompSpecificationContent(content));
        });

        return buttonbar;
    }

    @Override
    public void buildUi() {
        ComponentRepository componentRepository = new ComponentRepository();
        String[] columns = {"den","price","type", "maker","warranty","stock"};

        ComponentsForm componentsForm = new ComponentsForm(new JPanel(), new Component());
        TableBuilder<Component> tableBuilder = new TableBuilder<>(columns,componentRepository.getAllData(Component.class));
        if (Permission.userCan("add", "Components")) {
            componentsForm.createForm();
        }
        JTable jt = tableBuilder.getTable();
        JScrollPane sp=new JScrollPane(jt);
        JTextField jtfFilter = new JTextField(5);
        TableBuilder.Search tableSearchHandler = new TableBuilder.Search(jtfFilter,jt);
        tableSearchHandler.attachSearchHandler();

        setLayout(new MigLayout("fill","[][]"));
        add(buttonbar(), "grow , span");
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

        if (Permission.userCan("update", "Components"))
            tableBuilder.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (tableBuilder.getSelectedRowAsObject("den") != null) {
                    componentsForm.setObject(tableBuilder.getSelectedRowAsObject("den"));
                    componentsForm.refreshForm();
                }
            }
        });

        if (Permission.userCan("sell", "Components"))
        componentsForm.formEventManager.subscribe("sellComponent", new SellComponent(tableBuilder,componentsForm,content));

    }

    @Override
    public String toString() {
        return "Manage Components";
    }
}

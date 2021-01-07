package com.retailShop.Page.Module.Base.Components;

import com.retailShop.Entity.Component;
import com.retailShop.Page.ContentHandler;
import com.retailShop.Page.Module.Base.Components.Specification.CompSpecificationContent;
import com.retailShop.Page.Module.Base.Components.Types.CompTypeContent;
import com.retailShop.Page.Module.Base.Content;
import com.retailShop.Page.Module.Forms.Events.ExportTable;
import com.retailShop.Page.Tables.TableBuilder;
import com.retailShop.Repository.ComponentRepository;
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

        buttonbar.add(components);
        buttonbar.add(specifications);
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

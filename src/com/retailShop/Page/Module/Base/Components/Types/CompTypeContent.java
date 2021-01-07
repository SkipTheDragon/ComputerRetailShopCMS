package com.retailShop.Page.Module.Base.Components.Types;

import com.retailShop.Entity.Component;
import com.retailShop.Entity.ComponentType;
import com.retailShop.Page.ContentHandler;
import com.retailShop.Page.Module.Base.Components.ComponentsContent;
import com.retailShop.Page.Module.Base.Components.ComponentsForm;
import com.retailShop.Page.Module.Base.Content;
import com.retailShop.Page.Module.Forms.Events.ExportTable;
import com.retailShop.Page.Tables.TableBuilder;
import com.retailShop.Repository.ComponentRepository;
import com.retailShop.Repository.ComponentTypeRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class CompTypeContent extends ComponentsContent {
    public CompTypeContent(ContentHandler content) {
        super(content);
    }

    @Override
    public void buildUi() {
        ComponentTypeRepository componentTypeRepository = new ComponentTypeRepository();
        String[] columns = {"id","den"};

        CompTypeForm componentsForm = new CompTypeForm(new JPanel(), new ComponentType());
        TableBuilder<ComponentType> tableBuilder = new TableBuilder<>(columns,componentTypeRepository.getAllData(ComponentType.class));

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
            tableBuilder.refreshTable(componentTypeRepository.getAllData(ComponentType.class));
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
        return "Manage Component Types";
    }
}

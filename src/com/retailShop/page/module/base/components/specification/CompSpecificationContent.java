package com.retailShop.page.module.base.components.specification;

import com.retailShop.entity.ComponentSpecification;
import com.retailShop.page.ContentHandler;
import com.retailShop.page.module.base.components.ComponentsContent;
import com.retailShop.page.module.forms.events.ExportTable;
import com.retailShop.page.module.tables.TableBuilder;
import com.retailShop.repository.ComponentSpecRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class CompSpecificationContent extends ComponentsContent {
    public CompSpecificationContent(ContentHandler content) {
        super(content);
    }

    @Override
    public void buildUi() {
        ComponentSpecRepository componentSpecRepository = new ComponentSpecRepository();
        String[] columns = {"id","den","content", "componentByComponent_den"};

        ComponentsSpecForm componentsSpecForm = new ComponentsSpecForm(new JPanel(), new ComponentSpecification());
        TableBuilder<ComponentSpecification> tableBuilder = new TableBuilder<>(columns,componentSpecRepository.getAllData(ComponentSpecification.class));

        componentsSpecForm.createForm();

        JTable jt = tableBuilder.getTable();
        JScrollPane sp=new JScrollPane(jt);
        JTextField jtfFilter = new JTextField(5);
        TableBuilder.Search tableSearchHandler = new TableBuilder.Search(jtfFilter,jt);
        tableSearchHandler.attachSearchHandler();

        setLayout(new MigLayout("fill","[][]"));
        add(buttonbar(), "grow , span");
        add(componentsSpecForm.getPanel(), "growy");
        add(sp, "grow , push , span");
        add(new JLabel());
        add(new JLabel("Specify a word to match:"));
        add(jtfFilter, "w 100%");

        componentsSpecForm.formEventManager.subscribe("formRefresh", e -> {
            tableBuilder.refreshTable(componentSpecRepository.getAllData(ComponentSpecification.class));
            tableSearchHandler.updateTable(jt);
        });

        componentsSpecForm.formEventManager.subscribe("exportTable", new ExportTable(tableBuilder,this));

        tableBuilder.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (tableBuilder.getSelectedRowAsObject("den") != null) {
                    componentsSpecForm.setObject(tableBuilder.getSelectedRowAsObject("den"));
                    componentsSpecForm.refreshForm();
                }
            }
        });
    }

    @Override
    public String toString() {
        return "Manage Component's Specifications";
    }
}

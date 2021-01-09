package com.retailShop.Page.Module.Base.Purchases;

import com.retailShop.Entity.Component;
import com.retailShop.Entity.PurchaseOrder;
import com.retailShop.Entity.PurchaseOrderItem;
import com.retailShop.Page.ContentHandler;
import com.retailShop.Page.Module.Base.Components.ComponentsForm;
import com.retailShop.Page.Module.Base.Content;
import com.retailShop.Page.Module.Forms.Events.ExportTable;
import com.retailShop.Page.Tables.TableBuilder;
import com.retailShop.Repository.ComponentRepository;
import com.retailShop.Repository.PurchasesItemRepository;
import com.retailShop.Repository.PurchasesRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PurchasesContent extends Content {
    public PurchasesContent(ContentHandler content) {
        super(content);
    }

    @Override
    public void buildUi() {

        PurchasesRepository purchasesRepository = new PurchasesRepository();
        PurchasesItemRepository purchasesItemRepository = new PurchasesItemRepository();
        String[] columns = {"id","buyer","orderDate"};
        String[] columns2 = {"componentByComponent_den", "componentByComponent_price", "componentByComponent_type", "componentByComponent_maker"};

        TableBuilder<PurchaseOrder> tableBuilder = new TableBuilder<>(columns,purchasesRepository.getAllData(PurchaseOrder.class));
        TableBuilder<PurchaseOrderItem> purchasesItemRepositoryTableBuilder = new TableBuilder<>(columns2, new ArrayList<>());

        JTable jt = tableBuilder.getTable();
        JTable jt2 = purchasesItemRepositoryTableBuilder.getTable();
        JScrollPane sp= new JScrollPane(jt);
        JScrollPane sp2= new JScrollPane(jt2);

        JTextField jtfFilter = new JTextField(5);
        TableBuilder.Search tableSearchHandler = new TableBuilder.Search(jtfFilter,jt);
        tableSearchHandler.attachSearchHandler();

        setLayout(new MigLayout("fill","[][]"));
    //    add(componentsForm.getPanel(), "growy");
        add(sp, "grow , push , span");
        add(sp2, "grow , push , span");
        add(new JLabel());
        add(new JLabel("Specify a word to match:"));
        add(jtfFilter, "w 100%");

        tableBuilder.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {

                if (tableBuilder.getSelectedRowAsObject("buyer") != null) {
                    if (purchasesItemRepository.getAllItems(tableBuilder.getSelectedRowAsObject("id")) != null)
                    purchasesItemRepositoryTableBuilder.refreshTable(purchasesItemRepository.getAllItems(tableBuilder.getSelectedRowAsObject("id")));
                }

            }
        });
       // componentsForm.formEventManager.subscribe("exportTable", new ExportTable(tableBuilder,this));
    }

    @Override
    public String toString() {
        return "Manage Purchases";
    }
}

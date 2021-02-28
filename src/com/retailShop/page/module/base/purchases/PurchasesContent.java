package com.retailShop.page.module.base.purchases;

import com.retailShop.entity.PurchaseOrder;
import com.retailShop.entity.PurchaseOrderItem;
import com.retailShop.page.ContentHandler;
import com.retailShop.page.module.base.Content;
import com.retailShop.page.module.tables.TableBuilder;
import com.retailShop.page.Permission;
import com.retailShop.repository.PurchasesItemRepository;
import com.retailShop.repository.PurchasesRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PurchasesContent extends Content {
    public PurchasesContent(ContentHandler content) {
        super(content);
    }

    @Override
    public void buildUi() {

        PurchasesRepository purchasesRepository = new PurchasesRepository();
        PurchasesItemRepository purchasesItemRepository = new PurchasesItemRepository();
        PurchasesForm purchasesForm = new PurchasesForm(new JPanel(), new PurchaseOrder());

        purchasesForm.formEventManager.addEvent("report");

        String[] columns = {"id","buyer", "seller","orderDate"};
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
        TableBuilder.Search tableSearchHandler2 = new TableBuilder.Search(jtfFilter,jt2);
        tableSearchHandler2.attachSearchHandler();
        if (Permission.userCan("add", "Purchases")) {
            purchasesForm.createForm();
        }
        setLayout(new MigLayout("fill","[][]"));
        add(purchasesForm.getPanel(), "growy");
        add(sp, "grow , push , span");
        add(sp2, "grow , push , span");
        add(new JLabel());
        add(new JLabel("Specify a word to match:"));
        add(jtfFilter, "w 100%");

        if (Permission.userCan("update", "Purchases")) {
            tableBuilder.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {

                    if (tableBuilder.getSelectedRowAsObject("buyer") != null) {
                        if (purchasesItemRepository.getAllItems(tableBuilder.getSelectedRowAsObject("id")) != null)
                            purchasesItemRepositoryTableBuilder.refreshTable(purchasesItemRepository.getAllItems(tableBuilder.getSelectedRowAsObject("id")));
                    }
                }
            });
        }

        if (Permission.userCan("todaysRaport", "Purchases")) {
            purchasesForm.formEventManager.subscribe("report", e -> {
                JPanel jpanel = new JPanel();
                int moneyMade = 0;
                int componentsSold = 0;

                final Calendar date = Calendar.getInstance();
                date.add(Calendar.DATE, -7);
                Date previousDate = date.getTime();
                Date now = new Date();

                for (Object o : purchasesRepository.soldInThisTimePeriod(previousDate, now)) {
                    PurchaseOrder po = (PurchaseOrder) o;
                    for (Object allItem : purchasesItemRepository.getAllItems(po)) {
                        PurchaseOrderItem purchaseOrderItem = (PurchaseOrderItem) allItem;

                        moneyMade += purchaseOrderItem.getComponentByComponent().getPrice();
                        componentsSold += 1;
                    }
                }

                jpanel.add(new JLabel("Money made: " + moneyMade + "$"));
                jpanel.add(new JLabel("Components sold: " + componentsSold));

                int reply = JOptionPane.showConfirmDialog(null, jpanel, "Today's report", JOptionPane.YES_NO_OPTION);
            });
        }

    }

    @Override
    public String toString() {
        return "Manage Purchases";
    }
}

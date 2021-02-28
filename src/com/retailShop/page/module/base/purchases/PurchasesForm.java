package com.retailShop.page.module.base.purchases;

import com.retailShop.entity.PurchaseOrder;
import com.retailShop.entity.PurchaseOrderItem;
import com.retailShop.page.module.forms.Form;
import com.retailShop.page.module.forms.FormBuilder;
import com.retailShop.page.Permission;
import com.retailShop.repository.PurchasesItemRepository;
import com.retailShop.repository.PurchasesRepository;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class PurchasesForm extends Form<PurchaseOrder> {

    public PurchasesForm(JPanel panel, PurchaseOrder object) {
        super(panel, object);
    }

    @Override
    public FormBuilder<PurchaseOrder> createForm() {
       panel.setLayout(new MigLayout("wrap 2","[][]"));

//        UserRepository userRepository= new UserRepository();

//        JSpinner timeSpinner = new JSpinner( new SpinnerDateModel() );
//        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "d/M/Y - HH:mm:ss");
//        timeSpinner.setEditor(timeEditor);
//        timeSpinner.setValue(new Date());

//        User[] strings = new User[userRepository.getAllData(User.class).size()];
//        userRepository.getAllData(User.class).toArray(strings);
//        JComboBox<User> roles = new JComboBox<>(strings);

//        formBuilder
//                .addField(new FormField("orderDate", timeSpinner, "Order Date").convertTo(Timestamp.class))
//                .addField(new FormField("buyer", roles, "Buyer"))
//        ;
//
//        addSubmitButton();
//        addClearButton();
//        if (Permission.userCan("delete", "Purchases"))
//            addDeleteButton(object.getId(), "Do you really wanna delete employee named: " + object.getId());

        formBuilder.buildForm();

        JDateChooser calendar = new JDateChooser();
        JDateChooser calendar2 = new JDateChooser();

        getPanel().add(new JLabel());
        getPanel().add(new JLabel("Range of report"), "span 2");
        getPanel().add(new JLabel("Start Date"));
        getPanel().add(calendar);
        getPanel().add(new JLabel("End date"));
        getPanel().add(calendar2);

        if (Permission.userCan("generateRaport", "Purchases")) {

            JButton jButton = new JButton("Generate Report");

            jButton.addActionListener(e -> {
                PurchasesRepository purchasesRepository = new PurchasesRepository();
                PurchasesItemRepository purchasesItemRepository = new PurchasesItemRepository();

                JPanel jpanel = new JPanel();
                int moneyMade = 0;
                int componentsSold = 0;

                for (Object o : purchasesRepository.soldInThisTimePeriod(calendar.getDate(), calendar2.getDate())) {
                    PurchaseOrder po = (PurchaseOrder) o;
                    for (Object allItem : purchasesItemRepository.getAllItems(po)) {
                        PurchaseOrderItem purchaseOrderItem = (PurchaseOrderItem) allItem;

                        moneyMade += purchaseOrderItem.getComponentByComponent().getPrice();
                        componentsSold += 1;
                    }
                }

                jpanel.add(new JLabel("Money made: " + moneyMade));
                jpanel.add(new JLabel("Components sold: " + componentsSold));

                int reply = JOptionPane.showConfirmDialog(null, jpanel, "Today's report", JOptionPane.YES_NO_OPTION);
            });

            getPanel().add(jButton, "span 2");
        }
        return formBuilder;
    }

    @Override
    public void processForm() {
        if (!objectExistInDatabase(object.getId()))
            insert();
        else
            update();
    }

    @Override
    protected PurchaseOrder getNewInstanceOfObject() {
        return new PurchaseOrder();
    }
}

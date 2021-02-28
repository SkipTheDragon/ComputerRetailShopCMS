package com.retailShop.page.module.base;

import com.retailShop.page.ContentHandler;

public class HomeContent extends Content{

    public HomeContent(ContentHandler content) {
        super(content);
    }

    @Override
    public void buildUi() {
//        setLayout(new MigLayout("","[][]",""));
//        JLabel search = new JLabel("Search:");
//        JTextField searchText = new JTextField(10);
//
//        add(searchText, "w 100!");
//        add(new JButton("Submit"), "push, span");
//
//        ArrayList<Class<? extends EntityType>> repository = new ArrayList<>();
//        repository.add(Component.class);
//        repository.add(PurchaseOrder.class);
//        repository.add(User.class);
//
//        for (Class<? extends EntityType> aClass : repository) {
//            JRadioButton jRadioButton = new JRadioButton(aClass.toString());
//            add(jRadioButton, "span");
//            jRadioButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//
//                }
//            });
//        }
    }

    @Override
    public String toString() {
        return "Home";
    }
}

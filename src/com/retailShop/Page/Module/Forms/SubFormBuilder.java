package com.retailShop.Page.Module.Forms;

import javax.swing.*;

public class SubFormBuilder<T extends EntityType> extends FormBuilder<T>{
    String mapTo;
    String name;
    /**
     * @param object The object that we bind data to.
     * @param form   The class where we build the form and run the after submit instructions.
     * @param panel
     */
    public SubFormBuilder(T object, Form<T> form, JPanel panel) {
        super(object, form, panel);
    }


}

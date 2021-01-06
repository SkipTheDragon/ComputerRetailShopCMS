package com.retailShop.Page.Module.Forms;

import com.toddfast.util.convert.TypeConverter;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;


public class FormField {
    private String name;
    private String labelLayoutConstraints;
    private final String mapTo;
    private Object component;
    private Class<?> convertTo;
    private String layoutConstraint;
    private Set<ConstraintViolation<Object>> errors;
    private Object object;
    private Object defaultValue;
    private boolean hiddenField = false;
    /**
     * Basic field.
     * @param mapTo Which object property to map the input.
     * @param component The component that we get input from.
     */
    public FormField(String mapTo, Object component){
        this.mapTo = mapTo;
        this.component = component;
    }

    /**
     * Creates a basic field with label.
     * @param name label name.
     */
    public FormField(String mapTo, Object component,  String name){
        this(mapTo, component);
        this.name = name;
    }

    /**
     * Creates a basic field with label and layout constraints for each label and component.
     * @param layoutConstraint constraints for component.
     * @param name label name.
     * @param labelLayoutConstraints labels constraints.
     */
    public FormField(String mapTo, Object component, String layoutConstraint, String name, String labelLayoutConstraints){
        this(mapTo, component, layoutConstraint);
        this.name = name;
        this.labelLayoutConstraints = labelLayoutConstraints;
        this.layoutConstraint = layoutConstraint;
    }

    public boolean isHiddenField() {
        return hiddenField;
    }

    public void setHiddenField(boolean hiddenField) {
        this.hiddenField = hiddenField;
    }

    /**
     * Tells the TypeConvertor to convert the input from JComponent to this type of class.
     * @param convertTo Class we need to convert the parameter to.
     */
    public FormField convertTo(Class<?> convertTo) {
        this.convertTo = convertTo;

        return this;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * Overrides or adds a default value to JComponent.
     * @param defaultValue The default value to assign, usually a string.
     */
    public FormField setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        setDefaultByComponent(defaultValue);

        return this;
    }

    public Object getObject() {
        return object;
    }

    private boolean hasLabel() {
        return name != null;
    }

    /**
     * @return true if the form has errors, false otherwise.
     */
    public boolean hasErrors() {
        if (this.errors != null)
            return !this.errors.isEmpty();

        return false;
    }

    /**
     * Takes the input from JComponent and binds it to the Object, also converts the input if needed.
     */
    private void bindInput() {
        try {
            if (this.convertTo != null) {
                var i = TypeConverter.convert(this.convertTo, getInputByComponent());
                invokeSetter(object, mapTo, i);
            } else {
                invokeSetter(object,mapTo,getInputByComponent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        bindInput();
        checkForErrors();
    }

    /**
     * Transforms error messages to JLabels.
     * @return List of error messages.
     */
    public ArrayList<JLabel> getErrorsAsLabels() {
        ArrayList<JLabel> labelList = new ArrayList<>();

        for (ConstraintViolation<Object> error : errors) {
            labelList.add(new JLabel(error.getMessage()));
        }

        return labelList;
    }

    /**
     * Renders the input field with or without label and assigns a default value to field's input.
     * @param panel where we add our labels to.
     */
    public void render(JPanel panel) {

        if (defaultValue == null) {
            setValueFromObject();
        }

        if (hiddenField) return;

        if (hasLabel()) {
            panel.add(new JLabel(name+ ":"), layoutConstraint);
        } else {
            panel.add(new JLabel());
        }

        setDefaultByComponent(defaultValue);

        if (component instanceof JComponent)
            panel.add((JComponent) component, labelLayoutConstraints);
    }

    /**
     * Every component has a different way to get its input cause why not.
     */
    private Object getInputByComponent() throws Exception {
        if (component instanceof JComboBox) {
            return ((JComboBox<?>) component).getSelectedItem();
        } else if (component instanceof JFileChooser) {
            return ((JFileChooser) component).getSelectedFiles();
        } else if (component instanceof JOptionPane) {
            return ((JOptionPane) component).getInputValue();
        } else if (component instanceof JPasswordField) {
            return ((JPasswordField) component).getPassword();
        } else if (component instanceof JTextComponent) {
            return ((JTextComponent) component).getText();
        } else {
            return component;
        }
    }

    /**
     * Sets the default value for each type of component.
     * @param defaultValue the value to set as component's default.
     */
    private void setDefaultByComponent(Object defaultValue) {
        if (defaultValue != null) {
            if (component instanceof JTextComponent) {
                ((JTextComponent) component).setText(TypeConverter.convert(String.class, defaultValue));
            } else if (component instanceof JComboBox) {
                if (getDefaultItemToSelect() != null) {
                    ((JComboBox<?>) component).setSelectedItem(getDefaultItemToSelect());
                } else {
                    ((JComboBox<?>) component).setSelectedItem(0);
                }
            } else {
                component = defaultValue;
            }
        }
    }

    /**
     * Gets object's default value from a prefilled combobox.
     * @return object's default value.
     */
    private Object getDefaultItemToSelect() {
        if (component instanceof JComboBox) {
            JComboBox<?> comboBox =  ((JComboBox<?>) component);

            int size = comboBox.getItemCount();
            for (int i = 0; i < size; i++) {
                try {
                    if (comboBox.getItemAt(i).equals(invokeGetter(object,mapTo))) {
                        return comboBox.getItemAt(i);
                    }
                } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Takes objects property that corresponds to mapTo field.
     * Adds objects property to JComponent as String.
     */
    private void setValueFromObject() {
        try {
            Object getter = invokeGetter(object, mapTo);
            if (getter != null) {
                this.defaultValue = getter;
            }
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * Checks if the input has errors according to Hibernate's validation
     */
    private void checkForErrors() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        this.errors = validator.validateProperty(object, mapTo);
    }


    private void invokeSetter(Object obj, String propertyName, Object variableValue) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PropertyDescriptor pd;
        pd = new PropertyDescriptor(propertyName, obj.getClass());
        Method setter = pd.getWriteMethod();
        setter.invoke(obj,variableValue);
    }

    private Object invokeGetter(Object obj, String propertyName) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PropertyDescriptor pd;
        pd = new PropertyDescriptor(propertyName, obj.getClass());
        Method getter = pd.getReadMethod();
        return getter.invoke(obj);
    }
}

package com.retailShop.Page.Tables;

import com.retailShop.Page.Module.Forms.FormEventManager;
import com.toddfast.util.convert.TypeConverter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TableBuilder<T> {

    private String[] columns;
    private List<T> data;
    private final JTable table;
    private String[][] columnsAsStringArray;
    private boolean isCellEditable = false;

    /**
     * Creates a new table and applies default settings.
     * @param columns Array of columns or object's properties.
     * @param data List of objects.
     */
    public TableBuilder(String[] columns, List<T> data) {
        this.table = new JTable();
        this.columns = columns;
        refreshTable(data);

    }

    public void refreshTable(List<T> data) {
        this.data = data;
        convertObjectToArray();
        defaultSettings();
    }

    public void refreshTable(String[] columns, List<T> data) {
        this.columns = columns;
        this.data = data;
        convertObjectToArray();
        defaultSettings();
    }

    public JTable getTable() {
        return table;
    }

    /**
     * Converts data (list of objects) to a two dimensional array containing object's data.
     * Only columns that match object's properties will get added to the array.
     */
    private void convertObjectToArray() {
        ArrayList<String[]> columnsAsArrayList = new ArrayList<>();

        for (T object : data) {
            ArrayList<String> row = new ArrayList<>();
            for (String column_name : columns) {
                row.add(getPropertyFromString(object,column_name));
            }
            columnsAsArrayList.add(row.toArray(String[]::new));
        }

        this.columnsAsStringArray = columnsAsArrayList.toArray(String[][]::new);
    }

    private String getPropertyFromString(T parentObject, String column_name) {
        if (column_name.contains("_")) {
            String[] word = column_name.split("_");

            try {
                Object childObject = invokeGetter(parentObject, word[0]);
                return invokeGetter(childObject, word[1]).toString();
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return getRowDataFromObjectProperty(parentObject, column_name);
    }
    /**
     * Get the data from the object according to column_name
     * @param object we need to extract the data from.
     * @param column_name (object's property) the data that we need.
     * @return the value of object's property.
     */
    private String getRowDataFromObjectProperty(Object object, String column_name) {
        try {
            Object getter = invokeGetter(object,column_name);
            if (getter != null) {
                return TypeConverter.convert(String.class, invokeGetter(object, column_name));
            }
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Shortcut for adding an action listener to the table.
     * @param listSelectionListener the listener
     */
    public void addListSelectionListener(ListSelectionListener listSelectionListener) {
        table.setCellSelectionEnabled(true);
        ListSelectionModel select= table.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        select.addListSelectionListener(listSelectionListener);
    }

    /**
     * Shortcut for making table's cells editable.
     * @param cellEditable ?
     */
    public void setCellEditable(boolean cellEditable) {
        isCellEditable = cellEditable;
    }

    public boolean isCellEditable() {
        return isCellEditable;
    }

    /**
     * Shortcut for rewriting table's model.
     * @param tableModel the new table model.
     */
    public void setModel(TableModel tableModel) {
        table.setModel(tableModel);
    }


    private void defaultSettings() {
        table.setModel(new DefaultTableModel(columnsAsStringArray,columns) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return isCellEditable;
            }
        });
    }

    /**
     * Exports table to excel type of file
     * @param file where we write table's data.
     */
    public void toExcel(File file){
        try {
            TableModel model = table.getModel();
            FileWriter excel = new FileWriter(file);

            for(int i = 0; i < model.getColumnCount(); i++){
                excel.write(model.getColumnName(i) + "\t");
            }

            excel.write("\n");

            for(int i=0; i< model.getRowCount(); i++) {
                for(int j=0; j < model.getColumnCount(); j++) {
                    excel.write(model.getValueAt(i,j).toString()+"\t");
                }
                excel.write("\n");
            }

            excel.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods for getting selected data by different means
    ///////////////////////////////////////////////////////////////////////////

    public T getSelectedRowAsObject(String identifier) {
        try {
            for (T object : data) {
                if (invokeGetter(object, identifier) != null) {
                    if (invokeGetter(object, identifier).toString().equals(getSelectedRowDataByColNo(getColumnNoByName(identifier)))) {
                        return object;
                    }
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    public T getSelectedRowByContentAsObject(String identifier, String content) {
        try {
            for (T object : data) {
                if (invokeGetter(object,identifier) == content) {
                    return object;
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSelectedRowData() {
        String Data = null;
        int[] row = table.getSelectedRows();
        int[] column = table.getSelectedColumns();

        for (int j : row) {
            for (int k : column) {
                Data =  (String) table.getValueAt(j, k);
            }
        }

        return Data;
    }

    public String getSelectedRowDataByColNo(int columnNo) {
        String Data = null;
        int[] row = table.getSelectedRows();

        for (int j : row) {
            Data =  (String) table.getValueAt(j, columnNo);
        }

        return Data;
    }

    public int getColumnNoByName(String identifier) {
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].equals(identifier)) {
                return i;
            }
        }
        return 0;
    }

    private Object invokeGetter(Object obj, String propertyName) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PropertyDescriptor pd;
        pd = new PropertyDescriptor(propertyName, obj.getClass());
        Method getter = pd.getReadMethod();

        return getter.invoke(obj);
    }


    public static class Search {
        private JTextField jTextField;
        private TableRowSorter<TableModel> rowSorter;
        private JTable table;

        public Search(JTextField jTextField, JTable table) {
            this.jTextField = jTextField;
            this.rowSorter = new TableRowSorter<>(table.getModel());
            this.table = table;

            jTextField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    table.setRowSorter(rowSorter);
                }

                @Override
                public void focusLost(FocusEvent e) {
                    table.setRowSorter(null);
                }
            });
        }

        public void updateTable(JTable table) {
            this.table = table;
            this.rowSorter = new TableRowSorter<>(table.getModel());
            table.setRowSorter(rowSorter);
        }

        public void attachSearchHandler() {
            jTextField.getDocument().addDocumentListener(new DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    String text = jTextField.getText();

                    if (text.trim().length() == 0) {
                        rowSorter.setRowFilter(null);
                    } else {
                        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    String text = jTextField.getText();

                    if (text.trim().length() == 0) {
                        rowSorter.setRowFilter(null);
                    } else {
                        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {

                }
            });
        }

    }
}

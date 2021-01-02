package com.retailShop;

import com.retailShop.Page.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends JFrame {
    public static Dimension prefferedSize = new Dimension(1200, 1000);

    public Window(String title) throws HeadlessException {
        super(title);
    }

    public static void main(String[] args) {

        Window window = new Window("PcGarage Management Program");
        Container contentPane = window.getContentPane();
        contentPane.add(new Login(window));

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int g = JOptionPane.showConfirmDialog(window, "Are you sure ?");
                if(g == JOptionPane.OK_OPTION){
                    window.setVisible(false);
                    window.dispose();
                    System.exit(0);
                }
            }
        });
        // Main.windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setMinimumSize(Window.prefferedSize);
        window.setVisible(true);
        window.pack();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

    }
}



/*
        Access Modifier	within class	within package	outside package by subclass only	outside package
        Private	    Y	N	N	N
        Default	    Y	Y	N	N
        Protected	Y	Y	Y	N
        Public	    Y	Y	Y	Y
*/

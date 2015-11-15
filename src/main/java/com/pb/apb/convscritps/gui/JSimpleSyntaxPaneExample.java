package com.pb.apb.convscritps.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import jsyntaxpane.DefaultSyntaxKit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Новомлинов Александр
 */
public class JSimpleSyntaxPaneExample extends JFrame{
    private final JEditorPane pane = new JEditorPane();

    public JSimpleSyntaxPaneExample() {
        super("Тест");
        setSize(400,300);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        DefaultSyntaxKit.initKit();
        
        
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        c.add(new JScrollPane(pane));
        
        pane.setContentType("text/sql");
        pane.setText("function f(){}");
        JButton btn = new JButton("print text");
        btn.addActionListener((ActionEvent e) -> {
            System.out.println("pane text = "+pane.getText());
        });
        c.add(btn, BorderLayout.SOUTH);
        for(Object k : pane.getActionMap().allKeys()){
            Action a = pane.getActionMap().get(k);
            //a.setEnabled(false);
            System.out.println(""+k+"="+a);
        }
        
        pane.getInputMap().remove(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
        for(KeyStroke k : pane.getInputMap().allKeys()){
            Object a = pane.getInputMap().get(k);
            //a.setEnabled(false);
            System.out.println(""+k+"="+a);
        }
        System.out.println("inputMap"+pane.getInputMap());
    }
    
    
    public static void main(String[] args) {
        new JSimpleSyntaxPaneExample().setVisible(true);
    }
    
}

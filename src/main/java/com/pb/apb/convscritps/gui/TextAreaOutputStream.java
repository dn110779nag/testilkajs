/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pb.apb.convscritps.gui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author Новомлинов Александр
 */
public class TextAreaOutputStream extends OutputStream{
    private final JTextArea console;
    private final ByteArrayOutputStream bous = new ByteArrayOutputStream(1024);

    public TextAreaOutputStream(JTextArea console) {
        this.console = console;
    }

    
    @Override
    public void write(int b) throws IOException {
        bous.write(b);
        if(b==10){
            SwingUtilities.invokeLater(new Job(bous.toString()));
            bous.reset();
        }
    }
    
    private class Job implements Runnable{
        
        private final String text;

        public Job(String text) {
            this.text = text;
        }

        @Override
        public void run() {
            console.append(text);
        }
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pb.apb.convscritps.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.pb.apb.convscritps.IOValues;
import com.pb.apb.convscritps.RhinoScriptRunner;
import com.pb.apb.convscritps.StringUtil;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.script.ScriptException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import jsyntaxpane.DefaultSyntaxKit;

/**
 *
 * @author Новомлинов Александр
 */
public class MainFrame2 extends JFrame {

    private final JTabbedPane tabPane = new JTabbedPane();
    private final JTextArea logArea = new JTextArea();
    private final JEditorPane scriptEditor = new JEditorPane();
    private final JEditorPane inputEditor = new JEditorPane();
    private final JEditorPane outputEditor = new JEditorPane();
    private File currDir = new File("").getAbsoluteFile();

    public MainFrame2() {
        try {
            setTitle("Тестилка js");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    if (JOptionPane.showConfirmDialog(MainFrame2.this, "Вы уверены, что хотите выйти?", "Внимание!!!",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });
            prepareUI();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame2.class.getName()).log(Level.SEVERE, null, ex);
        }
        //TextAreaOutputStream in = new TextAreaOutputStream(logArea);
        //TextAreaOutputStream in = new TextAreaOutputStream(logArea);
        
        System.setErr(new PrintStream(new TextAreaOutputStream(logArea)));
        System.setOut(new PrintStream(new TextAreaOutputStream(logArea)));
        
    }

    private void prepareUI() throws IOException {

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        DefaultSyntaxKit.initKit();
        tabPane.add("JS", new JScrollPane(scriptEditor));
        tabPane.add("Input", new JScrollPane(inputEditor));
        tabPane.add("Output", new JScrollPane(outputEditor));
//        tabPane.setSelectedIndex(1);

        scriptEditor.setContentType("text/javascript");
        inputEditor.setContentType("text/json");
        outputEditor.setContentType("text/json");

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabPane, new JScrollPane(logArea));
        splitPane.setResizeWeight(0.7);
        c.add(splitPane, BorderLayout.CENTER);

        
        c.add(prepareToolBar(), BorderLayout.NORTH);
        
        

    }
    
    private JToolBar prepareToolBar() throws IOException{
        JToolBar toolBar = new JToolBar();
        JButton startBtn = new JButton(new ImageIcon(ImageIO.read(this.getClass().getResource("/icons/start.png"))));
        toolBar.add(startBtn);
        startBtn.addActionListener((ActionEvent e) -> {
            try {
                IOValues ret = new RhinoScriptRunner().runScriptFromStrings(scriptEditor.getText(), inputEditor.getText());
                JsonParser parser = new JsonParser();
                Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
                JsonElement el = parser.parse(ret.getOutputValue());
                JsonElement jerr = el.getAsJsonObject().get("stack");
                
                outputEditor.setText(gson.toJson(el));
                tabPane.setSelectedIndex(2);
                String err;
                if(jerr!=null && (err=jerr.getAsString())!=null && !err.trim().equals("")){
                    error("error: "+err);
                }
            } catch (IOException | ScriptException ex) {
                error(ex);
            }
        });
        JButton jsBtn = new JButton(new ImageIcon(ImageIO.read(this.getClass().getResource("/icons/js_64.png"))));
        toolBar.add(jsBtn);

        class OpenFileAction implements ActionListener {

            private final JEditorPane editorPane;
            private final int tabIndex;

            public OpenFileAction(JEditorPane editorPane, int tabIndex) {
                this.editorPane = editorPane;
                this.tabIndex = tabIndex;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser(currDir);
                if (fc.showOpenDialog(MainFrame2.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        File f = fc.getSelectedFile();
                        StringBuilder b = StringUtil.readFile(f);
                        editorPane.setText(b.toString());
                        tabPane.setSelectedIndex(tabIndex);
                    } catch (IOException ex) {
                        error(ex);
                    }
                }
                currDir = fc.getCurrentDirectory();
            }

        }

        jsBtn.addActionListener(new OpenFileAction(scriptEditor,0));

        JButton jsonBtn = new JButton(new ImageIcon(ImageIO.read(this.getClass().getResource("/icons/json.png"))));
        toolBar.add(jsonBtn);
        jsonBtn.addActionListener(new OpenFileAction(inputEditor,1));
        
        return toolBar;
    }

    public static void main(String[] args) {
        new MainFrame2().setVisible(true);
    }

    private void error(Exception ex) {
        error(""+ex);
        
    }
    private void error(String err) {
        JOptionPane.showMessageDialog(MainFrame2.this, err, "Ошибка!!!", JOptionPane.ERROR_MESSAGE);
        logArea.append(err+"\n");
        
    }

}

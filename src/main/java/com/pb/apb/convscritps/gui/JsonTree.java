/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pb.apb.convscritps.gui;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.io.Reader;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

/**
 * Компонент для отображения JSON в виде дерева.
 *
 * @author user
 */
public class JsonTree extends JTree {

    private JsonElement jsonElement;
    private DefaultMutableTreeNode top;
    private final SelectionListener selectionListener;
    
    public static interface SelectionListener{
        void onSelect(String value);
    }

    private static class NodeData {

        private final JsonElement jsonElement;
        private final String displayText;

        public NodeData(JsonElement jsonElement, String displayText) {
            this.jsonElement = jsonElement;
            this.displayText = displayText;
        }

        public JsonElement getJsonElement() {
            return jsonElement;
        }

        public String getDisplayText() {
            return displayText;
        }

    }

    public JsonTree(SelectionListener selectionListener) {
        init();
        this.selectionListener = selectionListener;
    }

    public void setJson(JsonElement jsonElement) {
        this.jsonElement = jsonElement;
        buildTree();
    }

    public void setJson(String json) {
        JsonParser jp = new JsonParser();
        jsonElement = jp.parse(json);
        buildTree();
    }

    public void setJson(Reader reader) {
        JsonParser jp = new JsonParser();
        jsonElement = jp.parse(reader);
        buildTree();
    }

    private void init() {
        top = new DefaultMutableTreeNode(new NodeData(null, "data"));
        setModel(new DefaultTreeModel(top));
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        putClientProperty("JTree.lineStyle", "Angled");
        DefaultTreeCellRenderer renderer
                = new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus); 
                
                if(value instanceof DefaultMutableTreeNode){
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                    NodeData d = (NodeData) node.getUserObject();
                    if(d!=null){
                        this.setText(d.getDisplayText());
                    }
                }
                
                return this;
            }
                    
        };

        setCellRenderer(renderer);
        addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node
                    = (DefaultMutableTreeNode) getLastSelectedPathComponent();
            if(node==null) {
                return;
            }
            NodeData data = (NodeData) node.getUserObject();
            if (data != null && data.getJsonElement() != null) {
                selectionListener.onSelect(new GsonBuilder().setPrettyPrinting().create().toJson(data.getJsonElement()));
            }
        });
    }

    public void buildTree() {
        top.removeAllChildren();
        DefaultTreeModel model = (DefaultTreeModel) this.getModel();
        top.setUserObject(new NodeData(jsonElement, "data"));
        model.setRoot(top);

        prepareElement(top, jsonElement);
        repaint();
        setSelectionRow(0);
    }

    private void prepareElement(DefaultMutableTreeNode parent, JsonElement el) {
        if (el.isJsonObject()) {
            JsonObject jo = el.getAsJsonObject();
            jo.entrySet().forEach(e -> {
                DefaultMutableTreeNode n;
                if (e.getValue().isJsonPrimitive()) {
                    n = new DefaultMutableTreeNode(new NodeData(e.getValue(), "" + e.getKey() + ":" + e.getValue().getAsJsonPrimitive()));
                } else {
                    n = new DefaultMutableTreeNode(new NodeData(e.getValue(), e.getKey()));
                    prepareElement(n, e.getValue());
                }
                parent.add(n);
            });

        } else if (el.isJsonArray()) {
            JsonArray array = el.getAsJsonArray();
            int i = 0;
            for (JsonElement e : array) {
                DefaultMutableTreeNode n;
                if (e.isJsonPrimitive()) {
                    n = new DefaultMutableTreeNode(new NodeData(e, "[" + (i++) + "]: " + e.getAsJsonPrimitive()));
                } else {
                    n = new DefaultMutableTreeNode(new NodeData(e, "[" + (i++) + "]: "));
                    prepareElement(n, e);
                }
                parent.add(n);
            }

        }
    }

    public static void main(String[] args) {
        JFrame testFrame = new JFrame("test");
        testFrame.setSize(800, 600);
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = testFrame.getContentPane();
        c.setLayout(new BorderLayout());
        final JsonTree t = new JsonTree(s -> System.out.println(s));
        t.setJson("{\"ttt\":\"vvv\",\"arr\":[0,1,2,3], \"o1\":{\"ov\":true, \"arr2\":[{\"ov2\":\"ttt\",\"ov3\":111}]}}");
        c.add(new JScrollPane(t));
        
        JButton btn = new JButton("json2");
        
        btn.addActionListener(e -> t.setJson("{\"ttt\":\"vvv\"}"));
        c.add(btn, BorderLayout.NORTH);
        
        
        JButton btn3 = new JButton("json3");
        
        btn3.addActionListener(e -> t.setJson("{\"ttt\":\"vvv2\",\"arr\":[0,1,2,3], \"o1\":{\"ov\":true, \"arr2\":[{\"ov2\":\"ttt\",\"ov3\":111}]}}"));
        c.add(btn3, BorderLayout.SOUTH);
        testFrame.setVisible(true);
        
        t.setJson("{\"ttt\":\"vvv\"}");
        
        t.setJson("{\"ttt\":\"vvv2\",\"arr\":[0,1,2,3], \"o1\":{\"ov\":true, \"arr2\":[{\"ov2\":\"ttt\",\"ov3\":111}]}}");

    }

}

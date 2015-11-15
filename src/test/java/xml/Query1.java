/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Новомлинов Александр
 */
public class Query1 {
    private static int i=1;
    public static void main(String[] args) throws Exception {
        InputStream is = new FileInputStream("src/test/xml/query1.xml");
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
        Set<String> set = new HashSet<>();
        NodeList list = doc.getElementsByTagName("division");
        for(int i=0;i<list.getLength(); i++){
            Element el = (Element) list.item(i);
            String refs = el.getAttribute("services_ref");
            String[] arefs = refs.split("[;]");
            set.addAll(Arrays.asList(arefs));
        }
        
        set.forEach(e -> System.out.println("i="+(i++)+"; e="+e));
        System.out.println("set="+set);
        System.out.println("set.size()="+set.size());
    }
}

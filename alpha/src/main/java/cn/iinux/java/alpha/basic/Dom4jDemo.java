package cn.iinux.java.alpha.basic;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

/**
 * Dom4j 生成XML文档与解析XML文档
 */
public class Dom4jDemo {
    @Test
    public void createXml() {
        String fileName = "a.xml";
        Document document = DocumentHelper.createDocument();
        Element employees = document.addElement("employees");
        Element employee = employees.addElement("employee");
        Element name = employee.addElement("name");
        name.setText("活这么大就没饱过");
        Element sex = employee.addElement("sex");
        sex.setText("m");
        Element age = employee.addElement("age");
        age.setText("24");
        try {
            Writer fileWriter = new FileWriter(fileName);
            XMLWriter xmlWriter = new XMLWriter(fileWriter);
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void parserXml() {
        String fileName = "a.xml";
        File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(inputXml);
            Element employees = document.getRootElement();
            for (Iterator<Element> i = employees.elementIterator(); i.hasNext(); ) {
                Element employee = i.next();
                for (Iterator<Element> j = employee.elementIterator(); j.hasNext(); ) {
                    Element node = j.next();
                    System.out.println(node.getName() + ":" + node.getText());
                }
            }
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("dom4j parserXml");
    }
}

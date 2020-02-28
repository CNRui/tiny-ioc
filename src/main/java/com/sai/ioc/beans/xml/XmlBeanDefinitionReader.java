package com.sai.ioc.beans.xml;

import com.sai.ioc.beans.AbstractBeanDefinitionReader;
import com.sai.ioc.beans.BeanDefinition;
import com.sai.ioc.beans.BeanReference;
import com.sai.ioc.beans.PropertyValue;
import com.sai.ioc.beans.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    @Override
    public void loadBeanDefinition(String location) throws Exception {
        InputStream inputStream = getResourceLoader().getResource().getInputStream();
        doLoadBeanDefinition(inputStream);
    }

    private void doLoadBeanDefinition(InputStream inputStream) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(inputStream);
        registerBeanDefinitions(document);
        inputStream.close();
    }

    private void registerBeanDefinitions(Document document) {
        Element root = document.getDocumentElement();
        parseBeanDefinition(root);
    }

    private void parseBeanDefinition(Element root) {
        NodeList nodeList = root.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                processBeanDefinition(element);
            }
        }
    }

    private void processBeanDefinition(Element element) {
        String name = element.getAttribute("name");
        String className = element.getAttribute("class");
        BeanDefinition beanDefinition = new BeanDefinition();
        processProperty(element, beanDefinition);
        beanDefinition.setBeanClassName(className);
        //更改为实现BeanDefinitionRegistry 接口
        getRegistry().registerBeanDefinition(name, beanDefinition);
    }

    private void processProperty(Element element, BeanDefinition beanDefinition) {
        NodeList propertyNode = element.getChildNodes();
        for (int i = 1; i <= propertyNode.getLength(); i++) {
            Node node = propertyNode.item(i);
            if (node instanceof Element) {
                Element propertyElement = (Element) node;
                String name = propertyElement.getAttribute("name");
                String value = propertyElement.getAttribute("value");
                if (value != null && value.length() > 0) {
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
                } else {
                    String ref = propertyElement.getAttribute("ref");
                    if (ref != null && ref.length() > 0) {
                        BeanReference beanReference = new BeanReference(ref);
                        beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
                    } else {
                        throw new IllegalArgumentException("Configuration problem: <property> element for property '"
                                + name + "' must specify a ref or value");
                    }

                }

            }
        }
    }
}

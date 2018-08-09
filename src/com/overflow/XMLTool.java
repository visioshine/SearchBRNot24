/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.overflow;

import java.util.Map;
import java.io.*;   
import java.util.*;   
import org.dom4j.*;   
import org.dom4j.io.*; 
/**
 *
 * @author Administrator
 */
public class XMLTool {
    public static Map<String,String> getConfigXML(){
        Map<String,String> conMap = new HashMap<String,String>();
        try {   
            File f = new File("config.xml");   
            SAXReader reader = new SAXReader();   
            Document doc = reader.read(f);   
            Element root = doc.getRootElement();   
            Element foo;   
            for (Iterator i = root.elementIterator("VALUE"); i.hasNext();) {   
                foo = (Element) i.next();  
                conMap.put("ADDR", foo.elementText("ADDR")); 
            }   
        } catch (Exception e) {   
            e.printStackTrace();   
        }  
        return conMap;
    }
}

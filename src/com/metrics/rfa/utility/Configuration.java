package com.metrics.rfa.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 *
 * @author morris.yang
 */
public class Configuration {

    static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Configuration.class);
    public static String configFolder = Configuration.getStringConfigPath();
    //public boolean authorize = false;
    public Document doc;
    File file;
    public String defaultPath = configFolder + "config.xml";
    public String statusPath = configFolder + "status.properties";

    
    //�ѱK�Ϊ�
    
    public static String getStringConfigPath() {
        String path = new String();
        String fileName = "path.txt";
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader bufreader = new BufferedReader(reader);
            String sentence;
            while ((sentence = bufreader.readLine()) != null) {
                path = sentence;
                sentence = null;
            }
            bufreader.close();
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return path;
    }

    public void loadPath() {
    }

    public Configuration() {
        load(defaultPath);
    }

    public Configuration(File file) {
        load(file);
    }

    public Configuration(String filePath) {
        load(filePath);
    }

    public void load(String filePath) {
        file = new File(filePath);
        load(file);
    }

    public void load(File file) {
        this.file = file;
        parse(file);
    }

    /**
     *
     * @param file
     * @return
     * @throws DocumentException
     */
    public Document parse(File file) {
        try {
            //InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"UTF8");
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding("UTF-8");
            doc = saxReader.read(file);

        } catch (DocumentException ex) {
            doc = null;
            System.out.println("Config loading error");
            logger.error("Config loading error");
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doc;
    }
}

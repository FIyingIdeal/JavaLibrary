package test.java.util;

import org.junit.Test;

import java.io.*;
import java.util.Properties;

/**
 * Created by Administrator on 2017/5/16.
 */
public class PropertiesTest1 {

    private String propertiesFile = "default.properties";
    private String path = "E:/IDEAWorkspace/Java8/src/test/java/util/default.properties";

    public Properties load(String ...propertiesFiles) {

        if (propertiesFiles.length > 0) {
            propertiesFile = propertiesFiles[0];
        }
        Properties properties = new Properties();
        //刚开始可以读取，后来读取不到，直接将文件指向了out编译目录...
        //InputStream is = this.getClass().getResourceAsStream(propertiesFile);
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(path));
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Test
    public void readJVM() {
        Properties properties = System.getProperties();
        //properties.list(System.out);
        //Properties中的forEach方法继承自HashTable
        properties.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    @Test
    public void getProperty() {
        Properties properties = load();
        System.out.println(properties.getProperty("username"));
        System.out.println(properties.getProperty("password"));
    }

    //setProperty方法只是在内存中将k-v对保存，而没有将其写入properties文件中,写入文件请参照store
    @Test
    public void setProperty() {
        Properties properties = load();
        properties.setProperty("rememberme", "true");
        System.out.println(properties.getProperty("rememberme"));   //true
        properties = load();
        System.out.println(properties.getProperty("rememberme"));   //null
    }

    @Test
    public void store() {
        Properties properties = load();
        properties.setProperty("store", "false");
        try {
            OutputStream os = new BufferedOutputStream(new FileOutputStream(path));
            properties.store(os, "add store");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        properties = load();
        System.out.println(properties.getProperty("store"));
    }
}

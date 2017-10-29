package test.java.util;

import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by Administrator on 2017/5/16.
 */
public class PropertiesTest {

    private static final String propertiesFile = "default.properties";
    private static final String path = "E:/IDEAWorkspace/Java8/src/test/java/util/default.properties";
    private static Properties properties;

    static {
        properties = new Properties();
        InputStream is = PropertiesTest.class.getResourceAsStream(propertiesFile);
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public Properties load(String ...propertiesFiles) {

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
    }*/

    @Test
    public void readJVM() {
        Properties properties = System.getProperties();
        //properties.list(System.out);
        //Properties中的forEach方法继承自HashTable
        properties.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    @Test
    public void getProperty() {
        //Properties properties = load();
        System.out.println(properties.getProperty("username"));
        System.out.println(properties.getProperty("password"));
    }

    //setProperty方法只是在内存中将k-v对保存，而没有将其写入properties文件中,写入文件请参照store
    @Test
    public void setProperty() {
        //Properties properties = load();
        properties.setProperty("rememberme", "true");
        System.out.println(properties.getProperty("rememberme"));   //true
        //properties = load();
        System.out.println(properties.getProperty("rememberme"));   //null
    }

    @Test
    public void store() {
        //Properties properties = load();
        properties.setProperty("store", "true");
        try {
            OutputStream os = new BufferedOutputStream(new FileOutputStream(path));
            URL url = Properties.class.getResource(propertiesFile);
            System.out.println(url.toURI());
            //File file = new File();
            //System.out.println(file.getAbsolutePath());
            properties.store(os, "add store");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {}
        //properties = load();
        System.out.println(properties.getProperty("store"));
    }
}

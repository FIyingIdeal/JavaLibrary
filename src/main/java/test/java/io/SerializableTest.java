package test.java.io;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.java.io.bean.Child;
import test.java.io.bean.Parent;
import test.java.io.bean.Player;

import java.io.*;
import java.util.Base64;

import test.java.io.bean.Family;

/**
 * @author yanchao
 * @date 2017/2/6
 */
public class SerializableTest {

    private static final Logger logger = LoggerFactory.getLogger(SerializableTest.class);

    /**
     * 序列化一个对象到文件中
     */
    @Test
    public void serializeTest() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("serialize.txt"))) {
            Player player = new Player("yanchao", 25, 'F');
            oos.writeObject(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 序列化一个对象到String
     * @see <a href="https://bugs.java.com/bugdatabase/view_bug.do?bug_id=4968673">Java BUG</a>
     * @see <a href="https://blog.csdn.net/woshisangsang/article/details/78470416"/>  这篇文章指出，如果使用ByteArrayOutputStream#toString("ISO-8859-1")及String#getBytes("ISO-8859-1")时可以序列化和反序列化成功
     * @see <a href="https://blog.csdn.net/cubesky/article/details/38753861"/>  这篇文章最后指出，如果使用Base64编解码也是可以成功的
     */
    @Test
    public void serializeObjectToString() {
        Player player = new Player("yanchao", 25, 'F');
        try ( ByteArrayOutputStream baos = new ByteArrayOutputStream();
              ObjectOutputStream oos = new ObjectOutputStream(baos) ) {
            oos.writeObject(player);
            // 1. 使用ISO-8859-1编码
            // String serializeString = baos.toString("ISO-8859-1");
            // 2. 使用Base64编码
            String serializeString = Base64.getEncoder().encodeToString(baos.toByteArray());
            logger.info("Player serialize to String is : {}", serializeString);
            // 转为String的时候必须制定编码集为“ISO-8859-1”，并且反序列化的时候也需要将制定，否则的话反序列化失败
            deserializeStringToObject(serializeString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字符串反序列化为Object
     * @see <a href="https://bugs.java.com/bugdatabase/view_bug.do?bug_id=4968673">Java BUG</a>
     * @see <a href="https://blog.csdn.net/woshisangsang/article/details/78470416"/>  这篇文章指出，如果使用ByteArrayOutputStream#toString("ISO-8859-1")及String#getBytes("ISO-8859-1")时可以序列化和反序列化成功
     * @see <a href="https://blog.csdn.net/cubesky/article/details/38753861"/>  这篇文章最后指出，如果使用Base64编解码也是可以成功的
     * @param str
     */
    public void deserializeStringToObject(String str) throws UnsupportedEncodingException {
        // 1. 使用ISO-8859-1编码
        // byte[] deserializeBytes = str.getBytes("ISO-8859-1");
        // 2. 使用Base64解码
        byte[] deserializeBytes = Base64.getDecoder().decode(str);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(deserializeBytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            Player player = (Player) ois.readObject();
            logger.info("String deserialize to Player is : {}", player);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中反序列化一个对象
     */
    @Test
    public void deserializeTest() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("serialize.txt"))) {
            Player player = (Player)ois.readObject();
            System.out.println(player);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 序列化一个父类不可序列化的可序列化子类
     * 此时父类的Filed将不会被序列化为二进制保存
     */
    @Test
    public void serializeChild() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("serializeChild.txt"))) {
            Child child = new Child("parentName", 55, "childName", 25);
            oos.writeObject(child);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化一个父类不可序列化的可序列化子类
     * 此时需要父类具有一个无参构造器，否则将抛出InvalidClassException异常
     * 如果父类是可序列化，则无需无参构造器
     */
    @Test
    public void deserializeChild() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("serializeChild.txt"))) {
            Child child = (Child)ois.readObject();
            System.out.println(child);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 序列化公共类
     * @param fileName
     * @param object
     */
    public void serialize(String fileName, Object object) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(fileName))) {
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化公共类
     * @param fileName
     * @return
     */
    /*public Object deserialize(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(fileName))) {
            return ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
   }*/


   public <T> T deserialize(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(fileName))) {
            return (T) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
   }

    /**
     * 序列化具有对象引用的可序列化类
     * 引用类型必须是可序列化的，否则引用的类也是不可序列化的
     */
    @Test
    public void serializeFamily() {
        Parent parent = new Parent("parentName", 55);
        Child child = new Child("childName", 25);
        serialize("serializeFamily.txt", new Family("familyName", parent, child));
    }

    /**
     * 反序列化具有对象引用的可序列化类
     */
    @Test
    public void deserializeFamily() {
        Family family = deserialize("serializeFamily.txt");
        System.out.println(family);
    }

    /**
     * 序列化多个对象
     * 当程序试图序列化一个对象时，程序将先检查该对象是否已经被序列化过，
     * 只有该对象从未（在本次虚拟中机）被序列化过，系统才会将该对象转换成字节序列并输出；
     * 如果某个对象已经序列化过，程序将只是直接输出一个序列化编号，而不是再次重新序列化该对象
     */
    @Test
    public void serializeMultObject() {
        Parent parent = new Parent("parentName", 55);
        Child child = new Child("childName", 25);
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("serializeMultObject.txt"))) {
            oos.writeObject(parent);
            oos.writeObject(child);
            oos.writeObject(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化多个对象
     */
    @Test
    public void deserializeMultObject() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("serializeMultObject.txt"))) {
            Parent parent = (Parent)ois.readObject();
            Child child = (Child) ois.readObject();
            Parent parent1 = (Parent)ois.readObject();
            System.out.println(child);
            System.out.println(parent);
            // true
            System.out.println(parent == parent1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 当使用Java序列化机制序列化可变对象时，只有第一次调用WriteObject()方法来
     * 输出对象时才会将对象转换成字节序列，并写入到ObjectOutputStream；
     * 即使在后面程序中，该对象的实例变量发生了改变，再次调用WriteObject()方法
     * 输出该对象时，改变后的实例变量也不会被输出
     */
    @Test
    public void reSerialize() {
        Parent parent = new Parent("name", 53);
        String fileName = "reSerialize.txt";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            oos.writeObject(parent);
            parent.setParentName("first name");
            // 修改后重新序列化
            oos.writeObject(parent);

            Parent desParent = (Parent)ois.readObject();
            // name
            System.out.println(desParent.getParentName());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

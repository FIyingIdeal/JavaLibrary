package test.javax.xml.bind;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

/**
 * Created by Administrator on 2017/3/22.
 */
public class MarshallerTest {

    public static String bean2XML(Class clazz, Object obj) throws JAXBException {
        String xml = null;
        JAXBContext context = JAXBContext.newInstance(clazz);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        Writer writer = new StringWriter();
        marshaller.marshal(obj, writer);
        xml = writer.toString();
        return xml;
    }

    public static Object xml2Bean(Class clazz, String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(new StringReader(xml));
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("birthdate", new Date());
        map.put("position", "beijign");
        User user = new User("zhangsan", 22, 'F', Arrays.asList("admin", "user"), map);
        String xml = bean2XML(User.class, user);
        System.out.println(xml);

        User xmlUser = (User)xml2Bean(User.class, xml);
        System.out.println(xmlUser);

    }
}

@XmlRootElement(name = "user")  //@XmlRootElement 是必须的
@XmlType(propOrder = {"name", "age", "sex", "roles", "map"})
class User {
    private String name;
    private int age;
    private char sex;
    private List<String> roles;

    private Map<String, Object> map;

    public User() {}        //无参构造器是必须的

    public User(String name, int age, char sex, List<String> roles, Map<String, Object> map) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.roles = roles;
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    @XmlElementWrapper(name = "roles")
    @XmlElement(name = "role")          //该标签在字段上使用报错
    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @XmlElement(name = "other")
    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", roles=" + roles +
                ", map=" + map +
                '}';
    }
}

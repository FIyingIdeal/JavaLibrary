package test.java.io.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/6.
 */
//可序列化的子类
public class Child extends Parent implements Serializable {

    private String childName;
    private int childAge;

    public Child(String childName, int childAge) {
        this.childName = childName;
        this.childAge = childAge;
    }

    public Child(String parentName, int parentAge, String childName, int childAge) {
        super(parentName, parentAge);
        this.childName = childName;
        this.childAge = childAge;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public int getChildAge() {
        return childAge;
    }

    public void setChildAge(int childAge) {
        this.childAge = childAge;
    }

    @Override
    public String toString() {
        return //super.toString() +
                "Child{" +
                "childName='" + childName + '\'' +
                ", childAge=" + childAge +
                '}';
    }
}

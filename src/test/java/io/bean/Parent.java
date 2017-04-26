package test.java.io.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/6.
 */
//不可序列化父类
public class Parent
        implements Serializable
{
    private String parentName;
    private int parentAge;

    public Parent() {}

    public Parent(String parentName, int parentAge) {
        this.parentName = parentName;
        this.parentAge = parentAge;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getParentAge() {
        return parentAge;
    }

    public void setParentAge(int parentAge) {
        this.parentAge = parentAge;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "parentName='" + parentName + '\'' +
                ", parentAge=" + parentAge +
                '}';
    }
}

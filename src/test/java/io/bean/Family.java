package test.java.io.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/6.
 */
public class Family implements Serializable {
    private String familyName;
    private Parent parent;
    private Child child;

    public Family(String familyName, Parent parent, Child child) {
        this.familyName = familyName;
        this.parent = parent;
        this.child = child;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "Family{" +
                "familyName='" + familyName + '\'' +
                ", parent=" + parent +
                ", child=" + child +
                '}';
    }
}

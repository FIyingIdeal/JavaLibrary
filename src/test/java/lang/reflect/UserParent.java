package test.java.lang.reflect;

/**
 * Created by Administrator on 2017/3/3.
 */
public class UserParent {
    private String parentName;
    private int parentAge;
    private char parentSex;
    public String parentPublicField;

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

    public char getParentSex() {
        return parentSex;
    }

    public void setParentSex(char parentSex) {
        this.parentSex = parentSex;
    }
}

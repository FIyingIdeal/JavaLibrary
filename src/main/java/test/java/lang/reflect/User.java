package test.java.lang.reflect;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/3.
 */
public class User extends UserParent implements Serializable {
    public static String CLASS_NAME;
    private String userName;
    private int userAge;
    private char userSex;

    public User() {}

    public User(String userName, int userAge, char userSex) {
        this.userName = userName;
        this.userAge = userAge;
        this.userSex = userSex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public char getUserSex() {
        return userSex;
    }

    public void setUserSex(char userSex) {
        this.userSex = userSex;
    }

    private void privatemethod() {
        System.out.println("private method");
    }

    protected void protectedmethod() {
        System.out.println("protected method");
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userAge=" + userAge +
                ", userSex=" + userSex +
                '}';
    }
}

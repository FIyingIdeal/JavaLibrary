package test.java.lang.EnumTest;

import org.junit.Test;

/**
 * Created by Administrator on 2017/5/31.
 */
public class EnumTest {

    /**
     * name() 方法用用来将一个enum实例转换为字符串
     */
    @Test
    public void name() {
        // Monday
        System.out.println(WeekEnum.Monday.name());
        // class java.lang.String
        System.out.println(WeekEnum.Monday.name().getClass());
    }

    /**
     * valueOf(Class<T extends Enum<T>> your_enum, String name)方法是用来从指定的Enum your_enum中获取value值与name一致的enum实例
     */
    @Test
    public void valueOf() {
        System.out.println(Enum.valueOf(WeekEnum.class, "Monday"));
        System.out.println(Enum.valueOf(WeekEnum.class, "Monday").getCn_name());
    }

    @Test
    public void ordinal() {
        System.out.println(WeekEnum.Monday.ordinal());
    }
}

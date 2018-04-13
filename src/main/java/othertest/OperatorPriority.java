package othertest;

import org.junit.Test;

/**
 * @author yanchao
 * @date 2018/4/9 10:21
 * @function 运算符优先级测试
 */
public class OperatorPriority {

    @Test
    public void test1() {
        System.out.println(! false || true);  // true    !优先级高于||
        System.out.println(! true && false);  // false   !优先级高于&&
        System.out.println(false || 2 > 1);   // true    >等优先级高于||等
        System.out.println(true || true && false);  // true   &&优先级高于||
    }
}

package JVMBook.chapter10;

/**
 * @author yanchao
 * @date 2017/10/20 14:49
 */
public class AutoBoxTest {

    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(g == (a + b));
        System.out.println(g.equals(a + b));
        System.out.println(g.equals(3));
        System.out.println(g.equals(3L));

    }
}

/**
 * 运行结果：
 true
 false
 true
 true
 true
 false
 */

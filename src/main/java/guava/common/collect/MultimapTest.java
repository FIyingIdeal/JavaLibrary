package guava.common.collect;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.Test;
import utils.PrintUtil;

/**
 * @author yanchao
 * @date 2018/12/8 17:45
 */
public class MultimapTest {

    @Test
    public void test() {
        Multimap<Integer, String> multimap = ArrayListMultimap.create();
        multimap.put(1,"1");
        multimap.put(1,"one");
        multimap.put(1,"first");
        multimap.put(2, "2");
        multimap.put(2, "two");
        multimap.put(2, "second");
        PrintUtil.println(multimap.get(1));
    }

    @Test
    public void nontime() {
        PrintUtil.println(System.currentTimeMillis());
        PrintUtil.println(System.nanoTime());
    }
}

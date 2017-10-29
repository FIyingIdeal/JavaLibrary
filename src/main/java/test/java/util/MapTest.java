package test.java.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/16.
 */
public class MapTest {

    private Map<String, Object> map = new HashMap<>();

    {
        map.put("first", "first");
        map.put("second", "second");
        map.put("third", "third");
        map.put("3", 3);
        map.put("4", 3);
    }

    /*public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put
    } */

    @Test
    public void values() {
        System.out.println(map.values());
    }
}

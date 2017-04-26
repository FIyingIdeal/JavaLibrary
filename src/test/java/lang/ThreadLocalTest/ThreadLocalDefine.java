package test.java.lang.ThreadLocalTest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/25.
 *
 * ThreadLocal定义在单独的类中声明为static final变量，并提供获取当前线程的ThreadLocal中保存变量的方法
 */
public class ThreadLocalDefine {

    private static final ThreadLocal<Map<String, Object>> mapThreadLocal = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            Map<String, Object> map = new HashMap<>();
            map.put("maxExceptionCount", 0);
            map.put("isError", false);
            map.put("isWrite", false);
            return map;
            //return super.initialValue();
        }
    };

    /**
     * 获取当前线程TreadLocal中变量
     * @return
     */
    public static Map<String, Object> getThreadLocal() {
        return mapThreadLocal.get();
    }

    public static void setThreaLocal(Map<String, Object> map) {
        mapThreadLocal.set(map);
    }
}

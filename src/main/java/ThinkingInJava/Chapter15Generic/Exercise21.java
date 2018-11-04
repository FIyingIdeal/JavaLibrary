package ThinkingInJava.Chapter15Generic;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author yanchao
 * @date 2018/10/22 11:12
 */
public class Exercise21 {

    private Map<String, Class<?>> map;

    public void addType(String typeName, Class<?> kind) {
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }
        map.put(typeName, kind);
    }

    public Object createNew(String typeName) throws IllegalAccessException, InstantiationException {
        if (Objects.isNull(map) || !this.map.containsKey(typeName)) {
            throw new IllegalArgumentException(typeName + "未与任何Class关联，使用addType(String, Class)进行关联");
        }
        Class<?> kind = map.get(typeName);
        return kind.newInstance();
    }

    public <T> T createNew(Class<T> type) throws IllegalAccessException, InstantiationException {
        return type.newInstance();
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Exercise21 exercise = new Exercise21();
        exercise.addType("Exercise21", Exercise21.class);
        System.out.println(exercise.createNew("Exercise21"));
        // true
        System.out.println(exercise.createNew("Exercise21") instanceof Exercise21);
        System.out.println(exercise.createNew(Exercise21.class));
    }
}

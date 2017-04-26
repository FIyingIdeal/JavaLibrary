package test.java.util.generic;

/**
 * Created by Administrator on 2017/3/14.
 */
public class Plate<T> {
    private T item;
    public Plate(T t) {
        this.item = t;
    }
    public void set(T t) {
        this.item = t;
    }
    public T get() {
        return this.item;
    }
}

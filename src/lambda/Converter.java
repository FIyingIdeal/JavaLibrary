package lambda;

/**
 * Created by Administrator on 2017/3/3.
 * 定义一个可用于lambda表达式的接口，该接口必须只有一个方法
 * 可使用@FunctionalInterface注解来对接口进行检验，如果定义了多个方法的话会报错
 */
@FunctionalInterface
public interface Converter<F, T> {
    T convert(F from);
}

package test.java.lang.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2017/6/1.
 * 自定义注解
 * {@code @Target} 用于说明Annotation所修饰的对象的范围，其取值范围在{@link java.lang.annotation.ElementType}的{@code enum}中定义:
 *         CONSTRUCTOR : 描述构造器
 *         FIELD : 描述域/属性
 *         LOCAL_VARIABLE : 描述局部变量
 *         METHOD : 描述方法
 *         PACKAGE : 描述包
 *         PARAMETER : 描述参数
 *         TYPE : 描述类、接口（包括注解类型）或enum声明
 *
 * {@code @Retention} 用于说明该Annotation被保留的策略，其取值范围在{@link java.lang.annotation.RetentionPolicy}的{@code enum}中定义：
 *         SOURCE : 在源文件中有效（源文件中保留）
 *         CLASS : 在class文件中有效（class保留）
 *         RUNTIME : 在运行时有效（运行时保留）
 *
 * {@code @Document} 用于描述其他类型的Annotation应该被作为被标注的程序成员的公共API，因此可以被例如javadoc此类的工具文档化
 *
 * {@code @Inherited} 是一个标记注解，说明某个被标注的类型是被继承的。如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个annotation将被用于该class的子类
 *      注意：@Inherited annotation类型是被标注过的class的子类所继承。类并不从它所实现的接口继承annotation，方法并不从它所重载的方法继承annotation。
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodParamaterAnnotation {
    public String value() default "";
}

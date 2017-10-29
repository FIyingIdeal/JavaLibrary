package test.java.lang.reflect.TypeTest;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2017/6/12.
 * Type介绍：
 *   Java引入Type是为了统一与泛型有关的类型和原始类型Class，故Type是所有类型的父接口，类型包括：
 *     1、原始类型（raw types，对应Class）；
 *     2、基本（原生）类型（primitive types，对应Class）；
 *     3、参数化类型（parameterized types，对应ParameterizedType）；
 *     4、类型变量（type variables，对应TypeVariable）；
 *     5、数组类型（array types，对应GenericArrayType）；
 *   它的子接口有：ParameterizedType, TypeVariable<D>, GenericArrayType, WildcardType
 *   实现类有：Class
 *
 * 参考：
 *     http://loveshisong.cn/%E7%BC%96%E7%A8%8B%E6%8A%80%E6%9C%AF/2016-02-16-Type%E8%AF%A6%E8%A7%A3.html
 *     http://www.jianshu.com/p/1f608fd05e20
 */
public class TypeTest <K extends Comparable<String> & Serializable, V> {

    private Map<String, Object> map;
    private String str;
    private Map<String, List<String>> complexMap;

    //ParameterizedType是指具体的泛型类型
    //{@link ParameterizedType#getRawType} 返回承载该泛型信息的对象
    @Test
    public void parameterizedTypeTest() {
        try {
            Field strField = TypeTest.class.getDeclaredField("str");
            System.out.println(strField.getGenericType() instanceof TypeVariable);  //false
            Field field = TypeTest.class.getDeclaredField("map");
            Type type = field.getGenericType();
            System.out.println(type); //java.util.Map<java.lang.String, java.lang.String>
            System.out.println(type instanceof ParameterizedType);  //true
            ParameterizedType parameterizedType = (ParameterizedType) type;

            //ParameterizedType#getRawType()： 返回承载该泛型信息的对象
            System.out.println(parameterizedType.getRawType()); //interface java.util.Map

            //ParameterizedType#getActualTypeArguments()：返回实际泛型类型列表（数组形式）
            System.out.println("getActyalTypeArguments() : ");
            Stream.of(parameterizedType.getActualTypeArguments()).forEach(System.out::println);
            //class java.lang.String
            //class java.lang.Object

            //getOwnerType():Returns a Type object representing the type that this type is a member of.
            //For example, if this type is O<T>.I<S>, return a representation of O<T>. 类似于Map.Entry
            System.out.println(parameterizedType.getOwnerType());   //null

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private K key;
    private V value;

    //TypeVariable:类型变量, 范型信息在编译时会被转换为一个特定的类型, 而TypeVariable就是用来反映在JVM编译该泛型前的信息.
    //1.类型变量在定义的时候只能使用extends进行(多)边界限定, 不能用super;
    //2.为什么边界是一个数组? 因为类型变量可以通过&进行多个上边界限定，因此上边界有多个
    @Test
    public void typeVariableTest() throws Exception {
        Field keyField = TypeTest.class.getDeclaredField("key");
        Field valueField = TypeTest.class.getDeclaredField("value");

        Assert.assertTrue(keyField.getGenericType() instanceof TypeVariable);
        Assert.assertTrue(valueField.getGenericType() instanceof TypeVariable);

        TypeVariable keyType = (TypeVariable) keyField.getGenericType();
        TypeVariable valueType = (TypeVariable) valueField.getGenericType();

        //getName(): 获取在定义时的类型名字
        System.out.println(keyType.getName());     //K
        System.out.println(valueType.getName());   //V

        //getGenericDeclaration(): 获取声明该类型变量的实体
        System.out.println(keyType.getGenericDeclaration());    //class test.java.lang.reflect.TypeTest.TypeTest
        System.out.println(valueType.getGenericDeclaration());  //class test.java.lang.reflect.TypeTest.TypeTest

        //getBounds(): 获取类型变量的上边界，若未声明上边界默认为Object
        Stream.of(keyType.getBounds()).forEach(System.out::println);    //java.lang.Comparable<java.lang.String>  interface java.io.Serializable
        Stream.of(valueType.getBounds()).forEach(System.out::println);  //class java.lang.Object

    }

    //GenericArrayType: 泛型数组，组成数组的元素中有泛型则实现了该接口；它的组成元素是ParameterizedType或TypeVariable类型
    @Test
    public void genericArrayType() {
        Method method = GenericArrayTypeTestClass.class.getDeclaredMethods()[0];
        Type[] types = method.getGenericParameterTypes();
        Stream.of(types).forEach(type -> System.out.println(type.getTypeName() + "  " + (type instanceof GenericArrayType)));
        //java.util.List<java.lang.String>[]  true      //List<String>[]组成元素List<String>是ParameterizedType类型，且是数组
        //T[]  true                                     //T[]组成元素T是TypeVariable类型，且是数组
        //java.util.List<java.lang.String>  false       //List<String>组成元素是ParameterizedType类型，但不是数组，故为false
        //java.lang.String[]  false                     //String[]是数组，但组成元素String是普通对象，没有泛型
        //int[]  false                                  //int[]是数组，但组成元素int是原生类型，也没有泛型

        //getGenericComponentType(): 返回数组的组成对象，即被JVM编译后的实际对象???
        GenericArrayType genericArrayType = (GenericArrayType) types[1];
        System.out.println(genericArrayType.getGenericComponentType());     //T

        GenericArrayTypeTestClass<String> test = new GenericArrayTypeTestClass<String>();
        Method method1 = test.getClass().getDeclaredMethods()[0];
        GenericArrayType genericArrayType1 = (GenericArrayType) method1.getGenericParameterTypes()[1];
        System.out.println(genericArrayType1.getGenericComponentType());    //T  ??? 还是T  //TODO
    }

    //WildcardType 该接口表示通配符泛型，比如 ? extends Number 和 ? super Integer。它有两个方法：
    //  1. Type[] getUpperBounds():获取泛型变量的上界；
    //  2. Type[] getLowerBounds():获取泛型变量的下界；
    private List<? extends Number> a;
    private List<? super String> b;
    @Test
    public void wildcardTypeTest() throws Exception {
        Field fieldA = TypeTest.class.getDeclaredField("a");
        Field fieldB = TypeTest.class.getDeclaredField("b");

        Assert.assertTrue(fieldA.getGenericType() instanceof ParameterizedType);
        Assert.assertTrue(fieldB.getGenericType() instanceof ParameterizedType);

        //取泛型类型
        ParameterizedType pTypeA = (ParameterizedType) fieldA.getGenericType();
        ParameterizedType pTypeB = (ParameterizedType) fieldB.getGenericType();

        Assert.assertTrue(pTypeA.getActualTypeArguments()[0] instanceof WildcardType);
        Assert.assertTrue(pTypeB.getActualTypeArguments()[0] instanceof WildcardType);

        //从泛型类型中获取通配符类型
        WildcardType wildcardTypeA = (WildcardType) pTypeA.getActualTypeArguments()[0];
        WildcardType wildcardTypeB = (WildcardType) pTypeB.getActualTypeArguments()[0];

        System.out.println(wildcardTypeA.getUpperBounds()[0]);      //class java.lang.Number
        //System.out.println(wildcardTypeA.getLowerBounds()[0]);    //a没有下界，取下界会抛异常java.lang.ArrayIndexOutOfBoundsException
        System.out.println(wildcardTypeB.getUpperBounds()[0]);      //class java.lang.Object   上界默认为Object
        System.out.println(wildcardTypeB.getLowerBounds()[0]);      //class java.lang.String
        System.out.println(wildcardTypeA);      //? extends java.lang.Number
        System.out.println(wildcardTypeB);      //? super java.lang.String
    }
}

class GenericArrayTypeTestClass<T> {
    public void show(List<String>[] pTypeArray, T[] vTypeArray, List<String> list,
                     String[] array, int[] ints) {}
}
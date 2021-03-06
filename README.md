# Java8Library
基于JDK1.8的java基础知识记录及代码测试，代码中包含有一些简要但很重要的注释。

代码目录说明：

1. keyword ：java关键字的测试；

2. test ： 该包是为了避免与jdk包结构冲突而添加的最外层包，内部是遵循jdk包结构进行分包的，但有一些例外（后期会进行优化）；

         test.barcode : 生成二维码的测试；
         
         test.java.initTest : java类初始化顺序的测试，如静态代码块、动态代码块及构造函数的执行顺序等；
         
         test.java.InnerClassTest : 针对内部类（静态内部类、非静态内部类）的测试；
   
3. NewFeatures.lambda : java8的重要特性 -- lambda表达式的测试；

4. NewFeatures.streamtest : 同样是java8的重要特性 -- Stream相关测试；

5. NewFeatures.trywithresource : jdk1.7开始支持的新的异常处理机制，简单说就是try-catch-finally的智能简化

6. ...其他包待定

运行说明：

1. 由于该测试案例为了避免每个类中写main方法的麻烦，且测试各个方法时的更于麻烦，故大部分代码是基于junit的单元测试，这样只需在需要测试的方法上右击“Run ...”即可只运行该方法查看效果，但需要手动添加junit的依赖包。部分旧代码依然采用的main测试方法，可直接"Run main"；

2.  ~~test.barcode包含有针对二维码的测试，如果要运行需要额外手动添加两个jar包：hamcrest-all-1.3.jar 和 zxing-core-3.2.1.jar，这两个包没有什么必然的联系，只是生成二维码的两种方式所需的不同的jar包；~~ 2017-10-29将项目改为maven项目，相关依赖已经添加到pom文件中；



其他：

1. JDK1.8，本测试案例部分是java8的新特性，所以需要JDK1.8的支持；

2. API...很重要；

3. 一个顺手的编辑器~ 当然这不是必须的，IDE是为了提升编码效率的，但如果你是新手的话更多的是需要对常用方法的了解和记忆，记事本就可满足你的需要 ^-^。

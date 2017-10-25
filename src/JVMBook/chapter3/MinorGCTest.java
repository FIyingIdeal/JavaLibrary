package JVMBook.chapter3;

/**
 * @author yanchao
 * @date 2017/10/25 16:18
 * @function Java内存分配策略--对象优先分配到Eden区，如果Eden区没有足够的空间进行分配，虚拟机将发起一次Minor GC
 *
 * 代码来源：《深入理解Java虚拟机 JVM高级特性与最佳实践》 第二版 P93
 *
 * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * 设置Java堆大小为20M，且不可扩展，其中10M分配给新生代，剩下10M分配给老年代，新生代中Eden区与Survivor区空间比例为8:1（两个Survivor区8:1:1）
 * -XX:+PrintGCDetails 参数是要求虚拟机在发生垃圾收集行为时打印内存回收日志，并在进程退出时输出当前的内存各区域分配情况
 */
public class MinorGCTest {

    private static final int _1MB = 1024* 1024;

    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        testAllocation();
    }
}

/**
 * JDK1.8.0_66运行结果（前边带有*的是个人添加的输出说明，而不是运行输出）：
 [GC (Allocation Failure) [PSYoungGen: 6275K->728K(9216K)] 6275K->4832K(19456K), 0.0023755 secs] [Times: user=0.00 sys=0.00, real=0.02 secs]
 * GC日志说明： [GC发生区域名称（此处可知为新生代）: GC前该内存区域已使用容量 -> GC后该区域已使用容量(该区域总容量)] GC前Java堆已使用容量 -> GC后Java堆已使用容量(Java堆总容量)
 * 以下为使用 -XX:+PrintGCDetails 参数在进程退出的时候输出的当前内存各区域分配情况
 Heap
   PSYoungGen      total 9216K, used 7164K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     eden space 8192K, 78% used [0x00000000ff600000,0x00000000ffc490a8,0x00000000ffe00000)
     from space 1024K, 71% used [0x00000000ffe00000,0x00000000ffeb6030,0x00000000fff00000)
     to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
   ParOldGen       total 10240K, used 4104K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     object space 10240K, 40% used [0x00000000fec00000,0x00000000ff002020,0x00000000ff600000)
   Metaspace       used 3211K, capacity 4496K, committed 4864K, reserved 1056768K
     class space    used 350K, capacity 388K, committed 512K, reserved 1048576K

 * 从运行结果中可知：新生代收集器是Parallel Scavenge，老年代收集器是Parallel Old。
 * 在执行testAllocation()中为allocation4分配内存的时候发生一次Minor GC。因为给allocation4分配内存的时候，发现Eden区已被占用6M（一共8M），
 * 剩余空间已不足以分配allocation4所需的4M内存，因此发生Minor GC。GC期间虚拟机发现已有的3个2M大小的对象全部无法放入Survivor区（只有1M），
 * 所以只好通过分配担保机制提前转移到老年代去。
 */
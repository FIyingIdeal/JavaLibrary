package test.java.util.concurrent.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Administrator on 2017/3/3.
 */
public class MyRecursiveTask extends RecursiveTask<Long> {

    private long workLoad;

    public MyRecursiveTask(long workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected Long compute() {
        if (this.workLoad > 16) {
            System.out.println("splitting workload : " + this.workLoad);
            List<MyRecursiveTask> subtasks = new ArrayList<>();
            subtasks.addAll(createSubTasks());

            for (MyRecursiveTask subtask : subtasks) {
                subtask.fork();
            }

            long result = 0;

            for (MyRecursiveTask subtask : subtasks) {
                result += subtask.join();
            }
            return result;
        } else {
            System.out.println("Doing workLoad myself: " + this.workLoad);
            return this.workLoad * 3;
        }
    }

    private List<MyRecursiveTask> createSubTasks() {
        List<MyRecursiveTask> substaks = new ArrayList<>();
        MyRecursiveTask subtask1 = new MyRecursiveTask(this.workLoad / 2);
        MyRecursiveTask subtask2 = new MyRecursiveTask(this.workLoad / 2);
        substaks.add(subtask1);
        substaks.add(subtask2);
        return substaks;
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);
        //如果是奇数个任务的话，这样的分配任务（createSubTasks()）会出现问题
        MyRecursiveTask task = new MyRecursiveTask(23);
        System.out.println(task.invoke());
        //        splitting workload : 23
        //        Doing workLoad myself: 11
        //        Doing workLoad myself: 11
        //        66
    }

}

package test.java.util.concurrent.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by Administrator on 2017/3/3.
 */
public class MyRecursiveAction extends RecursiveAction {

    private long workLoad = 0;

    public MyRecursiveAction(long workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected void compute() {

        if (this.workLoad > 16) {
            System.out.println("splitting workload : " + this.workLoad);
            List<MyRecursiveAction> subtasks = new ArrayList<>();

            subtasks.addAll(createSubTasks());
            for (RecursiveAction subtask : subtasks) {
                subtask.fork();
            }
        } else {
            System.out.println("Doing workLoad myself: " + this.workLoad);
        }

    }

    private List<MyRecursiveAction> createSubTasks() {
        List<MyRecursiveAction> subtasks = new ArrayList<>();
         MyRecursiveAction action1 = new MyRecursiveAction(this.workLoad / 2);
         MyRecursiveAction action2 = new MyRecursiveAction(this.workLoad / 2);
        subtasks.add(action1);
        subtasks.add(action2);
        return subtasks;
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);
        MyRecursiveAction action = new MyRecursiveAction(24);
        pool.invoke(action);
    }
}

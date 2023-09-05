package com.mycompany.helloworld.basic;

import lombok.Data;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

public class PBQStudy {

    @Data
    static class MyTask implements Comparable<MyTask> {
        private int priority = 0;

        private String taskName;

        @Override
        public int compareTo(MyTask o) {
            if (this.priority > o.getPriority()) {
                return 1;
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        PriorityBlockingQueue<MyTask> queue = new PriorityBlockingQueue<MyTask>();
        Random random = new Random();
        //往队列中放是个任务，从TaskName是按照顺序放进去的，优先级是随机的
        for (int i = 1; i < 11; i++) {
            MyTask task = new MyTask();
            task.setPriority(random.nextInt(10));
            task.setTaskName("taskName" + i);
            queue.offer(task);
        }

        //从队列中取出任务，这里是按照优先级去拿出来的,相当于是根据优先级做了一个排序
        while (!queue.isEmpty()) {
            MyTask pollTask = queue.poll();
            System.out.println(pollTask.toString());
        }

    }

}

package Simulation;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable { //this is a QUEUE thread
    private int serverID;
    private BlockingQueue<Task> tasks;
    private boolean running = true;
    private int totalWaitedTime = 0;
    private AtomicInteger waitingPeriod; //equal to the sum of processing times of clients!

    public Server(int maxNoTasks, int serverID) {
        waitingPeriod = new AtomicInteger(0);
        this.serverID = serverID;
        tasks = new ArrayBlockingQueue<>(maxNoTasks);
    }

    @Override
    public void run() {
        while (running) {
/*            synchronized (this) {
                while (tasks.isEmpty() && running) {
                    try {
                        wait();
                    } catch (InterruptedException ignored) {
                    }
                }
            }
            try {
                Task task = tasks.peek();
                Thread.sleep((task.getProcessingTime()) * 1000);
                tasks.take();
                totalWaitedTime += task.getWaitingTime();
            } catch (InterruptedException ignored) {
            }*/
                if (tasks.isEmpty()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    try {
                        Task task = tasks.peek();
                        Thread.sleep((task.getProcessingTime() - 1) * 1000);
                        tasks.take() ;
                        if(!tasks.isEmpty()) {
                            task = tasks.peek();
                            task.incrementProcessingTime();
                        }
                        Thread.sleep(1000);

                    } catch (InterruptedException ignored) {}
                }
        }
    }

    public void updateWaitingTime() {
        waitingPeriod.getAndDecrement();
    }

    public void stop() {
        this.running = false;
    }

    public void addTask(Task task) {
        tasks.offer(task);
        waitingPeriod.getAndAdd(task.getProcessingTime());
    }


    public Task[] getTasks() {
        if (tasks.isEmpty()) return null;
        Task[] taskArray = new Task[tasks.size()];
        int i = 0;
        for (Task task : tasks) {
            taskArray[i] = task;
            i++;
        }
        return taskArray;
    }

    public int getWaitingPeriod() {
        return waitingPeriod.intValue();
    }

    public int getTotalWaitedTime() {
        return totalWaitedTime;
    }

    public void setTotalWaitedTime(int totalWaitedTime) {
        this.totalWaitedTime = totalWaitedTime;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("Queue " + serverID + ": ");
        if (tasks.isEmpty()) return string + "closed.";
        for (Task task : tasks) {
            string.append(task);
        }
        return string.toString();
    }
}

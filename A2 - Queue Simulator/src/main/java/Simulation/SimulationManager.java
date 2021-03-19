package Simulation;

import Strategy.ShortestTimeStrategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable {
    public int timeLimit = 20;
    public int minProcessingTime = 2;
    public int maxProcessingTime = 10;
    public int minArrivalTime = 2;
    public int maxArrivalTime = 10;
    public int noServers = 3;
    public int noClients = 100;
    public AtomicInteger simulationTime = new AtomicInteger(0);
    private Scheduler scheduler;
    private List<Task> tasks;
    private Logger logger;
    private FileWriter writer;

    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime, int minArrivalTime, int maxArrivalTime, int noServers, int noClients) {
        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.noServers = noServers;
        this.noClients = noClients;
        generateTasks();
        scheduler = new Scheduler(new ShortestTimeStrategy(), noServers, noClients);
        logger = new Logger();
    }

    @Override
    public void run() {
        Task currentTask;

        while (simulationTime.intValue() <= timeLimit) {
            if (!tasks.isEmpty()) {
                currentTask = tasks.get(0);
                while (currentTask.getArrivalTime() == simulationTime.intValue()) {
                    scheduler.dispatchTask(currentTask);
                    tasks.remove(0);
                    if (tasks.isEmpty()) break;
                    currentTask = tasks.get(0);
                }
            }
            logger.log(simulationTime.intValue(), scheduler, tasks, writer);
            scheduler.updateWaitingTime();
            simulationTime.getAndIncrement();
            scheduler.incrementWaitedTime();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            scheduler.decrementProcessingTime();

            if (scheduler.done() && tasks.isEmpty()) {
                logger.log(simulationTime.intValue(), scheduler, tasks, writer);
                logger.logAvgTime(scheduler.getTotalWaitedTime() / (float) this.noClients, writer);
                try {
                    writer.close();
                } catch (IOException ignored) {
                }
                break;
            }
        }
        scheduler.stop();
    }

    private void generateTasks() {
        tasks = new ArrayList<>(noClients);
        Random random = new Random();
        int processingTime, arrivalTime;
        Task task;
        for (int i = 0; i < noClients; i++) {
            processingTime = minProcessingTime + random.nextInt(maxProcessingTime - minProcessingTime + 1);
            arrivalTime = minArrivalTime + random.nextInt(maxArrivalTime - minArrivalTime + 1);
            task = new Task(arrivalTime, processingTime, i);
            tasks.add(task);
        }
        Collections.sort(tasks);
    }

    public void changeOutFile(String filepath) {
        File myObj = null;
        try {
            myObj = new File(filepath);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            writer = new FileWriter(myObj.getAbsoluteFile());
        } catch (IOException e) {
            System.out.println("Bad out file.");
        }
    }
}

class Logger {
    public void log(int time, Scheduler scheduler, List<Task> tasks, FileWriter writer) {
        try {
            StringBuilder string = new StringBuilder("Time " + time + "\n");
            string.append("Waiting clients: ");
            for (Task task : tasks) {
                string.append(task.toString());
            }
            string.append("\n");
            for (Server server : scheduler.getServers()) {
                string.append(server.toString());
                string.append("\n");
            }
            string.append("\n");
            System.out.println(string.toString());
            writer.append(string.toString());
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error while logging.");
            System.out.println(e.getMessage());
        }
    }

    public void logAvgTime(float time, FileWriter writer) {
        try {
            writer.append("Average waiting time: ").append(String.valueOf(time));
        } catch (IOException e) {
            System.out.println("Error while logging avg waiting time.");
        }
    }

}

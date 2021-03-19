package Simulation;

import Strategy.Strategy;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private Strategy strategy;

    public Scheduler(Strategy strategy, int maxNoServers, int maxTasksPerServer) {
        this.strategy = strategy;
        servers = new ArrayList<>();
        for(int i = 0; i< maxNoServers; i++) {
            Server server = new Server(maxTasksPerServer, i + 1);
            servers.add(server);
            (new Thread(server)).start();
        }
    }

    public boolean done() {
        for (Server server : servers) {
            if(server.getTasks() != null) return false;
        }
        return true;
    }

    public void dispatchTask(Task task) {
        strategy.addTask(servers, task);
    }

    public void decrementProcessingTime() {
        for (Server server: servers) {
            if(server.getTasks() != null)  {
                server.getTasks()[0].decrementProcessingTime();
                for(Task task: server.getTasks()) {
                    task.incrementWaitingTime();
                }
            }
        }
    }

    public void incrementWaitedTime() {
        for (Server server: servers) {
            if(server.getTasks() != null) {
                server.setTotalWaitedTime(server.getTotalWaitedTime() + server.getTasks().length);
            }
        }
    }

    public void updateWaitingTime() {
        for (Server server: servers) {
            server.updateWaitingTime();
        }
    }

    public int getTotalWaitedTime() {
        int waitedTime = 0;
        for (Server server: servers) {
            waitedTime += server.getTotalWaitedTime();
        }
        return waitedTime;
    }

    public void stop() {
        for (Server server: servers) {
            server.stop();
        }
    }

    public List<Server> getServers() {
        return servers;
    }

    public void changeStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}


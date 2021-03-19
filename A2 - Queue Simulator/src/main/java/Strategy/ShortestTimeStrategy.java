package Strategy;

import Simulation.Server;
import Simulation.Task;

import java.util.List;
import java.util.ListIterator;

public class ShortestTimeStrategy implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task task) {
        if(servers == null) return;
        ListIterator<Server> it = servers.listIterator();
        Server optimalServer = servers.get(0);
        Server currentServer;
        int minimumWaitingPeriod = servers.get(0).getWaitingPeriod();
        while(it.hasNext()) {
            currentServer = it.next();
            if(currentServer.getWaitingPeriod() < minimumWaitingPeriod) {
                optimalServer = currentServer;
                minimumWaitingPeriod = currentServer.getWaitingPeriod();
            }
        }
        if (optimalServer != null) {
            optimalServer.addTask(task);
        }
    }
}

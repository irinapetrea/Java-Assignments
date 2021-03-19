package Strategy;

import Simulation.Server;
import Simulation.Task;

import java.util.List;
import java.util.ListIterator;

public class ShortestQueueStrategy implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task task) {
        // TODO: implement add task using the shortest queue strategy (new client always sits at the queue with least clients
        if(servers == null) return;
        ListIterator<Server> it = servers.listIterator();
        Server optimalServer = servers.get(0);
        Server currentServer;
        int minimumQueueLength = servers.get(0).getTasks().length;        while(it.hasNext()) {
            currentServer = it.next();
            if(currentServer.getWaitingPeriod() < minimumQueueLength) {
                optimalServer = currentServer;
                minimumQueueLength = currentServer.getTasks().length;
            }
        }
        if (optimalServer != null) {
            optimalServer.addTask(task);
        }
    }
}

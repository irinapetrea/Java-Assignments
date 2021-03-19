package Strategy;

import Simulation.Server;
import Simulation.Task;

import java.util.List;

public interface Strategy {
    public void addTask(List<Server> servers, Task task);
}

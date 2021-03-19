import Simulation.SimulationManager;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) throws IOException {
        SimulationManager manager = FileReader.read(args[0]);
        if(manager == null) return;
        manager.changeOutFile(args[1]);
        Thread simManager = new Thread(manager);
        simManager.setName("Simulation Manager");
        simManager.start();
    }
}

class FileReader {
    public static SimulationManager read(String infile) {
        File input = null;
        try {
            input = new File(infile);
            if (input.createNewFile()) {
                System.out.println("File created: " + input.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            int noClients;
            int noServers;
            int maxTime;
            int minArrival;
            int maxArrival;
            int minProcess;
            int maxProcess;

            Scanner reader = new Scanner(input);

            noClients = Integer.parseInt(reader.nextLine());
            noServers = Integer.parseInt(reader.nextLine());
            maxTime = Integer.parseInt(reader.nextLine());

            String[] arrival = reader.nextLine().split(",");
            String[] processing = reader.nextLine().split(",");

            minArrival = Integer.parseInt(arrival[0]);
            maxArrival = Integer.parseInt(arrival[1]);
            minProcess = Integer.parseInt(processing[0]);
            maxProcess = Integer.parseInt(processing[1]);
            reader.close();
            return new SimulationManager(maxTime, maxProcess, minProcess, minArrival, maxArrival, noServers, noClients);

        } catch (IOException e) {
            System.out.println("Bad in file.");
            return null;
        }
    }
}
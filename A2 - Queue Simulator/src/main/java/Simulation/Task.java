package Simulation;

public class Task implements Comparable<Task> {
    private int arrivalTime;
    private int processingTime;
    private int clientID;
    private int waitingTime = 0;

    public Task(int arrivalTime, int processingTime, int clientID) {
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
        this.clientID = clientID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public int getClientID() {
        return clientID;
    }

    public void incrementWaitingTime() {
        this.waitingTime++;
    }

    public void decrementProcessingTime(){
        this.processingTime--;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void incrementProcessingTime(){
        this.processingTime++;
    }

    @Override
    public int compareTo(Task o) {
        return arrivalTime - o.arrivalTime;
    }

    @Override
    public String toString() {
//        if(processingTime == 0) return "";
        return "(" + clientID + "," + arrivalTime + "," + processingTime + ")";
    }
}

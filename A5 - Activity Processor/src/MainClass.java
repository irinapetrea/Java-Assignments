import Business.DataProcessor;
import Business.TextFileProcessor;

public class MainClass {
    public static void main(String[] args) {
        DataProcessor dataProcessor = new DataProcessor(TextFileProcessor.parseTextFile("Activities.txt"));
        dataProcessor.task1();
        dataProcessor.task2();
        dataProcessor.task3();
        dataProcessor.task4();
        dataProcessor.task5();
        dataProcessor.task6();
    }
}

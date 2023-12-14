import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Driver
{
    public static void main(String[] args)
    {
        String fileName = "horses.csv";
        String outputFileName = "outputHorses.csv";
        List<Horse> horseList = new ArrayList<>();
        Set<Horse> horseSet;

        // Creates a Horse object with attributes based on a CSV file
        Horse horse1 = Horse.deserializeFromCSV(fileName);

        // Adds the Horse object's attributes to CSV file
        Horse.serializeToCSV(horse1, outputFileName);
        // Using the data from the recently created CSV file, create and populate the attributes of a new Horse object
        Horse horse2 = Horse.deserializeFromCSV(fileName);

        // Checks if the two Horse objects have the same contents
        if(horse1.equals(horse2))
            System.out.println("True");
        else
            System.out.println("False");

        horseList.add(horse1);
        horseList.add(new Horse("May", "Thoroughbred", 2016, 12, 3, 16.2, "Bay"));
        horseList.add(horse2);

        // horse2 Object will not be added to the horseSet since it is the same as horse1
        horseSet = Horse.serializeHorseListToSet(horseList);
    }
}
package org.cirdels;
import java.util.List;
import java.util.Set;

public class Driver
{
    public static void main(String[] args)
    {
        String fileName = "horses";
        String outputFileName = "outputHorses";
        List<Horse> horseList;
        List<Horse> horseList2;
        Set<Horse> horseSet;

        // *************  Lists  ***************

        // Creates a list of Horse objects with attributes based on a CSV file
        horseList = Horse.deserializeFromCSV(fileName);

        System.out.println("\nHorse List 1");
        for (Horse horse: horseList)
        {
            System.out.print(horse.prettyPrintForCSV());
        }

        // From a list, adds the Horse objects' attributes to a CSV file
        Horse.serializeToCSV(horseList, outputFileName);

        // Creates a list of Horse objects with attributes based on a CSV file
        horseList2 = Horse.deserializeFromCSV(fileName);

        System.out.println("\nHorse List 2");
        for (Horse horse: horseList2)
        {
            System.out.print(horse.prettyPrintForCSV());
        }
        System.out.println();

        // Checks if the Horse objects in the two lists have the same contents
        System.out.println("Lists 1 and 2 check");
        for (int i = 0; i < horseList.size(); i++)
        {
            System.out.print("Horse " + i + ": ");
            if(horseList.get(i).equals(horseList2.get(i)))
                System.out.println("True");
            else
                System.out.println("False");
        }

        // **************  Set  ************************
        horseSet = Horse.serializeHorseListToSet(horseList, fileName, outputFileName);

        System.out.println("\nHorse Set 1");
        for (Horse horse: horseSet)
        {
            System.out.print(horse.prettyPrintForCSV());
        }

        System.out.println("\nList 1 and set check");
        for (int i = 0; i < horseList.size(); i++)
        {
            System.out.print("Horse " + i + ": ");
            if(horseSet.contains(horseList.get(i)))
                System.out.println("True");
            else
                System.out.println("False");
        }

        // ******************  XML  *********************
        Horse.serializeToXML(horseList, outputFileName);

        List<Horse> horseList3 = Horse.deserializeFromXML(fileName);

        System.out.println("\nHorse List 3");
        for (Horse horse: horseList3)
        {
            System.out.print(horse.prettyPrintForCSV());
        }
        System.out.println();

    }
}
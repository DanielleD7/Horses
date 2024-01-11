package org.cirdels;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Horse implements Comparable, Serializable
{
    private String name;
    private String breed;
    private int birthYear;
    private int birthMonth;
    private int birthDay;
    private double height; // In Hands
    private String coatColor;

    // Default Constructor
    public Horse()
    {
        this("", "", 0, 0, 0, 0.0, "");
    }

    // Overloaded Constructor
    public Horse(String name, String breed, int birthYear, int birthMonth, int birthDay, double height, String coatColor)
    {
        this.name = name;
        this.breed = breed;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.height = height;
        this.coatColor = coatColor;
    }

    // Creates and returns a list of Horse object using data from a CSV file to populate the attributes.
    public static List<Horse> deserializeFromCSV(String fileName)
    {
        List<Horse> horses = new ArrayList<>();
        Path filePath = Paths.get(fileName + ".csv");

        // Tries to start up the file reader
        try(BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8))
        {
            String currentLine;

            // Reads each line in the CSV file
            while((currentLine = reader.readLine()) != null)
            {
                // Splits the current line by the ","
                String[] splitLine = currentLine.split(",");

                // Creates a new Horse object and populates the attributes with the data from the CSV file.
                Horse horse = new Horse(splitLine[0].trim(), splitLine[1].trim(), Integer.parseInt(splitLine[2].trim()),
                        Integer.parseInt(splitLine[3].trim()), Integer.parseInt(splitLine[4].trim()),
                        Double.parseDouble(splitLine[5].trim()), splitLine[6].trim());

                // Adds the new Horse object to the list
                horses.add(horse);
            }
        }
        catch(IOException ex)
        {
            System.out.printf("File %s could not be found.\n", fileName);
        }

        return horses;
    }

    // Appends the attributes of the Horse object to an output file in CSV format
    // Creates a file if none exist
    public static void serializeToCSV(List<Horse> horses, String outputFileName)
    {
        Path filePath = Paths.get(outputFileName + ".csv");

        try
        {
            // If no file of that path exists, create one
            if(!Files.exists(filePath))
                Files.createFile(filePath);

            // Goes through each horse in the list
            for (Horse horse: horses)
            {
                // Attributes from the Horse object in CSV format
                String horseAttrStr = horse.prettyPrintForCSV();

                // Appends the data of the Horse object to the output file
                Files.write(filePath, horseAttrStr.getBytes(), StandardOpenOption.APPEND);
            }
        }
        catch (IOException ex)
        {
            System.out.printf("File %s could not be found.\n", outputFileName);
        }
    }

    // Creates and returns a list of Horse object using data from an XML file to populate the attributes.
    public static List<Horse> deserializeFromXML(String fileName)
    {
        List<Horse> horses = new ArrayList<>();
        Path filePath = Paths.get(fileName + ".xml");

        // Tries to start up the file reader
        try(BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8))
        {
            String currentLine;
            Horse horse = null;

            // Reads each line in the XML file and populates the horse's attributes
            while((currentLine = reader.readLine()) != null)
            {
                currentLine = currentLine.trim();

                if(currentLine.startsWith("<horse>"))
                    horse = new Horse();

                // Checks of there is a non-null horse object
                if(horse != null)
                {
                    if (currentLine.startsWith("<name>"))
                        horse.name = currentLine.replace("<name>", "").replace("</name>", "").trim();

                    else if (currentLine.startsWith("<breed>"))
                        horse.breed = currentLine.replace("<breed>", "").replace("</breed>", "").trim();

                    else if (currentLine.startsWith("<birthYear>"))
                        horse.birthYear = Integer.parseInt(currentLine.replace("<birthYear>", "").replace("</birthYear>", "").trim());

                    else if (currentLine.startsWith("<birthMonth>"))
                        horse.birthMonth = Integer.parseInt(currentLine.replace("<birthMonth>", "").replace("</birthMonth>", "").trim());

                    else if (currentLine.startsWith("<birthDay>"))
                        horse.birthDay = Integer.parseInt(currentLine.replace("<birthDay>", "").replace("</birthDay>", "").trim());

                    else if (currentLine.startsWith("<height>"))
                        horse.height = Double.parseDouble(currentLine.replace("<height>", "").replace("</height>", "").trim());

                    else if (currentLine.startsWith("<coatColor>"))
                        horse.coatColor = currentLine.replace("<coatColor>", "").replace("</coatColor>", "").trim();

                    else if (currentLine.startsWith("</horse>"))
                        horses.add(horse);
                }
            }
        }
        catch(IOException ex)
        {
            System.out.printf("File %s could not be found.\n", fileName);
        }

        return horses;
    }

    // Appends the attributes of the Horse object to an output file in XML format
    // Creates a file if none exist
    public static void serializeToXML(List<Horse> horses, String outputFileName)
    {
        Path filePath = Paths.get(outputFileName + ".xml");

        try
        {
            // If no file of that path exists, create one
            if(!Files.exists(filePath))
                Files.createFile(filePath);

            // Goes through each horse in the list
            for (Horse horse: horses)
            {
                // Attributes from the Horse object in XML format
                String horseAttrStr = horse.prettyPrintForXML();

                // Appends the data of the Horse object to the output file
                Files.write(filePath, horseAttrStr.getBytes(), StandardOpenOption.APPEND);
            }
        }
        catch (IOException ex)
        {
            System.out.printf("File %s could not be found.\n", outputFileName);
        }
    }


    // Returns a LinkedHashSet of the List passed in
    public static Set<Horse> serializeHorseListToSet(List<Horse> horses, String fileName, String outputFileName)
    {
        // List to file to set
        // Might be able to call the methods Horse already has

        // ***********  List to CSV file  **************
        Path filePath = Paths.get(outputFileName + ".csv");

        try
        {
            // If no file of that path exists, create one
            if(!Files.exists(filePath))
                Files.createFile(filePath);

            // Goes through each horse in the list
            for (Horse horse: horses)
            {
                // Attributes from the Horse object in CSV format
                String horseAttrStr = horse.prettyPrintForCSV();

                // Appends the data of the Horse object to the output file
                Files.write(filePath, horseAttrStr.getBytes(), StandardOpenOption.APPEND);
            }
        }
        catch (IOException ex)
        {
            System.out.printf("File %s could not be found.\n", outputFileName);
        }

        // **************  CSV file to LinkedHashSet  *****************
        Set<Horse> horseSet = new LinkedHashSet<>();
        filePath = Paths.get(fileName + ".csv");

        // Tries to start up the file reader
        try(BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8))
        {
            String currentLine;

            // Reads each line in the CSV file
            while((currentLine = reader.readLine()) != null)
            {
                // Splits the current line by the ","
                String[] splitLine = currentLine.split(",");

                // Creates a new Horse object and populates the attributes with the data from the CSV file.
                Horse horse = new Horse(splitLine[0].trim(), splitLine[1].trim(), Integer.parseInt(splitLine[2].trim()),
                        Integer.parseInt(splitLine[3].trim()), Integer.parseInt(splitLine[4].trim()),
                        Double.parseDouble(splitLine[5].trim()), splitLine[6].trim());

                // Adds the new Horse object to the Set
                horseSet.add(horse);
            }
        }
        catch(IOException ex)
        {
            System.out.printf("File %s could not be found.\n", fileName);
        }

        return horseSet;
    }

//    public static Set<Horse> serializeHorseListToSet(List<Horse> horseList)
//    {
//        // List to file to set
//        // Comp
//        return new LinkedHashSet<>(horseList);
//    }

    // Returns a string of the Horse's objects attributes in CSV format
    public String prettyPrintForCSV()
    {
        return name + "," + breed + "," + birthYear + "," + birthMonth + "," + birthDay + "," + height + "," + coatColor + "\n";
    }

    // Returns a string of the Horse's objects attributes in XML format
    public String prettyPrintForXML()
    {
        return "<horse>\n" +
                "    <name>" + this.name + "</name>\n" +
                "    <breed>" + this.breed + "</breed>\n" +
                "    <birthYear>" + this.birthYear + "</birthYear>\n" +
                "    <birthMonth>" + this.birthMonth + "</birthMonth>\n" +
                "    <birthDay>" + this.birthDay + "</birthDay>\n" +
                "    <height>" + this.height + "</height>\n" +
                "    <coatColor>" + this.coatColor + "</coatColor>\n" +
                "</horse>\n";
    }

    //Getters
    public String getName()
    {
        return name;
    }

    public String getBreed()
    {
        return breed;
    }

    public int getBirthYear()
    {
        return birthYear;
    }

    public int getBirthMonth()
    {
        return birthMonth;
    }

    public int getBirthDay()
    {
        return birthDay;
    }

    public double getHeight()
    {
        return height;
    }

    public String getCoatColor()
    {
        return coatColor;
    }

    // Overrides the equals method to allow the comparison of the two Horse objects based on their attributes.
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horse horse = (Horse) o;
        return birthYear == horse.birthYear && birthMonth == horse.birthMonth && birthDay == horse.birthDay
                && Double.compare(height, horse.height) == 0 && Objects.equals(name, horse.name)
                && Objects.equals(breed, horse.breed) && Objects.equals(coatColor, horse.coatColor);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, breed, birthYear, birthMonth, birthDay, height, coatColor);
    }

    @Override
    public int compareTo(Object o)
    {
        Horse horse = (Horse) o;

        if (this.birthYear != horse.birthYear)
            return Integer.compare(this.birthYear, horse.birthYear);
        else if (this.birthMonth != horse.birthMonth)
            return Integer.compare(this.birthMonth, horse.birthMonth);
        else // birthYear and birthMonth are the same
            return Integer.compare(this.birthDay, horse.birthDay);
    }
}

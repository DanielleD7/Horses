import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Horse
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

    // Creates and returns a Horse object using data from a CSV file to populate its attributes.
    public static Horse deserializeFromCSV(String fileName)
    {
        Horse horse;
        Path filePath = Paths.get(fileName);

        // Tries to start up the file reader
        try(BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8))
        {
            // Gets the first line in the text file and splits it by the ","
            String currentLine = reader.readLine();
            String[] splitLine = currentLine.split(",");

            // Creates a new Horse object and populates the attributes with the data from the file.
            horse = new Horse(splitLine[0].trim(), splitLine[1].trim(), Integer.parseInt(splitLine[2].trim()),
                    Integer.parseInt(splitLine[3].trim()), Integer.parseInt(splitLine[4].trim()),
                    Double.parseDouble(splitLine[5].trim()), splitLine[6].trim());
        }
        catch(IOException ex)
        {
            System.out.printf("File %s could not be found.\n", fileName);
            horse = new Horse();
        }

        return horse;
    }

    // Appends the attributes of the Horse object to an output file in CSV format
    public static void serializeToCSV(Horse horse, String outputFileName)
    {
        Path filePath = Paths.get(outputFileName);

        try
        {
            // Attributes from the Horse object in CSV format
            String horseAttrStr = horse.prettyPrintForCSV();

            // If no file of that path exists, create one
            if(!Files.exists(filePath))
                Files.createFile(filePath);

            // Appends the data of the Horse object to the output file
            Files.write(filePath, horseAttrStr.getBytes(), StandardOpenOption.APPEND);
        }
        catch (IOException ex)
        {
            System.out.printf("File %s could not be found.\n", outputFileName);
        }
    }

    // Returns a LinkedHashSet of the List passed in
    public static Set<Horse> serializeHorseListToSet(List<Horse> horseList)
    {
        return new LinkedHashSet<>(horseList);
    }

    // Returns a string of the Horse's objects attributes
    public String prettyPrintForCSV()
    {
        return name + "," + breed + "," + birthYear + "," + birthMonth + "," + birthDay + "," + height + "," + coatColor + "\n";
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

    public int getBirthYear() {
        return birthYear;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public int getBirthDay() {
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
}

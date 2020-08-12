package search;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    
    static Scanner scanner = new Scanner(System.in);
    
    public static String[] standartInputPeople() {
        System.out.println("Enter the number of people:");
        
        int numberOfLines = scanner.nextInt();
        scanner.nextLine();
        String[] lines = new String[numberOfLines];
        
        System.out.println("Enter the number of people:");
        
        for (int i = 0; i < lines.length; i++) {
            lines[i] = scanner.nextLine();
        }
        
        return lines;
    }

    public static String[] fileInputPeople(String path) {
        File people = new File(path);
        String[] lines = new String[10000];
        int index = 0;

        try (Scanner scanner = new Scanner(people)) {
            while (scanner.hasNext()) {
                lines[index] = scanner.nextLine();
                index++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + people.getPath());
        }

        return Arrays.copyOfRange(lines,0, index);
    }
    
    public static void findPerson(String[] lines) {
        
        System.out.println("\nEnter a name or email to search all suitable people.");

        String dataToSearch = scanner.next().toLowerCase();
        StringBuilder searchResult = new StringBuilder();

        for (String s : lines) {
            String line = s.toLowerCase();
            if (line.contains(dataToSearch)) {
                searchResult.append(s).append("\n");
            }
        }
        
        if (searchResult.length() == 0) {
            System.out.println("No matching people found.");
        } else {
            System.out.println("Found people:");
            System.out.println(searchResult);
        }

    }
    
    public static void main(String[] args) {
        String[] lines;

        if (args.length != 0 && "--data".equals(args[0])) {
            lines = fileInputPeople(args[1]);
        } else {
            lines = standartInputPeople();
        }
        
        while(true) {
            System.out.println("\n=== Menu ===\n1. Find a person\n2. Print all people\n0. Exit");
            String choice = scanner.next();
            switch(choice) {
                case "1":
                    findPerson(lines);
                    break;
                case "2":
                    System.out.println("\n=== List of people ===");
                    System.out.println(String.join("\n", lines));
                    break;
                case "0":
                    return;
                default:
                    System.out.println("\nIncorrect option! Try again.");
            }
        }
        
        
    }
}

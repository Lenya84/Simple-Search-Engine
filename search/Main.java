package search;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    
    static Scanner scanner = new Scanner(System.in);
    
    public static List<String> standartInputPeople() {
        System.out.println("Enter the number of people:");
        
        int numberOfLines = scanner.nextInt();
        scanner.nextLine();
        List<String> people = new ArrayList<>();
        
        System.out.println("Enter the number of people:");
        
        for (int i = 0; i < numberOfLines; i++) {
            people.add(scanner.nextLine());
        }
        
        return people;
    }

    public static List<String> fileInputPeople(String path) {
        File peopleFile = new File(path);
        List<String> people = new ArrayList<>();

        try (Scanner scanner = new Scanner(peopleFile)) {
            while (scanner.hasNext()) {
                people.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + peopleFile.getPath());
        }

        return people;
    }
    
    public static void findPerson(List<String> lines) {
        
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

    public static void findPerson(Map<String, Set<Integer>> map, List<String> people) {
        System.out.println("\nEnter a name or email to search all suitable people.");

        String dataToSearch = scanner.next().toLowerCase();
        StringBuilder searchResult = new StringBuilder();

        Set<Integer> numbers = map.get(dataToSearch);

        if (numbers != null) {
            for (var n : numbers) {
                searchResult.append(people.get(n)).append("\n");
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
        List<String> people;

        if (args.length != 0 && "--data".equals(args[0])) {
            people = fileInputPeople(args[1]);
        } else {
            people = standartInputPeople();
        }

        Map<String, Set<Integer>> map = new HashMap<>();
        for (var line : people) {
            for (var word : line.split(" ")) {
                for (int i = 0; i < people.size(); i++) {
                    word = word.toLowerCase();
                    String str = people.get(i).toLowerCase();
                    if (str.contains(word.toLowerCase())) {
                        if (map.putIfAbsent(word, new HashSet<>(Set.of(i))) != null) {
                            map.get(word).add(i);
                        }
                    }
                }
            }
        }
        
        while(true) {
            System.out.println("\n=== Menu ===\n1. Find a person\n2. Print all people\n0. Exit");
            String choice = scanner.next();
            switch(choice) {
                case "1":
                    findPerson(map, people);
                    break;
                case "2":
                    System.out.println("\n=== List of people ===");
                    System.out.println(String.join("\n", people));
                    break;
                case "0":
                    return;
                default:
                    System.out.println("\nIncorrect option! Try again.");
            }
        }
        
        
    }
}

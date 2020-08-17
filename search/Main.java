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
        
        System.out.println("Enter people:");
        
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

    public static void findPerson(Map<String, Set<Integer>> map, List<String> people, String mode) {
        System.out.println("\nEnter a name or email to search all suitable people.");
        scanner.nextLine();
        String dataToSearch = scanner.nextLine().toLowerCase();
        StringBuilder searchResult = new StringBuilder();

        if ("ALL".equals(mode)) {
            Set<Integer> indexes = map.get(dataToSearch);

            if (indexes != null) {
                for (var indx : indexes) {
                    searchResult.append(people.get(indx)).append("\n");
                }
            }
        } else if ("ANY".equals(mode)) {
            Set<Integer> indexes = new HashSet<>();

            for (var word : dataToSearch.split(" ")) {
                indexes.addAll(map.getOrDefault(word, Set.of()));
            }

            for (var indx : indexes) {
                searchResult.append(people.get(indx)).append("\n");
            }
        } else if ("NONE".equals(mode)) {
            Set<Integer> noneIndexes = new HashSet<>();

            for (var word : dataToSearch.split(" ")) {
                if (map.get(word) != null) {
                    noneIndexes.addAll(map.get(word));
                }
            }

            for (int i = 0; i < people.size(); i++) {
                if (!noneIndexes.contains(i)) {
                    searchResult.append(people.get(i)).append("\n");
                }
            }
        } else {
            System.out.println("error");
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
                word = word.toLowerCase();
                for (int i = 0; i < people.size(); i++) {
                    String str = people.get(i).toLowerCase();
                    if (str.contains(word)) {
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
                    System.out.println("Select a matching strategy: ALL, ANY, NONE");
                    String mode = scanner.next();
                    findPerson(map, people, mode);
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

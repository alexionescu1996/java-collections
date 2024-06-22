package collections.hashmap;

import org.example.model.UserProfile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOError;
import java.util.*;
import java.util.stream.Collectors;

import static collections.arraylist.Main.performanceAL;
import static collections.hashset.Main.performanceHS;
import static collections.linkedhashset.Main.performanceLHS;
import static collections.linkedlist.Main.performanceLL;
import static collections.treeset.Main.performanceTS;

public class Main {

    @Test
    public void ex1() {
        HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
        hashMap.put(1, "a");
        hashMap.put(2, "b");
        System.out.println(hashMap);
        hashMap.put(2, "a");
        String x = hashMap.getOrDefault(3, "#");
        hashMap.putIfAbsent(3, hashMap.getOrDefault(3, "#"));
        System.out.println(hashMap);

    }

    @Test
    public void testPerf() {
        performanceAL();
        performanceLL();
        performanceHS();
        performanceLHS();
        performanceTS();
        performanceHM();

//        ArrayList:      977 ms
//        LinkedList:     2250 ms

//        HashSet:        2641 ms with String name comparison
//        HashSet:         890 ms with Integer id comparison

//        LinkedHashSet:  3398 ms with String name comparison
//        LinkedHashSet:  728  ms with Integer id comparison

//        TreeSet:        3766 ms with String name comparison
//        TreeSet:        1109 ms with Integer id comparison


//        ArrayList: 1008 ms 10000000
//        LinkedList: 2489 ms 10000000
//        HashSet: 1750 ms 10000000
//        LinkedHashSet: 1985 ms 10000000
//        TreeSet: 3734 ms 10000000
//        HashMap: 938 ms 10000000

    }

    @Test
    public void ex2() {
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            hashMap.putIfAbsent(i, "name_" + i);
        }
        for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        for (Integer key : hashMap.keySet()) {
            System.out.println(key + " " + hashMap.get(key));
        }
        for (String value : hashMap.values()) {
            System.out.println(value);
        }
        System.out.println(hashMap);
    }

    @Test
    public void ex3() {
//        name,salary,age
        HashMap<String, Integer> hashMap = new HashMap<>();

        String filePath = "src/main/resources/MOCK_DATA.csv";
        long startTime = System.currentTimeMillis();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.nextLine();
            while (scanner.hasNext()) {
                String current = scanner.nextLine();
                String[] words = current.split(",");
                String name = words[0];
                Double salary = Double.valueOf(words[1]);
//                Integer age = Integer.valueOf(words[2]);
                hashMap.put(name, hashMap.getOrDefault(name, 0) + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void performanceHM() {
        long startTime = System.currentTimeMillis();
        Map<Integer, UserProfile> hashMap = new HashMap<>();
        populateMap(hashMap, 10_000_000);
        long endTime = System.currentTimeMillis();
        System.out.println("HashMap: " + (endTime - startTime) + " ms " + hashMap.size());
    }

    public void populateMap(Map<Integer, UserProfile> map, int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            Integer id = random.nextInt(150);
            UserProfile u = new UserProfile("name_" + i, id);
            map.put(i, u);
        }
    }
}

package collections.hashmap;

import org.example.model.Employee;
import org.example.model.Person;
import org.example.model.UserProfile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.*;
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
        System.out.println(hashMap);
    }

    @Test
    public void ex4() {
        HashMap<Employee, Integer> hashMap = new HashMap<>();
        populateMapFromFile(hashMap, 500);
    }

    private static void populateMapFromFile(HashMap<Employee, Integer> hashMap, int limit) {
        ArrayList<Employee> duplicates = new ArrayList<>();
        int key = 0;
        Random random = new Random();

        String filePath = "src/main/resources/MOCK_DATA.csv";
        long startTime = System.currentTimeMillis();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.nextLine();
            while (scanner.hasNext()) {
                String current = scanner.nextLine();
                String[] words = current.split(",");

                String name = words[0];
                Double salary = Double.valueOf(words[1]);
                Integer age = Integer.valueOf(words[2]);
                Integer id = random.nextInt(1000);

                Employee employee = new Employee(id, name, age, salary);
                if (hashMap.containsKey(employee)) {
                    Integer duplicateID = hashMap.get(employee);

                    System.out.println("am gasit deja" + employee.getName() + " " + employee.getSalary() + " " + employee.getAge());
                    duplicates.add(employee);
                }
                if (hashMap.size() < limit) {
                    hashMap.put(employee, key++);
                } else {
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        System.out.println("size : " + hashMap.size());
        System.out.println("finished in :" + (endTime - startTime));
    }

    @Test
    public void ex5() {
        HashMap<Employee, Integer> hashMap = new HashMap<>();
        populateMapFromFile(hashMap, 5);

        HashMap<Integer, Employee> reverseHM = new HashMap<>();
        hashMap.forEach((key, value) -> {
            reverseHM.put(value, key);
        });
        System.out.println(reverseHM);
        System.out.println(hashMap);
        HashMap<Person, Integer> hashMap1 = new HashMap<>(hashMap);
        System.out.println(hashMap1.size());
        ArrayList<Person> arrayList = new ArrayList<>(hashMap.keySet());
        Person p = arrayList.get(0);
        Integer pId = hashMap1.get(p);
        System.out.println(pId);

        Employee employee = new Employee(pId, p.getName(), p.getAge(), p.getSalary());
        Integer employeeId = hashMap.get(employee);
        System.out.println(employeeId);
        hashMap.put(employee, -1);
        System.out.println(hashMap);

        HashSet<Person> people = new HashSet<>(hashMap.keySet());
        System.out.println(people);
    }

    @Test
    public void sorByKey() {
        HashMap<Employee, Integer> hashMap = new HashMap<>();
        populateMapFromFile(hashMap, 5);

        TreeSet<Employee> treeSet = new TreeSet<>(Comparator.comparing(Employee::getAge));

        ArrayList<Employee> arrayList = new ArrayList<>(hashMap.keySet());
        arrayList.sort(Comparator.comparing(Employee::getSalary));

        treeSet.addAll(hashMap.keySet());
        System.out.println(hashMap);

        System.out.println(treeSet);
        System.out.println(arrayList);

        HashMap<Employee, Integer> sortedHM = new HashMap<>();
        for (Employee e : treeSet) {
            sortedHM.put(e, hashMap.get(e));
        }
        System.out.println(sortedHM);
    }

    @Test
    public void ex6() {
        HashMap<Employee, Integer> hashMap = new HashMap<>();
        populateMapFromFile(hashMap, 5);

        HashMap<Integer, Employee> reverseHM = new HashMap<>();
        hashMap.forEach((key, value) -> {
            reverseHM.put(value, key);
        });

        System.out.println(hashMap);

        HashMap<Integer, Employee> cloneReverseHM = (HashMap<Integer, Employee>) reverseHM.clone();
        Person p = reverseHM.get(0);
        p.setName("####");

        System.out.println(cloneReverseHM);
        cloneReverseHM.putAll(reverseHM);

        System.out.println(cloneReverseHM.size());
    }

    @Test
    public void ex7() throws InterruptedException {
        HashMap<Employee, Integer> hashMap = new HashMap<>();
        populateMapFromFile(hashMap, 5);

        HashMap<Integer, Employee> reverseHM = new HashMap<>();
        hashMap.forEach((key, value) -> reverseHM.put(value, key));

        Map<Integer, Employee> map = Collections.synchronizedMap(reverseHM);
//        Map<Integer, Employee> map = new HashMap<>(reverseHM);

        Employee first = map.get(0);
        first.setName("TESTING");


        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();
            Runnable r = () -> {
                Employee f = map.get(0);
                f.setName("###");
                System.out.println(map);
            };
            service.submit(r);
            System.out.println("finished");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (service != null) {
                service.awaitTermination(5, TimeUnit.SECONDS);
                if (service.isShutdown()) {
                    System.out.println("done");
                }
            }
        }

        System.out.println(map);
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

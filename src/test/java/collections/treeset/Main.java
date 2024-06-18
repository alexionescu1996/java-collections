package collections.treeset;

import com.sun.source.tree.Tree;
import lombok.Data;
import org.example.model.UserProfile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static collections.arraylist.Main.performanceAL;
import static collections.hashset.Main.performanceHS;
import static collections.linkedhashset.Main.performanceLHS;
import static collections.linkedhashset.Main.populateList;
import static collections.linkedlist.Main.performanceLL;

public class Main {

    private static TreeSet<Person> EMPLOYEES_TS = new TreeSet<>(Comparator.comparing(Person::getAge));


    @BeforeAll
    private static void init() {
//        EMPLOYEES_TS = (TreeSet<Person>) addFromFile(EMPLOYEES_TS);
    }

    @Test
    public void testPerf() {
        performanceAL();
        performanceLL();
        performanceHS();
        performanceLHS();
        performanceTS();

//        ArrayList:      977 ms
//        LinkedList:     2250 ms

//        HashSet:        2641 ms with String name comparison
//        HashSet:         890 ms with Integer id comparison

//        LinkedHashSet:  3398 ms with String name comparison
//        LinkedHashSet:  728  ms with Integer id comparison

//        TreeSet:        3766 ms with String name comparison
//        TreeSet:        1109 ms with Integer id comparison

    }

    @Test
    public void ex3() {
        Person p = new Person("a123", 30, 32.3d);
        Person p1 = new Person("a123", 31, 32.3d);
        Person p2 = new Person("abb", 30, 32.3d);

        TreeSet<Person> byName = new TreeSet<>(Comparator.comparing(Person::getName));
        byName.add(p);
        byName.add(p1);
        byName.add(p2);

        System.out.println(byName.size());

        TreeSet<Person> byAge = new TreeSet<>(Comparator.comparing(Person::getAge));
        byAge.add(p);
        byAge.add(p1);
        byAge.add(p2);
        Person p4 = new Person("abb", 1, 32.3d);
        byAge.add(p4);

        System.out.println(byName.size());

        TreeSet<Person> bySalary = new TreeSet<>(Comparator.comparing(Person::getSalary));
        bySalary.add(p);
        bySalary.add(p1);
        bySalary.add(p2);
        System.out.println(byName.size());
    }

    @Test
    public void ex4() {
        System.out.println("start method");
        ArrayList<Person> arrayList = (ArrayList<Person>) addFromFile(new ArrayList<>());
        LinkedList<Person> linkedList = (LinkedList<Person>) addFromFile(new LinkedList<>());
        HashSet<Person> hashSet = (HashSet<Person>) addFromFile(new HashSet<>());
        LinkedHashSet<Person> linkedHashSet = (LinkedHashSet<Person>) addFromFile(new LinkedHashSet<>());

        TreeSet<Person> byAgeTS = (TreeSet<Person>) addFromFile(new TreeSet<>(Comparator.comparing(Person::getAge)));
        TreeSet<Person> bySalaryTS = (TreeSet<Person>) addFromFile(new TreeSet<>(Comparator.comparing(Person::getSalary)));
        TreeSet<Person> byNameTs = (TreeSet<Person>) addFromFile(new TreeSet<>(Comparator.comparing(Person::getName)));

//        System.out.println(arrayList.size());
//        System.out.println(linkedList.size());
    }

    @Test
    public void ex5() {
        TreeSet<Person> bySalaryTS = (TreeSet<Person>) addFromFile(new TreeSet<>(Comparator.comparing(Person::getSalary)));
        TreeSet<Person> byNameTs = (TreeSet<Person>) addFromFile(new TreeSet<>(Comparator.comparing(Person::getName)));

        TreeSet<Person> under1500salary = bySalaryTS.stream()
                .filter(person -> person.getSalary() < 1500)
                .collect(
                        Collectors.toCollection(
                                () -> new TreeSet<>(Comparator.comparing(Person::getSalary))
                        ));

        Person sibby = new Person("Sibby", 69, 1012.0d);

        TreeSet<Person> subset = (TreeSet<Person>) bySalaryTS.subSet(bySalaryTS.first(), sibby);
        TreeSet<Person> subsetWithIncluded = (TreeSet<Person>) bySalaryTS.subSet(bySalaryTS.first(), true, sibby, true);
        TreeSet<Person> headSet = (TreeSet<Person>) bySalaryTS.headSet(sibby, true);
        TreeSet<Person> tailSet = (TreeSet<Person>) bySalaryTS.tailSet(sibby, true);
        System.out.println(tailSet.size());

        System.out.println(under1500salary.size());
    }

    @Test
    public void ex6() {

    }

    public static void performanceTS() {
        TreeSet<UserProfile> treeSet = new TreeSet<>(Comparator.comparing(UserProfile::getId));
        long startTime = System.currentTimeMillis();

        populateList(treeSet, 10_000_000);
        long endTime = System.currentTimeMillis();

        System.out.println("TreeSet: " + (endTime - startTime) + " ms");
    }

    private static Collection<Person> addFromFile(Collection<Person> collection) {
        String filePath = "src/main/resources/MOCK_DATA.csv";
        long startTime = System.currentTimeMillis();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNext()) {
                String input = scanner.nextLine();
                String[] inputS = input.split(",");
                String name = inputS[0];
                double salary = Integer.parseInt(inputS[1]);
                int age = Integer.parseInt(inputS[2]);
                Person person = new Person(name, age, salary);
                collection.add(person);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(collection.size() + " took: " + (endTime - startTime) + " ms");
        return collection;
    }


}

@Data
class Person {
    private String name;
    private Integer age;
    private Double salary;

    public Person(String name, Integer age, Double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;

        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
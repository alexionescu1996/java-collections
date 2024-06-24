package collections.treeset;

import lombok.Data;
import org.example.model.Person;
import org.example.model.UserProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static collections.arraylist.Main.performanceAL;
import static collections.hashset.Main.performanceHS;
import static collections.linkedhashset.Main.performanceLHS;
import static collections.linkedhashset.Main.populateSet;
import static collections.linkedlist.Main.performanceLL;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        TreeSet<Person> treeSet = new TreeSet<>(Comparator.comparing(Person::getSalary));
        addFromFile(treeSet);
        Person first = treeSet.first();
        Person last = treeSet.last();

        Person beforeFirst = treeSet.lower(first);
        Assertions.assertThrows(NullPointerException.class, () -> {
            beforeFirst.getSalary();
        });

        System.out.println("first: " + first.getSalary());
        System.out.println("last: " + last.getSalary());

        Person floor = treeSet.floor(new Person("search", 1, 1115.5d));
        Person ceiling = treeSet.ceiling(new Person("search", 1, 1115.5d));

        System.out.println("floor: " + floor.getSalary());
        System.out.println("ceiling: " + ceiling.getSalary());
    }


    @Test
    public void ex7() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        for (int i = 0; i < 50; i = i + 10) {
            treeSet.add(i);
        }
        System.out.println(treeSet.size());

        int first = treeSet.first();
        int last = treeSet.last();

        System.out.println(first + " " + last);

//        floor <=
        int floorResult = treeSet.floor(23);

//        ceiling >=
        int ceilResult = treeSet.ceiling(23);

        System.out.println("floor: " + floorResult);
        System.out.println("ceiling: " + ceilResult);
//        int floorResult1 = treeSet.floor(5);
//        int ceilResult1 = treeSet.ceiling(5);

//        System.out.println(floorResult1 + " " + ceilResult1);

    }


    @Test
    public void ex8() {
        TreeSet<Person> treeSet = new TreeSet<>(Comparator.comparing(Person::getSalary));
        addFromFile(treeSet);

        Person floor = treeSet.floor(new Person("search", 1, 1115.5d));
        Person ceiling = treeSet.ceiling(new Person("search", 1, 1115.5d));

        TreeSet<Person> sub = (TreeSet<Person>) treeSet.subSet(floor, treeSet.last());
        System.out.println(sub.size());

        TreeSet<Person> sub1 = (TreeSet<Person>) treeSet.subSet(treeSet.first(), ceiling);
        System.out.println(sub1.size());

        Person seconds = treeSet.lower(sub1.last());
        System.out.println(seconds);

        TreeSet<Person> newS = new TreeSet<>(Comparator.comparing(Person::getName));
        newS.addAll(treeSet);
        System.out.println(newS.size() + " " + treeSet.size());

//        treeSet.headSet();
    }

    @Test
    public void ex9() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            treeSet.add(i);
        }

        int first = treeSet.first();
        int last = treeSet.last();
        System.out.println(first + " " + last);

        int second = treeSet.higher(first);
        int third = treeSet.higher(second);
        System.out.println(second + " " + third);

        System.out.println(treeSet.lower(third));

        TreeSet<Integer> headSet = (TreeSet<Integer>) treeSet.headSet(5);
        System.out.println(headSet);

        TreeSet<Integer> tailSet = (TreeSet<Integer>) treeSet.tailSet(5);
        System.out.println(tailSet);

        TreeSet<Integer> subSet = (TreeSet<Integer>) treeSet.subSet(3, 6);
        System.out.println(subSet);
    }

    @Test
    public void ex10() {
        TreeSet<Integer> treeSet1 = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            treeSet1.add(i);
        }

        TreeSet<Integer> treeSet2 = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            treeSet2.add(i);
        }

        System.out.println(treeSet2.containsAll(treeSet1));
        System.out.println(treeSet1.equals(treeSet2));
    }

    @Test
    public void ex11() {
        List<Person> arrayList = new ArrayList<>();
        addFromFile(arrayList);
        System.out.println(arrayList.size());

        TreeSet<Person> treeSet = new TreeSet<>(arrayList);
        System.out.println(treeSet.size());

        TreeSet<Person> tsCustomComparator = new TreeSet<>(new PersonAgeComparator());
        tsCustomComparator.addAll(arrayList);
        System.out.println(tsCustomComparator.size());
    }

    public static void performanceTS() {
        TreeSet<UserProfile> treeSet = new TreeSet<>(Comparator.comparing(UserProfile::getName));
        long startTime = System.currentTimeMillis();

        populateSet(treeSet, 10_000_000);
        long endTime = System.currentTimeMillis();

        System.out.println("TreeSet: " + (endTime - startTime) + " ms " + treeSet.size());
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

class PersonNameComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return p1.getName().compareTo(p2.getName());
    }
}

class PersonAgeComparator implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        return o1.getAge().compareTo(o2.getAge());
    }
}


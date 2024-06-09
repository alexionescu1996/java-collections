package collections.arraylist;


import org.example.model.UserProfile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Main {


    static List<UserProfile> initList;

    @BeforeAll
    public static void init() {
        initList = getList(10);

    }


    @Test
    public void ex1() {
        List<UserProfile> list = getList(10);

        UserProfile user = list.get(0);
        list.add(user);
        list.add(user);
        list.add(user);

        System.out.println(user);

        list.remove(user);
        list.remove(0);

        System.out.println(list);
    }

    @Test
    public void ex2() {
        List<UserProfile> list = getList(150);
        long startTime = System.currentTimeMillis();
        System.out.println(list.size());
        list.removeIf(userProfile -> userProfile.getName().length() > 6);
        System.out.println(list.size());
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime + " ms");
    }

    @Test
    public void ex3() {
        System.out.println("start");

        List<UserProfile> list = getList(150);
        long startTime = System.currentTimeMillis();

        List<UserProfile> list2 = getList(10_000_000);
        long startTime2 = System.currentTimeMillis();

        System.out.println(startTime2 - startTime + "created ms");

//          -2147483648         2_147_483_647
//                                1_000_000
//        Integer.MIN_VALUE   Integer.MAX_VALUE

        list.addAll(list2);
        long startTime3 = System.currentTimeMillis();

        System.out.println(startTime3 - startTime2 + " last ms");


//        System.out.println(list.size() + " :: list size");
//        10/4
    }

    @Test
    public void ex4() {
        List<UserProfile> list = getList(150);
        UserProfile[] array = list.toArray(new UserProfile[0]);

        List<UserProfile> fromArray = Arrays.stream(array).toList();
        System.out.println(fromArray.size() + " new array size");
    }

    @Test
    public void ex5() {
        List<UserProfile> list = getList(10);

        Comparator<UserProfile> comparator =
                Comparator.comparing(UserProfile::getName);
        Collections.sort(list, comparator);

    }

    @Test
    public void ex_6_7() {
        List<UserProfile> list = getList(10);

        Collections.reverse(list);
        Collections.shuffle(list);

    }

    @Test
    public void ex8() {
        List<UserProfile> list = getList(10);
        List<UserProfile> newList = list.subList(0, 5);
        list.addAll(newList);

        System.out.println(list.size() + "size");

        HashMap<UserProfile, Integer> duplicatesMap = new HashMap<>();
        for (UserProfile i : list) {
            duplicatesMap.put(i, duplicatesMap.getOrDefault(i, 0) + 1);
        }

        System.out.println(duplicatesMap);

        List<UserProfile> duplicates = duplicatesMap.entrySet()
                .stream()
                .filter(p -> p.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();

        System.out.println(duplicates.size());
    }

    @Test
    public void ex9() {
        initList.sort(Comparator.comparing(UserProfile::getId));
    }

    @Test
    public void ex10() {
//        retainALl => list with common elements

        List<String> bag = new ArrayList<>();
        List<String> box = new ArrayList<>();

        bag.add("pen");
        bag.add("pencil");
        bag.add("notebook");
        bag.add("rubber");

        box.add("pen");
        box.add("pencil");

        box.retainAll(bag);
        System.out.println(box.size());

    }

    @Test
    public void ex11() {
        UserProfile first = initList.get(3);

//        initList.sort(Comparator.comparing(UserProfile::getName));

        int index = Collections.binarySearch(
                initList, first, Comparator.comparing(UserProfile::getName)
        );

        System.out.println(index);
    }

    @Test
    public void ex12() {
        List<UserProfile> subList = initList.subList(3, 7);
        System.out.println(subList);
    }

    @Test
    public void ex13() {
        List<UserProfile> immutableList = Collections.unmodifiableList(initList);
        assertThrows(UnsupportedOperationException.class, () -> immutableList.add(new UserProfile("test", -1)));

        List<UserProfile> newList = new ArrayList<>(immutableList);
        System.out.println(immutableList);
    }

    @Test
    public void ex15() {
        Iterator<UserProfile> iterator = initList.iterator();
        Iterator<UserProfile> iterator2 = initList.listIterator();

        while (iterator2.hasNext()) {
            System.out.println(iterator2.next());
        }
        System.out.println("==");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private static List<UserProfile> getList(int n) {
        List<UserProfile> list = new ArrayList<>();
        Random random = new Random();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            list.add(new UserProfile("name_" + i, random.nextInt()));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("created in " + (endTime - startTime) + " ms");
        return list;
    }

}

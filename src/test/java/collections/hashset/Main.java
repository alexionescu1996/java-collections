package collections.hashset;

import org.example.model.UserProfile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Main {

    private static HashSet<UserProfile> HASH_SET = new HashSet<>();

    @BeforeAll
    public static void init() {
        populate(HASH_SET, 5);
    }

    @Test
    public void ex1() {
        HashSet<String> set = new HashSet<>();
        set.add("123");
        System.out.println(set.size());
        set.add("123");

        System.out.println(set.size());
        set.add("123");
        set.remove("123");
        set.add("444");

        System.out.println(set.contains("444"));

        System.out.println(HASH_SET.size());

        System.out.println(HASH_SET.contains(new UserProfile("name_2", 1)));

        UserProfile test = new UserProfile("name_2");
        System.out.println(HASH_SET.contains(test) + " contine");

        for (UserProfile u : HASH_SET) {
            if (u.getName().equals("name_2")) {
                System.out.println(u);
            }
        }

        Iterator<UserProfile> iterator = HASH_SET.iterator();
        while (iterator.hasNext()) {
            UserProfile i = iterator.next();
            if (i.getName().equals("name_2")) {
                System.out.println(i);
            }
        }
    }

    @Test
    public void ex2() {
        long startTime = System.currentTimeMillis();

        HashSet<UserProfile> hashSet = new HashSet<>();
        populate(hashSet, 10_000_000);

        long endTime = System.currentTimeMillis();
        long hashSetTime = endTime - startTime;
        System.out.println(hashSetTime);

//        2994
//        hashset > linkedlist > arraylist
    }

    @Test
    public void ex3() {
        int[] nums = new int[]{1, 2, 2, 3, 3, 7, 7, 5, 5, 4};
        HashSet<Integer> hashSet = new HashSet<>();
        List<Integer> dupes = new ArrayList<>();

        for (int i : nums) {
            if (hashSet.contains(i)) {
                dupes.add(i);
            } else {
                hashSet.add(i);
            }
        }

        System.out.println(dupes);
    }

    @Test
    public void ex4() {
        HashSet<Integer> A = new HashSet<>(Arrays.asList(4, 3, 2));
        HashSet<Integer> B = new HashSet<>(Arrays.asList(1, 2, 3));
        HashSet<Integer> C = new HashSet<>(Arrays.asList(4, 5, 6, 7));

//        UNION
        A.addAll(B);
        System.out.println(A);

//        intersection
        A.retainAll(C);
        System.out.println(A);

//        difference
        C.removeAll(A);
        System.out.println(C);
    }

    @Test
    public void ex5() {
        System.out.println(HASH_SET);
    }

    @Test
    public void ex6() {
//        ArrayList<?> list = (ArrayList<?>) HASH_SET.stream().toList();
        ArrayList<UserProfile> list2 = new ArrayList<>(HASH_SET);
        System.out.println(list2);
        UserProfile u = new UserProfile("name_1", 141);
        list2.add(u);
        list2.add(u);
        list2.add(u);
        System.out.println(list2);

//        System.out.println(list.size());
        System.out.println(list2.size());

        HashSet<UserProfile> hashSet = new HashSet<>(list2);
        System.out.println(hashSet.size());
    }

    @Test
    public void ex7() {
        HashSet<UserProfile> hashSetCopy = new HashSet<>(HASH_SET);
        populate(hashSetCopy, 5);

        System.out.println(hashSetCopy.equals(HASH_SET));
        System.out.println(hashSetCopy.toString() + " " + HASH_SET.toString());

        assertEquals(hashSetCopy, HASH_SET);
    }

    @Test
    public void ex8() {
    /*     Shallow copy

        shallowCopySet este o un hashset nou, intr-o zona de memorie noua,
        dar cu referintele catre aceleasi obiecte din HASH_SET
    */

        UserProfile profile = new UserProfile("###", -1);
        HASH_SET.add(profile);

        HashSet<UserProfile> shallowCopySet = (HashSet<UserProfile>) HASH_SET.clone();
        profile.setName("Alice");


        HashSet<UserProfile> deepCopySet = new HashSet<>();
        for (UserProfile userProfile : HASH_SET) {
            deepCopySet.add(new UserProfile(userProfile.getName(), userProfile.getId()));
        }
        profile.setName("Alice name changed");

    }

    @Test
    public void ex9() {
        UserProfile result = HASH_SET
                .stream()
                .filter(userProfile -> userProfile.getName().equals("name_1"))
                .findAny()
                .orElse(null);

        System.out.println(result);
    }

    @Test
    public void ex10() {
        HashSet<UserProfile> subSet = (HashSet<UserProfile>) HASH_SET.stream()
                .skip(0) // the offset
                .limit(3) // how many items you want
                .collect(Collectors.toSet());

        System.out.println(HASH_SET.containsAll(subSet));
    }

    @Test
    public void ex11() {
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(3);

        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(1);
        linkedHashSet.add(2);
        linkedHashSet.add(3);

        System.out.println(hashSet);
        System.out.println(linkedHashSet);
    }

    public static void populate(HashSet<UserProfile> list, int n) {
        for (int i = 0; i < n; i++) {
            Random random = new Random();
            list.add(new UserProfile("name_" + i, random.nextInt(150)));
        }
    }
}

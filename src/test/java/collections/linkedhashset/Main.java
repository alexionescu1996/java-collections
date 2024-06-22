package collections.linkedhashset;

import org.example.model.CacheEntry;
import org.example.model.UserProfile;
import org.junit.jupiter.api.Test;

import java.util.*;

import static collections.arraylist.Main.performanceAL;
import static collections.hashset.Main.performanceHS;
import static collections.linkedlist.Main.performanceLL;

public class Main {


    @Test
    public void ex1() {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("Apple");
        linkedHashSet.add("Apple");
        linkedHashSet.add("Apple");

        linkedHashSet.add("Orange");
        linkedHashSet.add("Watermelon");
        linkedHashSet.add("Pear");

        System.out.println(linkedHashSet.size());

        linkedHashSet.remove("Apple");
        System.out.println(linkedHashSet);
    }

    @Test
    public void ex2() {
        performanceLHS();
    }

    public static void performanceLHS() {
        LinkedHashSet<UserProfile> linkedHashSet = new LinkedHashSet<>();
        long startTime = System.currentTimeMillis();

        populateSet(linkedHashSet, 10_000_000);
        long endTime = System.currentTimeMillis();

        System.out.println("LinkedHashSet: " + (endTime - startTime) + " ms " + linkedHashSet.size());
    }

    @Test
    public void testPerf() {
        performanceAL();
        performanceLL();
        performanceHS();
        performanceLHS();

//        ArrayList: 1241 ms
//        LinkedList: 2699 ms
//        HashSet: 3371 ms
//        LinkedHashSet: 3398 ms with String name comparison
//        LinkedHashSet: 728  ms with Integer id comparison

//        ArrayList: 1117 ms
//        LinkedList: 2715 ms
//        HashSet: 3428 ms
//        LinkedHashSet: 3665 ms

//          LHS > HS > LL > AL > LHS
    }

    @Test
    public void ex3() {
        LinkedHashSet<UserProfile> linkedHashSet = new LinkedHashSet<>();

        populateSet(linkedHashSet, 10);

        for (UserProfile userProfile : linkedHashSet) {
            System.out.println(userProfile);
        }
        System.out.println();

        HashSet<UserProfile> hashSet = new HashSet<>(linkedHashSet);
//        System.out.println(hashSet);
        for (UserProfile userProfile : hashSet) {
            System.out.println(userProfile);
        }
        System.out.println();

        ArrayList<UserProfile> arrayList = new ArrayList<>(linkedHashSet);
        for (UserProfile userProfile : arrayList) {
            System.out.println(userProfile);
        }

    }

    @Test
    public void LRUCache() {
        LinkedHashSet<CacheEntry> linkedHashSet = new LinkedHashSet<>();

        fill(linkedHashSet, 150_000);

        String inputToken = "tsofeagwej";
        CacheEntry testCE = new CacheEntry(inputToken, new Date(), new Date());
        linkedHashSet.add(testCE);

        updateWithForLoop(linkedHashSet, inputToken);

        Iterator<CacheEntry> it = linkedHashSet.iterator();
        updateWithIterator(it, inputToken, linkedHashSet);

        updateWithStream(inputToken, linkedHashSet);

//        System.out.println(linkedHashSet);
    }

    private static void updateWithStream(String inputToken, LinkedHashSet<CacheEntry> linkedHashSet) {
        long startTime = System.currentTimeMillis();

        CacheEntry searched = linkedHashSet
                .stream()
                .filter(cacheEntry -> cacheEntry.getToken().equals(inputToken))
                .findFirst().orElse(null);

        if (searched != null) {
            linkedHashSet.remove(searched);
            searched.setUpdatedOn(new Date());
            linkedHashSet.add(searched);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("stream: " + (endTime - startTime));
    }

    private static void updateWithIterator(Iterator<CacheEntry> it, String inputToken, LinkedHashSet<CacheEntry> linkedHashSet) {
        long startTime = System.currentTimeMillis();

        while (it.hasNext()) {
            CacheEntry updatedCacheEntry = it.next();
            if (updatedCacheEntry.getToken().equals(inputToken)) {
                it.remove();
                updatedCacheEntry.setUpdatedOn(new Date());
                linkedHashSet.add(updatedCacheEntry);
                break;
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("iterator : " + (endTime - startTime));
    }

    private static void updateWithForLoop(LinkedHashSet<CacheEntry> linkedHashSet, String inputToken) {
        long startTime = System.currentTimeMillis();

        for (CacheEntry it : linkedHashSet) {
            if (it.getToken().equals(inputToken)) {
                linkedHashSet.remove(it);

                it.setUpdatedOn(new Date());
                System.out.println(it.getUpdatedOn() + " " + it.getCreatedOn());

                linkedHashSet.add(it);

                break;
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("for loop: " + (endTime - startTime));
    }

    private static void fill(LinkedHashSet<CacheEntry> linkedHashSet, int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            String generatedString = getGeneratedString(random);
            CacheEntry cacheEntry = new CacheEntry(generatedString, new Date(), new Date());
            linkedHashSet.add(cacheEntry);
        }
    }

    private static String getGeneratedString(Random random) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }


    @Test
    public void ex4() {
        LinkedHashSet<CacheEntry> initialLHS = new LinkedHashSet<>();
        CacheEntry test = new CacheEntry("TESTING", null, null);
        initialLHS.add(test);
        fill(initialLHS, 5);

        LinkedHashSet<CacheEntry> shallowCopy = new LinkedHashSet<>(initialLHS);

        System.out.println(shallowCopy.size());

        LinkedHashSet<CacheEntry> deepCopy = new LinkedHashSet<>();
        Iterator<CacheEntry> iterator = initialLHS.iterator();

        while (iterator.hasNext()) {
            CacheEntry current = iterator.next();
            deepCopy.add(new CacheEntry(current.getToken(), current.getCreatedOn(), current.getUpdatedOn()));
        }

        test.setToken("###");
    }

    @Test
    public void ex5() {
        LinkedHashSet<CacheEntry> initialLHS = new LinkedHashSet<>();
        fill(initialLHS, 11);
        int startIndex = 0;
        List<LinkedHashSet<CacheEntry>> result = getSubLinkedHashSet(initialLHS, startIndex);
        System.out.println(result.size());
    }

    @Test
    public void ex6() {
        LinkedHashSet<Integer> initialLHS = new LinkedHashSet<>();
        for (int i = 0; i < 10; i++) {
            initialLHS.add(i);
        }

        HashSet<Integer> hashSet = new HashSet<>(initialLHS);
        LinkedList<Integer> list = new LinkedList<>(initialLHS);
        ArrayList<Integer> arrayList = new ArrayList<>(initialLHS);
    }



    private static List<LinkedHashSet<CacheEntry>> getSubLinkedHashSet(LinkedHashSet<CacheEntry> initialLHS, int startIndex) {
        List<LinkedHashSet<CacheEntry>> result = new ArrayList<>();
        for (int i = 0; i < initialLHS.size(); i++) {
            if (i % 3 == 0 && i > 1) {
                result.add(new LinkedHashSet<>(new ArrayList<>(initialLHS).subList(startIndex, i)));
                startIndex = i;
            }
        }
        for (LinkedHashSet<CacheEntry> iterator : result) {
            initialLHS.removeAll(iterator);
        }
        if (!initialLHS.isEmpty()) {
            result.add(new LinkedHashSet<>(initialLHS));
        }
        return result;
    }

    public static void populateSet(Set<UserProfile> set, int n) {
        for (int i = 0; i < n; i++) {
            Random random = new Random();
            set.add(new UserProfile("name_" + i, random.nextInt(150)));
        }
    }

}

package collections.linkedlist;

import org.example.model.UserProfile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Main {

    private static LinkedList<UserProfile> linkedList = new LinkedList<>();

    @BeforeAll
    public static void init() {
        populateList(linkedList, 15);
    }

    @Test
    public void ex1() {
        System.out.println(linkedList.size());
        linkedList.add(new UserProfile("test", -1));
        linkedList.add(new UserProfile("test2", -2));
        linkedList.remove(new UserProfile("test2", -2));

        System.out.println(linkedList);
    }

    @Test
    public void ex2() {
        performanceLL();
//        LinkedListTime >> ArrayList
    }

    public static void performanceLL() {

        long startTime = System.currentTimeMillis();

        LinkedList<UserProfile> linkedList = new LinkedList<>();
        populateList(linkedList, 10_000_000);

        long endTime = System.currentTimeMillis();
        long linkedListTime = endTime - startTime;

        System.out.println("LinkedList: " + linkedListTime + " ms");

    }

    @Test
    public void QUEUE_FIFO() {
        LinkedList<UserProfile> QUEUE = new LinkedList<>();
        populateList(QUEUE, 10);


        QUEUE.addLast(new UserProfile("test", -1)); // Enqueue
        System.out.println(QUEUE);


        QUEUE.removeFirst(); // Dequeue
        System.out.println(QUEUE);
    }

    @Test
    public void STACK_LIFO() {
        LinkedList<UserProfile> STACK = new LinkedList<>();
        populateList(STACK, 3);
        System.out.println(STACK);


        STACK.addFirst(new UserProfile("MISSING_NAME", -1)); // Push
        System.out.println(STACK);


        STACK.removeFirst(); // Pop
        System.out.println(STACK);


        System.out.println();


        STACK.push(new UserProfile("MISSING_NAME", -2)); // Another push
        STACK.pop(); // Another pop


        System.out.println(STACK);
    }

    @Test
    public void ex5() {
        LinkedList<UserProfile> STACK = new LinkedList<>();
        populateList(STACK, 5);

        STACK.pop(); // Another pop

        STACK.push(new UserProfile("MISSING_NAME", -2)); // Another push
        STACK.push(new UserProfile("MISSING_NAME", -2)); // Another push

        Iterator<UserProfile> iterator = STACK.descendingIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println(

        );

        iterator = STACK.listIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void ex6() {

        linkedList.add(3, new UserProfile("testing", 0));
        linkedList.remove(1);

    }

    @Test
    public void ex7() throws ClassCastException {

        @SuppressWarnings("unchecked")
        LinkedList<UserProfile> copy = (LinkedList<UserProfile>) linkedList.clone();

        linkedList.clear();

        System.out.println(copy);
        System.out.println(linkedList + " " + linkedList.size());

        LinkedList<UserProfile> anotherCopy = new LinkedList<>();
        Collections.addAll(anotherCopy, copy.toArray(new UserProfile[0]));

        System.out.println(anotherCopy.size());
        System.out.println(anotherCopy);
    }

    @Test
    public void ex8() {
        ArrayList<UserProfile> list = new ArrayList<>(linkedList);
        System.out.println(list.size());

        ArrayList<UserProfile> copyList = new ArrayList<>(list);
        list.addAll(copyList);
        System.out.println(list.size());

        HashSet<UserProfile> hashSet = new HashSet<>(list);
        System.out.println(hashSet.size());

//        Collections.reverse(linkedList);
    }


    public static void populateList(List<UserProfile> list, int n) {
        for (int i = 0; i < n; i++) {
            Random random = new Random();
            list.add(new UserProfile("name_" + i, random.nextInt(150)));
        }
    }
}

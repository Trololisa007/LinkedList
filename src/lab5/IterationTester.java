/**
 * This opens a text file whose path is given by args[0] and
 * makes a list of the unique words in the file.  The algorithm is
 * simple: at each step it gets  the next word in the file,d asks if
 * the list of  unique words contains it, and if not adds it to the list.
 * This is done 3 times: once with Java's ArrayList, once with MyLinkedList
 * where the contains method uses and iterator to walk through the elements
 * in the list, and once with MyLinkedList where contains uses get(i) to
 * walk through the list.  It prints the times each pass takes.
 */

package lab5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;

public class IterationTester {

    private static boolean contains_iteration(List<String> list, String word) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String w = it.next();
            if (word.equals(w))
                return true;
        }
        return false;
    }

    private static boolean contains_index(List<String> list, String word) {
        for (int i = 0; i < list.size(); i++) {
            String w = list.get(i);
            if (word.equals(w))
                return true;
        }
        return false;
    }

    private static void runTest(String test, File file, List<String> list, BiFunction<List<String>, String, Boolean> contains) {
        Scanner words = null;
        try {
            words = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(-1);
        }

        int totalWords = 0;
        long start = System.currentTimeMillis();

        while (words.hasNext()) {
            totalWords++;
            String w = words.next();
            if (!contains.apply(list, w)) {
                list.add(w);
            }
        }
        long time = System.currentTimeMillis() - start;

        words.close();
        System.out.printf("File: %s with %d total words.\n", file.getName(), totalWords);
        System.out.printf("The %s took %d milliseconds.\n", test, time);
        System.out.printf("It found %d unique words.\n", list.size());
    }

    public static void main(String[] args) {
        File file = new File(args[0]);

        // TEST 1
        List<String> list = new ArrayList<String>();
        runTest("ArrayList Test", file, list, IterationTester::contains_index);

        // TEST 2
        list = new MyLinkedList<String>();
        runTest("Iterator Test", file, list, IterationTester::contains_iteration);

        // Test 3
        list = new MyLinkedList<String>();
        runTest("Index Test", file, list, IterationTester::contains_index);
    }
}

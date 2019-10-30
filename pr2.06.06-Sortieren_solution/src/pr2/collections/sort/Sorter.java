package pr2.collections.sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Klasse zum Sortieren von Strings.
 */
public class Sorter {

    /**
     * Liefert einen Comparator für Strings.
     *
     * @param order Sortierreihenfolge.
     * @return Comparator, entsprechend der gewünschten Sortierreihenfolge.
     */
    private static Comparator<String> stringComparator(final SortOrder order) {
        return new MyComparator(order);
    }

    /**
     * Sortiert das übergebene Array entsprechend der gewünschten Reihenfolge.
     *
     * @param array das zu sortierende Array.
     * @param order die Sortierreihenfolge.
     */
    public static void sort(String[] array, SortOrder order) {
        Arrays.sort(array, stringComparator(order));
    }
}

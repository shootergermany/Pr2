package pr2.collections.sort;

import java.util.Comparator;

class MyComparator implements Comparator<String> {

    private final SortOrder order;

    public MyComparator(SortOrder order) {
        this.order = order;
    }

    /**
     * Vergleicht die Strings.
     *
     * @param o1 Erster String
     * @param o2 Zweiter String
     * @return Ergebnis des Vergleichs.
     */
    public int compare(String o1, String o2) {
        switch (this.order) {
        case ASCENDING:
            return o1.compareTo(o2);
        case DESCENDING:
            return -o1.compareTo(o2);
        case ASCENDING_CASE_INSENSITIVE:
            return o1.toUpperCase().compareTo(o2.toUpperCase());
        case DESCENDING_CASE_INSENSITIVE:
            return -o1.toUpperCase().compareTo(o2.toUpperCase());
        default:
            throw new IllegalArgumentException();
        }
    }
}

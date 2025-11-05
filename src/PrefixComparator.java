import java.util.Comparator;

/**
 * Factory pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 *
 *     Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 * @author owen astrachan
 * @date October 8, 2020
 * @date October 2025
 */
public class    PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
        return new PrefixComparator(prefix);
    }


    @Override
    /**
     * Use at most myPrefixSize characters from each of v and w
     * to return a value comparing v and w by words (.getWord()). 
     * Comparisons should be made based on the first 
     * (at most) myPrefixSize chars in v and w.
     * @return < 0 if v < w, == 0 if v == w, and > 0 if v > w
     */
    public int compare(Term v, Term w) {
        String vs = v.getWord();
        String ws = w.getWord();
        // only compare up to the prefix limit or shorter word
        int limit = Math.min(Math.min(vs.length(), ws.length()), myPrefixSize);
        for (int i = 0; i < limit; i++) {
            if (vs.charAt(i) != ws.charAt(i)) {
                return vs.charAt(i) - ws.charAt(i);
            }
        }
        if (limit == myPrefixSize) {
    // prefixes are equal up to the limit
    return 0;
} else if (vs.length() == ws.length()) {
    return 0;
} else if (vs.length() < ws.length()) {
    return -1;
} else {
    return 1;
}
}
}

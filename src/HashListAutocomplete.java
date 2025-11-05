import java.util.*;

public class HashListAutocomplete implements Autocompletor {

    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        if (terms.length != weights.length) {
            throw new IllegalArgumentException("Terms and weights must have same length");
        }
        initialize(terms, weights);
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        myMap = new HashMap<>();
        mySize = 0;
        for (int i = 0; i < terms.length; i++) {
            Term t = new Term(terms[i], weights[i]);
            int prefixLimit = Math.min(MAX_PREFIX, terms[i].length());
            for (int j = 0; j <= prefixLimit; j++) {
                String prefix = terms[i].substring(0, j);
                myMap.putIfAbsent(prefix, new ArrayList<>());
                myMap.get(prefix).add(t);
            }
        }

        // sort each list by descending weight
        for (String prefix : myMap.keySet()) {
            List<Term> list = myMap.get(prefix);
            list.sort(Comparator.comparingDouble(Term::getWeight).reversed());
        }

        // estimate memory size
        Set<String> countedKeys = new HashSet<>();
        for (Map.Entry<String, List<Term>> entry : myMap.entrySet()) {
            String key = entry.getKey();
            if (!countedKeys.contains(key)) {
                mySize += BYTES_PER_CHAR * key.length();
                countedKeys.add(key);
            }
            for (Term t : entry.getValue()) {
                mySize += BYTES_PER_CHAR * t.getWord().length() + BYTES_PER_DOUBLE;
            }
        }
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        if (prefix.length() > MAX_PREFIX) {
            prefix = prefix.substring(0, MAX_PREFIX);
        }
        List<Term> all = myMap.get(prefix);
        if (all == null) {
            return new ArrayList<>();
        }
        return all.subList(0, Math.min(k, all.size()));
    }

    @Override
    public int sizeInBytes() {
        return mySize;
    }
}
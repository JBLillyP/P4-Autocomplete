import java.util.*;

/**
 * Facilitates using fast binary search with
 * a Comparator. The methods firstIndex and lastIndex
 * run in time ceiling(1 + log(n)) where n is size of list.
 * 
 * @author ola for framework
 * @author 201 student implementing firstIndex and lastIndex
 *
 */
public class BinarySearchLibrary {

	/**
	 * Return the index of the first object (smallest index)
	 * o in parameter "equal" to target, that is
	 * the first object o such that comp.compare(o,target) == 0
	 *
	 * @param list   is list of Items being searched
	 * @param target is Item searched for
	 * @param comp   how Items are compared for binary search
	 * @return smallest index k such that comp.compare(list.get(k),target) == 0
	 */
	public static <T> int firstIndexSlow(List<T> list,
			T target, Comparator<T> comp) {
		int index = Collections.binarySearch(list, target, comp);

		if (index < 0)
			return index;

		while (0 <= index && comp.compare(list.get(index), target) == 0) {
			index -= 1;
		}
		return index + 1;
	}

public static <T> int firstIndex(List<T> list, T target, Comparator<T> comp) {
    int n = list.size();
    if (n == 0) return -1;
    int low = -1, high = n - 1;
    while (low + 1 != high) {
        int mid = (low + high) / 2;
        int cmp = comp.compare(list.get(mid), target);
        if (cmp < 0) low = mid;
        else high = mid;
    }
    return comp.compare(list.get(high), target) == 0 ? high : -1;
}

public static <T> int lastIndex(List<T> list, T target, Comparator<T> comp) {
    int n = list.size();
    if (n == 0) return -1;
    int low = 0, high = n;
    while (low + 1 != high) {
        int mid = (low + high) / 2;
        int cmp = comp.compare(list.get(mid), target);
        if (cmp <= 0) low = mid;
        else high = mid;
    }
    return comp.compare(list.get(low), target) == 0 ? low : -1;
}
}

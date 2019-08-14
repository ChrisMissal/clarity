package skadistats.clarity.model.s2;

public class S2LongFieldPathFormat {

    private static final int[] BITS_PER_COMPONENT = { 11, 11, 11, 11, 11 };

    private static final long[] CLEAR_MASK = new long[BITS_PER_COMPONENT.length - 1];
    private static final long[] PRESENT_BIT = new long[BITS_PER_COMPONENT.length - 1];
    private static final long[] VALUE_SHIFT = new long[BITS_PER_COMPONENT.length];
    private static final long[] VALUE_MASK = new long[BITS_PER_COMPONENT.length];
    private static final long[] OFFSET = new long[BITS_PER_COMPONENT.length];
    private static final long PRESENT_MASK;

    static long set(long id, int i, int v) {
        return id & ~VALUE_MASK[i] | ((long)v + OFFSET[i]) << VALUE_SHIFT[i];
    }

    static int get(long id, int i) {
        return (int)(((id & VALUE_MASK[i]) >> VALUE_SHIFT[i]) - OFFSET[i]);
    }

    static long down(long id) {
        return id | PRESENT_BIT[last(id)];
    }

    static long up(long id, int n) {
        return id & CLEAR_MASK[last(id) - n];
    }

    static int last(long id) {
        return Long.bitCount(id & PRESENT_MASK);
    }

    static int hashCode(long id) {
        return Long.hashCode(id);
    }

    static int compareTo(long id1, long id2) {
        return Long.compare(id1, id2);
    }

    static {
        int bitCount = -1;
        for (int i = 0; i < S2LongFieldPathFormat.BITS_PER_COMPONENT.length; i++) {
            bitCount += S2LongFieldPathFormat.BITS_PER_COMPONENT[i] + 1;
        }
        int cur = bitCount;
        long presentMaskAkku = 0L;
        for (int i = 0; i < S2LongFieldPathFormat.BITS_PER_COMPONENT.length; i++) {
            S2LongFieldPathFormat.OFFSET[i] = i == 0 ? 1L : 0L;
            if (i != 0) {
                S2LongFieldPathFormat.CLEAR_MASK[i - 1] = (-1L << cur) & ((1L << bitCount) - 1);
                cur--;
                S2LongFieldPathFormat.PRESENT_BIT[i - 1] = 1L << cur;
                presentMaskAkku |= 1L << cur;
            }
            cur -= S2LongFieldPathFormat.BITS_PER_COMPONENT[i];
            S2LongFieldPathFormat.VALUE_SHIFT[i] = cur;
            S2LongFieldPathFormat.VALUE_MASK[i] = ((1L << S2LongFieldPathFormat.BITS_PER_COMPONENT[i]) - 1L) << cur;
        }
        PRESENT_MASK = presentMaskAkku;
    }

}

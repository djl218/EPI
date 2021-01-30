package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.BitSet;
import java.util.List;
import java.util.Iterator;

public class AbsentValueArray {

  public static int findMissingElement(Iterable<Integer> stream) {
    final int NUM_BUCKET = 1 << 16;
    int[] counter = new int[NUM_BUCKET];
    Iterator<Integer> s = stream.iterator();
    while (s.hasNext()) {
      int idx = s.next() >>> 16;
      ++counter[idx];
    }

    final int BUCKET_CAPACITY = 1 << 16;
    int candidateBucket = 0;
    for (int i = 0; i < NUM_BUCKET; ++i) {
      if (counter[i] < BUCKET_CAPACITY) {
        candidateBucket = i;
        break;
      }
    }

    BitSet candidates = new BitSet(BUCKET_CAPACITY);
    s = stream.iterator();
    while (s.hasNext()) {
      int x = s.next();
      int upperPartX = x >>> 16;
      if (candidateBucket == upperPartX) {
        int lowerPartX = ((1 << 16) - 1) & x;
        candidates.set(lowerPartX);
      }
    }

    for (int i = 0; i < BUCKET_CAPACITY; ++i) {
      if (!candidates.get(i)) {
        return (candidateBucket << 16) | i;
      }
    }
    return -1;
  }

  @EpiTest(testDataFile = "absent_value_array.tsv")
  public static void findMissingElementWrapper(List<Integer> stream)
      throws Exception {
    try {
      int res = findMissingElement(stream);
      if (stream.stream().filter(a -> a.equals(res)).findFirst().isPresent()) {
        throw new TestFailure(String.valueOf(res) + " appears in stream");
      }
    } catch (IllegalArgumentException e) {
      throw new TestFailure("Unexpected no missing element exception");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "AbsentValueArray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}

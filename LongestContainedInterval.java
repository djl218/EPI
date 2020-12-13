package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class LongestContainedInterval {
  @EpiTest(testDataFile = "longest_contained_interval.tsv")

  public static int longestContainedRange(List<Integer> A) {
    Set<Integer> unprocessedEntries = new HashSet<>(A);

    int maxIntervalSize = 0;
    while (!unprocessedEntries.isEmpty()) {
      int a = unprocessedEntries.iterator().next();
      unprocessedEntries.remove(a);

      int lowerBound = a - 1;
      while (unprocessedEntries.contains(lowerBound)) {
        unprocessedEntries.remove(lowerBound);
        --lowerBound;
      }

      int upperBound = a + 1;
      while (unprocessedEntries.contains(upperBound)) {
        unprocessedEntries.remove(upperBound);
        ++upperBound;
      }

      maxIntervalSize = Math.max(upperBound - lowerBound - 1, maxIntervalSize);
    }
    return maxIntervalSize;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LongestContainedInterval.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}

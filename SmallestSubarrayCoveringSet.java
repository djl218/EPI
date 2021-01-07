package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.*;

public class SmallestSubarrayCoveringSet {

  // Represent subarray by starting and ending indices, inclusive.
  private static class Subarray {
    public Integer start;
    public Integer end;

    public Subarray(Integer start, Integer end) {
      this.start = start;
      this.end = end;
    }
  }

  private static Integer getValueForFirstEntry(
          LinkedHashMap<String, Integer> m) {
    Integer result = null;
    for (Map.Entry<String, Integer> entry : m.entrySet()) {
      result = entry.getValue();
      break;
    }
    return result;
  }

  public static Subarray findSmallestSubarrayCoveringSet(Iterator<String> paragraph,
                                                         List<String> keywords) {
    LinkedHashMap<String, Integer> dict =
            new LinkedHashMap<>(keywords.size(), 1, true);
    for (String s : keywords) {
      dict.put(s, null);
    }
    int numStringsFromQueryStringsSeenSoFar = 0;

    Subarray result = new Subarray(-1, -1);
    int idx = 0;
    while (paragraph.hasNext()) {
      String s = paragraph.next();
      if (dict.containsKey(s)) {
        Integer it = dict.get(s);
        if (it == null) {
          numStringsFromQueryStringsSeenSoFar++;
        }
        dict.put(s, idx);

        if (numStringsFromQueryStringsSeenSoFar == keywords.size()) {
          if ((result.start == -1 && result.end == -1) ||
              idx - getValueForFirstEntry(dict) < result.end - result.start) {
            result.start = getValueForFirstEntry(dict);
            result.end = idx;
          }
        }
      }
      ++idx;
    }
    return result;
  }
  @EpiTest(testDataFile = "smallest_subarray_covering_set.tsv")

  public static int findSmallestSubarrayCoveringSetWrapper(
      TimedExecutor executor, Iterator<String> paragraph, List<String> keywords)
      throws Exception {
    Set<String> copy = new HashSet<>(keywords);

    Subarray result = executor.run(
        () -> findSmallestSubarrayCoveringSet(paragraph, keywords));

    if (result.start < 0 || result.start >= paragraph.size() ||
        result.end < 0 || result.end >= paragraph.size() ||
        result.start > result.end)
      throw new TestFailure("Index out of range");

    for (int i = result.start; i <= result.end; i++) {
      copy.remove(paragraph.get(i));
    }

    if (!copy.isEmpty()) {
      throw new TestFailure("Not all keywords are in the range");
    }
    return result.end - result.start + 1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SmallestSubarrayCoveringSet.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}

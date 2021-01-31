package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Set;
import java.util.HashSet;

public class IsStringPermutableToPalindrome {
  @EpiTest(testDataFile = "is_string_permutable_to_palindrome.tsv")

  public static boolean canFormPalindrome(String s) {
    Set<Character> charsWithOddFrequency = new HashSet<>();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (charsWithOddFrequency.contains(c)) {
        charsWithOddFrequency.remove(c);
      } else {
        charsWithOddFrequency.add(c);
      }
    }
    return charsWithOddFrequency.size() <= 1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsStringPermutableToPalindrome.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}

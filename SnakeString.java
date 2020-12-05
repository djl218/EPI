package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class SnakeString {
  @EpiTest(testDataFile = "snake_string.tsv")

  public static String snakeString(String s) {
    StringBuilder result = new StringBuilder();

    for (int i = 1; i < s.length(); i += 4) {
      result.append(s.charAt(i));
    }

    for (int i = 0; i < s.length(); i += 2) {
      result.append(s.charAt(i));
    }

    for (int i = 3; i < s.length(); i += 4) {
      result.append(s.charAt(i));
    }

    return result.toString();
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SnakeString.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}

package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class SortList {
  @EpiTest(testDataFile = "sort_list.tsv")

  public static ListNode<Integer> stableSortList(ListNode<Integer> L) {
    if (L == null || L.next == null) {
      return L;
    }

    ListNode<Integer> preSlow = null, slow = L, fast = L;
    while (fast != null && fast.next != null) {
      preSlow = slow;
      fast = fast.next.next;
      slow = slow.next;
    }

    if (preSlow != null) {
      preSlow.next = null;
    }
    return SortedListsMerge.mergeTwoSortedLists(stableSortList(L),
                                                stableSortList(slow));
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SortList.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}

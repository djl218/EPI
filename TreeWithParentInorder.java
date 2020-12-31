package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
import java.util.ArrayList;

public class TreeWithParentInorder {
  @EpiTest(testDataFile = "tree_with_parent_inorder.tsv")

  public static List<Integer> inorderTraversal(BinaryTree<Integer> tree) {
    BinaryTree<Integer> prev = null, curr = tree;
    List<Integer> result = new ArrayList<>();

    while (curr != null) {
      BinaryTree<Integer> next;
      if (curr.parent == prev) {
        if (curr.left != null) {
          next = curr.left;
        } else {
          result.add(curr.data);
          next = (curr.right != null) ? curr.right : curr.parent;
        }
      } else if (curr.left == prev) {
        result.add(curr.data);
        next = (curr.right != null) ? curr.right : curr.parent;
      } else {
        next = curr.parent;
      }
      prev = curr;
      curr = next;
    }
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeWithParentInorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}

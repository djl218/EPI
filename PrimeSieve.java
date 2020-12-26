package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class PrimeSieve {
  @EpiTest(testDataFile = "prime_sieve.tsv")
  // Given n, return all primes up to and including n.
  public static List<Integer> generatePrimes(int n) {
    if (n < 2) return Collections.emptyList();

    final int size = (int)Math.floor(0.5 * (n - 3) + 1);
    List<Integer> primes = new ArrayList<>();
    primes.add(2);
    List<Boolean> isPrime = new ArrayList<>(Collections.nCopies(size, true));
    for (long i = 0; i < size; ++i) {
      if (isPrime.get((int)i)) {
        int p = (((int)i * 2) + 3);
        primes.add(p);
        for (long j = ((i * i) * 2) + 6 * i + 3; j < size; j += p) {
          isPrime.set((int)j, false);
        }
      }
    }
    return primes;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "PrimeSieve.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}

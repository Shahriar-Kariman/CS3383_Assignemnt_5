import java.util.ArrayList;
import java.util.List;

public class PalindromePartitioning {

  public static List<List<String>> partition(String s) {
    List<List<String>> results = new ArrayList<>();
    backtrack(s, 0, new ArrayList<>(), results);
    return results;
  }

  private static void backtrack(String s, int start, List<String> currentPartition, List<List<String>> results) {
    if (start == s.length()) {
      results.add(new ArrayList<>(currentPartition));
      return;
    }

    for (int end = start; end < s.length(); end++) {
      String subString = s.substring(start, end + 1);
      if (isPalindrome(subString)) {
        currentPartition.add(subString);
        backtrack(s, end + 1, currentPartition, results);
        currentPartition.remove(currentPartition.size() - 1); // Backtrack
      }
    }
  }

  private static boolean isPalindrome(String s) {
    int left = 0, right = s.length() - 1;
    while (left < right) {
      if (s.charAt(left) != s.charAt(right)) {
        return false;
      }
      left++;
      right--;
    }
    return true;
  }

  public static void main(String[] args) {
      String s = args[0];
      List<List<String>> partitions = partition(s);

      // Print the results
      for (List<String> partition : partitions) {
        System.out.println(partition);
      }
  }
}

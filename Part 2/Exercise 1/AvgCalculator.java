import java.util.*;

class EmptyArray extends Exception {}

class NegativeValuesFound extends Exception {
    private final List<Integer> invalidIndices;

    public NegativeValuesFound(List<Integer> indices) {
        this.invalidIndices = indices;
    }

    public List<Integer> getInvalidIndices() {
        return invalidIndices;
    }
}

public class AvgCalculator {

    // Modified avg() method
    public static float avg(int[] nums) throws EmptyArray, NegativeValuesFound {
        if (nums == null || nums.length == 0) {
            throw new EmptyArray();
        }

        List<Integer> negatives = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 0) {
                negatives.add(i); // store index
            }
        }

        if (!negatives.isEmpty()) {
            throw new NegativeValuesFound(negatives);
        }

        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        return (float) sum / nums.length;
    }

    // Helper method to add suffix for index display (1st, 2nd, etc.)
    public static String ordinal(int i) {
        if (i % 100 >= 11 && i % 100 <= 13) return i + "th";
        return switch (i % 10) {
            case 1 -> i + "st";
            case 2 -> i + "nd";
            case 3 -> i + "rd";
            default -> i + "th";
        };
    }

    public static void main(String[] args) {
        int[] nums1 = {1, -2, -3, 4}; // Example 1
        int[] nums2 = {1, 2, 3};      // Example 2
        int[] nums3 = {}; // Example 3

        runExample(nums1);
        System.out.println();
        runExample(nums2);
        System.out.println();
        runExample(nums3);
    }

    public static void runExample(int[] nums) {
        try {
            float result = avg(nums);
            System.out.println(result);
        } catch (EmptyArray e) {
            System.out.println("The array is empty.");
        } catch (NegativeValuesFound e) {
            for (int index : e.getInvalidIndices()) {
                System.out.printf("The %s number %d in your array is invalid%n",
                        ordinal(index + 1), nums[index]);
            }
        }
    }
}

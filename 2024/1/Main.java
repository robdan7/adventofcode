import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    /**
     * Move last number in the list to the index of the smallest one. You can now skip the last index.
     * @param list - Input array
     * @return the smallest number
     */
    private static int _copyLastToSmallest(Integer[] list, Integer len) {
        int smallestIndex = len-1;
        int result = list[smallestIndex];
        for (int i = 0; i < len; i++) {
            if (list[i] < list[smallestIndex]) smallestIndex = i;
        }
        if (smallestIndex != len -1) {
            result = list[smallestIndex];
            list[smallestIndex] = list[len-1];
            list[len-1] = result;   // Move smallest number to last position in the array.
        }
        return result;
    }

    /**
     * Find the total distance of numbers in two list. This pairs up the smallest numbers and calculates
     * the distance, then adds the distance to the result. Then add up the second-smallest numbers and so on.
     * @param listA - First list
     * @param listB - Second list
     * @return - The total distance between all number pairs.
     */
    private static int findTotalDistance(Integer[] listA, Integer[] listB) {
        if (listA.length != listB.length) throw new IllegalArgumentException("Input lists do not have matching lengths");

        if (listA.length == 0) throw new IllegalArgumentException("List lengths are 0");
        int accumulatedDist = 0;
        for(int i = listA.length; i > 0; i--) {
            int smallestA = Main._copyLastToSmallest(listA, i);
            int smallestB = Main._copyLastToSmallest(listB, i);
            int absResult = Math.abs(smallestA - smallestB);
            accumulatedDist += absResult;
        }

        return accumulatedDist;
    }

    public static void main(String[] args) {


        // Read file
        File file = new File("data.txt");
        Scanner reader = null;
        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<Integer> list1 = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<Integer>();
        while (reader.hasNext()) {
            String data = reader.nextLine();
            String[] splitData = data.split("[,\\.\\s]");

            try {
                list1.add(Integer.parseInt(splitData[0]));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            try {
                list2.add(Integer.parseInt(splitData[splitData.length-1]));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // Create arrays (not necessary, this was a quick fix for the file)
        Integer[] target = list1.toArray(new Integer[0]);
        Integer[] target2 = list2.toArray(new Integer[0]);

        // Calc result and print
        int result = Main.findTotalDistance(target,target2);
        System.out.println(result);
    }
}
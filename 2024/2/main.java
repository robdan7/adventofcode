import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<List<Integer>> input = Main._parseInputFile("day2.txt");

        System.out.println(Main.checkSafeReports(input));
    }

    /**
     * <html>
     * Count how many reports that are safe. A report is safe if the following are true:
     * <ul>
     * <li>The levels are either all increasing or all decreasing.</li>
     * <li>Any two adjacent levels differ by at least one and at most three.</li>
     * </ul>
     * </html>
     *
     * @param reports - A 2-dimensional list of numbers. Each row represents a report that
     *                can either be safe or unsafe.
     * @return - The total number of safe reports.
     */
    public static int checkSafeReports(List<List<Integer>> reports) {
        int safeReports = 0;
        for (int i = 0; i < reports.size(); i++) {
            safeReports += Main._reportIsSafe(reports.get(i)) ? 1 : 0;
        }
        return safeReports;
    }

    /**
     * <html>
     * Calculate if a single report is safe. The report is safe if the following are true:
     * <ul>
     * <li>The levels are either all increasing or all decreasing.</li>
     * <li>Any two adjacent levels differ by at least one and at most three.</li>
     * </ul>
     * </html>
     *
     * @param report - Current report.
     * @return - <b>True</b> if the current report is considered safe. <b>False</b> otherwise.
     */
    private static boolean _reportIsSafe(List<Integer> report) {

        // What should happen if the report is empty?? Not defined in task, but I'll keep this check here anyway for my own sanity.
        if (report.isEmpty()) {
            System.err.println("Report length is 0. Is this intended?");
            return false;
        }

        // This keeps track if the report values are decreasing or increasing.
        int derivative = 0;

        // Create bool flag. This will be changed to false if the report is unsafe.
        // Used instead of return statements in the middle of the code.
        boolean returnFlag = true;

        int previousValue = report.getFirst();
        int nextValue = 0;
        // Go through each value and check if it meets the requirements
        for (int i = 0; i < report.size(); i++) {
            if (i + 1 < report.size()) nextValue = report.get(i + 1);

            // Check if numbers are increasing or decreasing.
            if (report.get(i) > previousValue && derivative < 0) {
                returnFlag = false;
                break;
            } else if (report.get(i) < previousValue && derivative > 0) {
                returnFlag = false;
                break;
            }


            if (i > 0) {
                int prevDiff = Math.abs(report.get(i) - previousValue);
                if (prevDiff < 1 || prevDiff > 3) {
                    returnFlag = false;
                    break;
                }
            }
            if (i < report.size() - 1) {
                int nextDiff = Math.abs(report.get(i) - nextValue);
                if (nextDiff < 1 || nextDiff > 3) {
                    returnFlag = false;
                    break;
                }
            }

            // Finally prepare derivative and previous value for next iteration.
            // Note: nextValue is set before each iteration.
            derivative = report.get(i) - previousValue;
            previousValue = report.get(i);
        }

        return returnFlag;
    }

    /**
     * Prepare input file for this task.
     *
     * @param fileName - Name of the input file. This is assumed to be in the same directory as the executable.
     * @return - A 2-dimensional array of reports.
     */
    public static List<List<Integer>> _parseInputFile(String fileName) {
        File file = new File(fileName);
        Scanner reader = null;
        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        List<List<Integer>> input = new ArrayList<List<Integer>>();
        while (reader.hasNext()) {
            String data = reader.nextLine();
            String[] splitData = data.split("[,\\.\\s]");

            try {
                // TODO Map string to integer with lambda expression...?
                input.add(new ArrayList<Integer>());
                for (String number : splitData) {
                    input.getLast().add(Integer.parseInt(number));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return input;
    }

}

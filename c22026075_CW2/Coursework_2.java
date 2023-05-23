import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Coursework_2 {
    // WRITE ABOUT NOT BE ABLE TO REACH 500 WORDS IN REPORT
    public static void main(String[] args) {
        // Reads in file and removes stopwords
        ArrayList<String> result = deleteStopWords("input.txt", "stopwords.txt");

        // Measures performance for given amount of words
        //measureSortPerformance(result, 100);
        //measureSortPerformance(result, 200);
        //measureSortPerformance(result, result.size());
        for (String word : result){
            System.out.println(word);
        }


    }

    public static ArrayList<String> deleteStopWords(String filePath, String stopwordPath) {
        // Initialize an empty ArrayList
        ArrayList<String> result = new ArrayList<>();

        Set<String> stopwords = loadStopwords(stopwordPath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Reads each line
            while ((line = reader.readLine()) != null) {
                // Split each line up
                String[] words = line.split("\\s+");
                // If not stopwords add into ArrayList
                for (String word : words) {
                    if (!stopwords.contains(word.toLowerCase())) {
                        result.add(word);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Set<String> loadStopwords(String stopwordPath) {
        Set<String> stopwords = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(stopwordPath))) {
            String line;
            // Reads each line from stopwords file and adds to HashSet
            while ((line = reader.readLine()) != null) {
                stopwords.add(line.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stopwords;
    }


    public static int insertionSort(ArrayList<String> listofWords) {
        int insertionCount = 0;
        // Iterate over ArrayList
        for (int i = 1; i < listofWords.size(); i++) {
            //Sets key to current element
            String key = listofWords.get(i);
            int j = i - 1;
            // Iterates over sorted subarray to the left and moves elements to the right
            while (j >= 0 && listofWords.get(j).compareToIgnoreCase(key) > 0) {
                listofWords.set(j + 1, listofWords.get(j));
                insertionCount++;
                j--;
            }
            listofWords.set(j + 1, key);
        }
        return insertionCount;
    }

    public static int mergeSort(ArrayList<String> listofWords) {
        int moves = 0;

        // Base case
        if (listofWords.size() <= 1) {
            return 0;
        }

        // Divide List into left and right sublists
        int middle = listofWords.size() / 2;
        ArrayList<String> left = new ArrayList<>(listofWords.subList(0, middle));
        moves ++;
        ArrayList<String> right = new ArrayList<>(listofWords.subList(middle, listofWords.size()));
        moves ++;

        // Use recursion to sort sublists
        moves += mergeSort(left);
        moves += mergeSort(right);

        moves += merge(left, right);

        return moves;
    }

    public static int merge(ArrayList<String> left, ArrayList<String> right) {
        ArrayList<String> mergedList = new ArrayList<>();
        int i = 0;
        int j = 0;
        int moves = 0;

        // While both pointers are within sublists
        while (i < left.size() && j < right.size()) {
            // If element pointed to by left pointer is less than or equal to element by the right pointer
            if (left.get(i).compareToIgnoreCase(right.get(j)) <= 0) {
                // Add left element to the mergedList
                mergedList.add(left.get(i));
                i++; moves++;
            } else {
                // Add right element the the mergedList
                mergedList.add(right.get(j));
                j++; moves++;

            }
        }

        while (i < left.size()) {
            //While there are elements remaining in the left sublist - add them to mergedList
            mergedList.add(left.get(i));
            i++; moves++;
        }

        while (j < right.size()) {
            mergedList.add(right.get(j));
            j++; moves++;
        }

        return moves;
    }



    public static void measureSortPerformance(ArrayList<String> words, int num_of_words){
        // Creating sublist from 0 to number of words specified
        ArrayList<String> subList = new ArrayList<>(words.subList(0, Math.min(num_of_words, words.size())));

        // Create copy of sublist for insertion sort
        ArrayList<String> insertionSortedList = new ArrayList<>(subList);

        // Measure number of swaps in insertion sort
        int insertionSortSwaps = insertionSort(insertionSortedList);

        //Measure time taken to sort list using insertion
        long insertionSortStartTime = System.nanoTime();
        insertionSort(insertionSortedList);
        long insertionSortEndTime = System.nanoTime();
        long insertionSortTime = insertionSortEndTime - insertionSortStartTime;

        //Create copy of sublist for merge sort
        ArrayList<String> mergeSortedList = new ArrayList<>(subList);

        //Measure time taken to sort using merge sort
        long mergeSortStartTime = System.nanoTime();
        int mergeSortMoves = mergeSort(mergeSortedList);
        long mergeSortEndTime = System.nanoTime();
        long mergeSortTime = mergeSortEndTime - mergeSortStartTime;

        System.out.println("Performance of sorting the first " + num_of_words + " words:\n");
        System.out.println("Insertion sort time: " + insertionSortTime + " ns");
        System.out.println("Insertion number of swaps: " + insertionSortSwaps + "\n");

        System.out.println("Merge sort time: " + mergeSortTime + " ns");
        System.out.println("Merge sort number of moves: " + mergeSortMoves + "\n");
    }

}










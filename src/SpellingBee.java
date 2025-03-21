// Spelling Bee by Murohn Zhang
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Spelling Bee
 *
 * This program accepts an input of letters. It prints to an output file
 * all English words that can be generated from those letters.
 *
 * For example: if the user inputs the letters "doggo" the program will generate:
 * do
 * dog
 * doggo
 * go
 * god
 * gog
 * gogo
 * goo
 * good
 *
 * It utilizes recursion to generate the strings, mergesort to sort them, and
 * binary search to find them in a dictionary.
 *
 * @author Zach Blick, [ADD YOUR NAME HERE]
 *
 * Written on March 5, 2023 for CS2 @ Menlo School
 *
 * DO NOT MODIFY MAIN OR ANY OF THE METHOD HEADERS.
 */
public class SpellingBee {

    private String letters;
    private ArrayList<String> words;
    public static final int DICTIONARY_SIZE = 143091;
    public static final String[] DICTIONARY = new String[DICTIONARY_SIZE];

    public SpellingBee(String letters) {
        this.letters = letters;
        words = new ArrayList<String>();
    }

    // TODO: generate all possible substrings and permutations of the letters.
    //  Store them all in the ArrayList words. Do this by calling ANOTHER method
    //  that will find the substrings recursively.
    public void generate() {
        buildWords("", letters);
    }
    public void buildWords(String word, String letters) {
        // Base case: if we've gone through all of the letters in 'letters', add word to list and return
        if (letters.isEmpty()) {
            words.add(word);
            return;
        }

        // Otherwise, for each letter in 'letters' create a permutation and find substrings of that permutation
        for (int i = 0; i < letters.length(); i++) {
            // Builds the front section of the substring
            String front = word + letters.charAt(i);

            // Builds a substring with the remaining letters, pass this into 'build words' as 'letters' since
            // this is what we pull more letters from
            String back = letters.substring(0, i) + letters.substring(i + 1);

            // Call function again with new substrings to eventually build a permutation
            buildWords(front, back);
        }

        // Add new word to 'words'
        words.add(word);
    }


    // Sort all items in 'words'
    public void sort() {
        words = mergeSort(words, 0, words.size() - 1);
    }

    public ArrayList<String> mergeSort(ArrayList<String> words, int start, int end) {
        // Base case: list with 1 element in it
        if (start == end) {
            // Return new array with singular string
            ArrayList<String> single = new ArrayList<String>();
            single.add(words.get(start));
            return single;
        }
        // Find midpoint of current array (average of start and end)
        int mid = (start + end) / 2;

        // Create two new arraylists of the halved array, sort them
        ArrayList<String> arr1 = mergeSort(words, start, mid);
        ArrayList<String> arr2 = mergeSort(words, (mid + 1), end);

        // Merge the two sorted arrays together
        return merge(arr1, arr2);

    }

    public ArrayList<String> merge(ArrayList<String> arr1, ArrayList<String> arr2) {
        // Make a new arraylist to hold combined arrays
        ArrayList<String> combined = new ArrayList<String>();

        // Variables to go through both arrays
        int index1 = 0;
        int index2 = 0;

        // While we can still go through both arrays (both of them aren't empty)
        while (index1 < arr1.size() && index2 < arr2.size()) {
            // Add each index in both arrays to 'combined' in alphabetical order
            {
                if (arr1.get(index1).compareTo(arr2.get(index2)) <= 0) {
                    combined.add(arr1.get(index1));
                    index1++;
                }
                // No need for another check (can just use else) because we're only comparing between two
                // (it's either one or the other)
                else {
                    combined.add(arr2.get(index2));
                    index2++;
                }
            }
        }

        // Add rest of array that's still filled
        while (index1 < arr1.size()) {
            combined.add(arr1.get(index1));
            index1++;
        }

        while (index2 < arr2.size()) {
            combined.add(arr2.get(index2));
            index2++;
        }

        return combined;
    }


    // Removes duplicates from the sorted list.
    public void removeDuplicates() {
        int i = 0;
        while (i < words.size() - 1) {
            String word = words.get(i);
            if (word.equals(words.get(i + 1)))
                words.remove(i + 1);
            else
                i++;
        }
    }

    // Make sure all the words created are valid words in the dictionary
    public void checkWords() {
        // For each word in 'words', check if it's in the dictionary and if not, remove it
        for (int i = 0; i < words.size(); i++) {
            if (!check(words.get(i), 0, DICTIONARY_SIZE - 1)) {
                words.remove(i);
                i--;
            }
        }
    }

    public boolean check(String word, int start, int end) {
        // Find current middle
        int mid = (start + end) / 2;
        // Base case: if middle of dictionary is word
        if (DICTIONARY[mid].equals(word)) {
            return true;
        }

        // Or if it's split it down to one word and isn't equal to the word
        if (start == end) {
            return false;
        }

        // If it comes before in the dictionary
        if (word.compareTo(DICTIONARY[mid]) < 0) {
            // Call check again with the first half of the dictionary
            return check(word, start, mid);
        }
        // Otherwise call check with the second half
        return check(word, mid + 1, end);
    }

    // Prints all valid words to wordList.txt
    public void printWords() throws IOException {
        File wordFile = new File("Resources/wordList.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(wordFile, false));
        for (String word : words) {
            writer.append(word);
            writer.newLine();
        }
        writer.close();
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public SpellingBee getBee() {
        return this;
    }

    public static void loadDictionary() {
        Scanner s;
        File dictionaryFile = new File("Resources/dictionary.txt");
        try {
            s = new Scanner(dictionaryFile);
        } catch (FileNotFoundException e) {
            System.out.println("Could not open dictionary file.");
            return;
        }
        int i = 0;
        while(s.hasNextLine()) {
            DICTIONARY[i++] = s.nextLine();
        }
    }

    public static void main(String[] args) {

        // Prompt for letters until given only letters.
        Scanner s = new Scanner(System.in);
        String letters;
        do {
            System.out.print("Enter your letters: ");
            letters = s.nextLine();
        }
        while (!letters.matches("[a-zA-Z]+"));

        // Load the dictionary
        SpellingBee.loadDictionary();

        // Generate and print all valid words from those letters.
        SpellingBee sb = new SpellingBee(letters);
        sb.generate();
        sb.sort();
        sb.removeDuplicates();
        sb.checkWords();
        try {
            sb.printWords();
        } catch (IOException e) {
            System.out.println("Could not write to output file.");
        }
        s.close();
    }
}

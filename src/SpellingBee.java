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
        // YOUR CODE HERE — Call your recursive method!
        // new arraylist of strings [substrings]
        // words = generateSubstrings(substrings, 0, 0, letters)
        // new arraylist of permutations
        // generatePermutations(permutations, "", letters)
        // loop through every permuation
            // create new arraylist called permutation substring
            // set = to generateSubstrings(permutation substring, 0, 0, i)
    }
    // function to generate substrings
    public String[] generateSubstrings(){
        // take in first and last strings and array
        // base case: if first == word length, return array
        // base case 2: if last >= word length, restart but with new first +1, last = first + 1 (call generateSubstring again)
        // array.add (word.substring(first, last + 1);
        // return generateSubstring(first, last + 1);
    }
    // function to generate permutations (return void), takes in arraylist of strings and two other strings (current and word)
        // current = current permutation
        // word = what you want to generate permuations on
        // base case: if word length = 0
            // permutations.add(current)
            // return permutations
        // for loop through word length
            // create a new word, everything except for letter at i
            // generate permutations of new word, add i to current
            // for every substring in permutation substring
                // check if it's already in words
                // add to words


    // TODO: Apply mergesort to sort all words. Do this by calling ANOTHER method
    //  that will find the substrings recursively.
    public void sort() {
        words = mergeSort(words, 0, words.size());
    }

    public ArrayList<String> mergeSort(ArrayList<String> words, int start, int end) {
        // base case: list with 1 element in it
        if (start == end) {
            return words;
        }
        //
        ArrayList<String> arr1 = mergeSort(words, start, end / 2);
        ArrayList<String> arr2 = mergeSort(words, end / 2 + 1, end);
        // merge sorted lists LOOK AT DAILY CHECKS
        if (arr1.get(0).compareTo(arr2.get(0)) > 0) {

        }
        return words;
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

    // TODO: For each word in words, use binary search to see if it is in the dictionary.
    //  If it is not in the dictionary, remove it from words.
    public void checkWords() {
        // YOUR CODE HERE
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

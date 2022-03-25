/************************************************************

This project was created by Colin Safford on March 1st, 2022.
As I've been playing Wordle the past month or two, I've felt
compelled to make a program that solves the puzzle for me.

3-1: Solves the puzzle efficiently. Chooses word by selecting
most variety in letters, while still meeting the qualifications
of the result. Not sure how I will improve logic in the future.

 3-24: Added all words to accommodate players who are playing
 with different sized words. Hurts efficiency for regular
 Wordle game.


 ************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WordleBot {

    private static final ArrayList<String> allWords = new ArrayList<>();
    private static final ArrayList<Character> nots = new ArrayList<>();
    private static final ArrayList<Character> contains = new ArrayList<>();
    private static final ArrayList<Integer> positionOfContains = new ArrayList<>();
    private static final ArrayList<Character> musts = new ArrayList<>();
    private static final ArrayList<Integer> positionOfMusts = new ArrayList<>();
    private static int wordsLeft = allWords.size();

    /*
        Welcome to Wordle Bot! My goal is to solve
        the new, popular game "Wordle".
     */
    public static void main(String[] args) throws Exception {
        int numOfLetters = getAmountOfLetters();
        getAllWords(numOfLetters);
        wordsLeft = allWords.size();
        boolean wordFound = false;
        Word guess = getBestWord();

        while (!wordFound) {
            System.out.println("Guess: " + guess.getWord() + " (Words remaining: " + wordsLeft + ", " + allWords + ")");
            if (wordsLeft == 1) {
                System.out.println("Solved!");
                break;
            }
            System.out.print("Result: ");
            Result result = getResult();
            if (result.getGreenCount() == numOfLetters) {
                wordFound = true;
                System.out.println("Solved!");
            }
            update(guess, result);
            guess = getBestWord();
            musts.clear();
            positionOfMusts.clear();
        }

    }

    /*
        Gets the amount of letters that the user is guessing
        (5 or 6 letter word, for example)
     */
    public static int getAmountOfLetters() {
        System.out.println("How many letters you workin' with? ");
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }


    /*
        Gets all the words of a specific length
        and puts it into allWords[]
     */
    public static void getAllWords(int num) {
        try {
            File myObj = new File("src\\allWords.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String word = myReader.nextLine();
                if (word.length() == num) {
                    allWords.add(word);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /*
        This method gets a word that meets the result's
        qualifications, while also producing a word that
        has the most variety in letters.
     */
    public static Word getBestWord() throws Exception {
        String guess = "";
        int variety = 0;
        for (int i = 0; i < allWords.size(); i++) {

            if (meetsQualifications(allWords.get(i))) {
                if (Word.getVarietyOfLetters(allWords.get(i)) > variety) {
                    guess = allWords.get(i);
                    variety = Word.getVarietyOfLetters(allWords.get(i));
                }
            }
            else {
                i--;
            }
        }
        wordsLeft = allWords.size();
        if (guess.equals("")) {
            throw new Exception("No words match the result. You probably put the result in wrong :(.");
        }
        return new Word(guess);
    }

    /*
        This method checks whether a word meets the qualifications
        of the previous result.
     */
    public static boolean meetsQualifications(String word) {
        for (Character not : nots) {
            if (word.contains(not + "")) {
                allWords.remove(word);
                return false;
            }
        }
        for (int i = 0; i < musts.size(); i++) {
            if (word.charAt(positionOfMusts.get(i)) != musts.get(i)) {
                allWords.remove(word);
                return false;
            }
        }

        for (int i = 0; i < contains.size(); i++) {
            if (!(word.contains(contains.get(i) + "") && word.charAt(positionOfContains.get(i)) != contains.get(i))) {
                allWords.remove(word);
                return false;
            }
        }

        return true;
    }

    /*
        Gets the result from the user
     */
    public static Result getResult() {
        Scanner input = new Scanner(System.in);
        String res = input.nextLine();
        return new Result(res);
    }

    /*
        Updates information for the meetQualifications method.
     */
    public static void update(Word word, Result result) {
        for (int i = 0; i < result.getColors().size(); i++) {
            if (result.getColors().get(i) == '-') {
                nots.add(word.getWord().charAt(i));
            }
            else if (result.getColors().get(i) == 'o') {
                contains.add(word.getWord().charAt(i));
                positionOfContains.add(i);
            }
            else if (result.getColors().get(i) == 'g') {
                musts.add(word.getWord().charAt(i));
                positionOfMusts.add(i);
            }
        }
    }

}

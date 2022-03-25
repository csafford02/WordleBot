import java.util.ArrayList;

public class Word {

    private final ArrayList<Character> letters = new ArrayList<>();
    private final String word;
    private final int variety;

    /*
        Constructor to make a word
     */
    public Word(String word) {
        this.word = word;
        this.variety = getVarietyOfLetters(word);
        for (int i = 0; i < word.length(); i++) {
            letters.add(word.charAt(i));
        }
    }

    /*
        Get word
     */
    public String getWord() {
        return word;
    }

    /*
        Returns the amount of variety in the word (1-5)
     */
    public static int getVarietyOfLetters(String word) {
        ArrayList<Character> lettersInvolved = new ArrayList<>();
        int variety = 0;

        for (int i = 0; i < word.length(); i++) {
            if (!lettersInvolved.contains(word.charAt(i))) {
                variety++;
                lettersInvolved.add(word.charAt(i));
            }
        }
        return variety;
    }

    @Override
    public String toString() {
        return "Word{" +
                "letters=" + letters +
                ", word='" + word + '\'' +
                ", variety=" + variety +
                '}';
    }
}

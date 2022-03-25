import java.util.ArrayList;

public class Result {

    private final ArrayList<Character> colors = new ArrayList<>();
    private int greenCount = 0;

    /*
        Constructor for Result
     */
    public Result(String res) {
        for (int i = 0; i < res.length(); i++) {
            colors.add(res.charAt(i));
            if (res.charAt(i) == 'g') {
                greenCount++;
            }

        }
    }

    /*
        Returns the array for the individual result for each letter
     */
    public ArrayList<Character> getColors() {
        return colors;
    }

    /*
        Returns how many correct letters are in the correct location
     */
    public int getGreenCount() {
        return greenCount;
    }

    @Override
    public String toString() {
        return "Result{" +
                "colors=" + colors +
                ", greenCount=" + greenCount +
                '}';
    }


}

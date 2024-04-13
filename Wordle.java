import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class Wordle {
    private static final int USER_GUESS_AMOUNT = 6;
    private static final int ARR_SIZE = 2317;
    private static final int DICT_WORD_LENGTH = 5;
    private static final String[] TXT_WRD_LIST = new String[ARR_SIZE];
    private static final String[] TARGET_WRD_LIST = new String[DICT_WORD_LENGTH];

    public static void main(String[] args) throws IOException {

        readFile("dict.txt"); //read file
        createTargetWrdList(); //picked one random word then assigned it into TARGET_WRD_LIST array.

        // created an array named userGuessList with the words that user inputs.
        //[blush, awake, colon, react, paper, crazy]
        String[] userGuessList = new String[USER_GUESS_AMOUNT];
        for (int i = 0; i < USER_GUESS_AMOUNT; i++) {
            userGuessList[i] = args[i].toUpperCase();
        }

        // created 2D string array with the userGuess input's letters.
        //[[b, l, u, s, h], [a, w, a, k, e], [c, o, l, o, n], [r, e, a, c, t], [p, a, p, e, r], [c, r, a, z, y]]
        String[][] userGuessLettersArr = new String[userGuessList.length][];

        for (int i = 0; i < userGuessList.length; i++) {
            String guess = userGuessList[i];
            String[] chars = new String[guess.length()];
            for (int j = 0; j < guess.length(); j++) {
                chars[j] = String.valueOf(guess.charAt(j));
            }
            userGuessLettersArr[i] = chars;
        }


        // comparison process
        int attemptCount = 1;
        for (String[] userGuessWord : userGuessLettersArr) {

            // concatenate the letter in userGuessWord list to string.
            StringBuilder targetWordBuilder = new StringBuilder();
            for (String s : userGuessWord) {
                targetWordBuilder.append(s);
            }
            String targetWord = targetWordBuilder.toString();
            // writes the word that user gave us on the top.
            System.out.println("Try" + attemptCount + " (" +targetWord+ "):");


            /* firstly checks the letter of words
             if the length of word is not 5 throws an statement about the situation.
            */
            if (!isfiveletter(userGuessWord)) {
                System.out.println("The length of word must be five!");
                attemptCount++;
            }
            // checking if the word is in txt file.
            else if (!isInList(userGuessWord, TXT_WRD_LIST)) {
                System.out.println("Word does not exist in the dictionary!.");
                attemptCount++;
            }
            // if all situation is correct check te letters elementwise.
            else {
                for (int j = 0; j < userGuessWord.length; j++) {
                    boolean foundInRightPlace = false;
                    boolean foundInWrongPlace = false;
                    // comprise characters in the words element-wise
                    for (int k = 0; k < 5; k++) {
                        if (Objects.equals(userGuessWord[j], TARGET_WRD_LIST[k])) {
                            if (j == k) {
                                System.out.println(j + 1 + ". letter exists and located in the right place.");
                                foundInRightPlace = true;
                            } else {
                                System.out.println(j + 1 + ". letter exists but located in the wrong place.");
                                foundInWrongPlace = true;
                            }
                            break;
                        }
                    }
                    if (!foundInRightPlace && !foundInWrongPlace) {
                        System.out.println(j + 1 + ". letter does not exist.");
                    }
                }
                boolean guessedCorrectly = true; //guessed correctly flag
                for (int i = 0; i < 5; i++) {
                    if (!Objects.equals(userGuessWord[i], TARGET_WRD_LIST[i])) {
                        guessedCorrectly = false;
                        break;
                    }
                }
                if (guessedCorrectly) {
                    System.out.println("Congratulations! You guess right in " + whichShot(attemptCount) + " shot!");
                    break;
                }
                attemptCount++;

            }
        }
        if (attemptCount == 6) {
            System.out.print("You failed! The key word is ");
            for (String letter : TARGET_WRD_LIST) {
                System.out.print(letter);
            }
        } else {
            System.out.print("Correct word is ");
            for (String letter : TARGET_WRD_LIST) {
                System.out.print(letter);
            }
        }
    }

    public static String whichShot(int n) {
        return switch (n) {
            case 1 -> n + "st";
            case 2 -> n + "nd";
            case 3 -> n + "rd";
            case 4, 6, 5 -> n + "th";
            default -> null;
        };
    }

    public static void readFile(String filePath) throws IOException {
        // method for read txt file
        FileReader fileReader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fileReader);
        for (int i = 0; i < ARR_SIZE; i++) {
            TXT_WRD_LIST[i] = br.readLine();
        }
        //TXT_WRD_LIST=[CIGAR, REBUT, SISSY, HUMPH, AWAKE, BLUSH, FOCAL, EVADE, NAVAL,...]
        br.close();
    }

    public static String getRandomWord() {
        // POESY
        int randomNumber = (int) (Math.random() * ARR_SIZE);
        return TXT_WRD_LIST[randomNumber];
        //radnom wordu listeye döndürmesi için createTargetWRdList methoduna gönderdim.
    }

    public static void createTargetWrdList() {
        // created an array named targetWordList with the letter of the random word.
        // [P, O, E, S, Y]
        String targetWord = getRandomWord();
        for (int i = 0; i < DICT_WORD_LENGTH; i++) {
            TARGET_WRD_LIST[i] = String.valueOf(targetWord.charAt(i));
        }
    }

    public static boolean isfiveletter(String[] list) {
        if (list.length == 5) {
            return true;
        }
        return false;
    }

    public static boolean isInList(String[] charList, String[] wordList) {
        for (String word : wordList) {
            boolean found = true;
            for (String c : charList) {
                if (!word.contains(c)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return true;
            }
        }
        return false;
    }
}
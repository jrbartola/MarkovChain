package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Jesse on 4/11/17.
 */
public class MarkovModel {

    private HashMap<String, ArrayList<String>> model;

    public MarkovModel() {
        this.model = new HashMap<>();
        model.put("_begin", new ArrayList<String>());
        model.put("_end", new ArrayList<String>());
    }

    public MarkovModel(String input) {
        super();

    }

    /** Trains our Markov Model by adding words and their relative
     *  sequential occurrences into our state space
     *
     * @param input A string of words from which their frequencies
     *              are extracted and trained into our model
     */

    public void trainModel(String input) {

        String[] words = input.split(" ");
        String currentWord = "";

        for (int i = 0; i < words.length; i++) {

            if (i == 0 || words[i-1].contains(".")) {
                model.get("_begin").add(words[i]);
            } else if (i == words.length - 1 || words[i].contains(".")) {
                model.get("_end").add(words[i]);
            } else {
                model.putIfAbsent(currentWord, new ArrayList<String>());
                model.get(currentWord).add(words[i]);
            }

            currentWord = words[i];

        }
    }

    /** Generates a sentence based on frequencies gathered from
     *  training the MarkovModel
     *
     *  @param None
     */

    public String generateSentence() {
        // If our map is empty return the empty string
        if (model.size() == 0) {
            return "";
        }

        ArrayList<String> startWords = model.get("_begin");
        ArrayList<String> endWords = model.get("_end");

        // Retrieve a starting word to begin our sentence
        int rand = ThreadLocalRandom.current().nextInt(0, startWords.size());

        String currentWord = startWords.get(rand);
        StringBuilder sb = new StringBuilder();

        while (!endWords.contains(currentWord)) {
            sb.append(currentWord + " ");
            ArrayList<String> nextStates = model.get(currentWord);

            if (nextStates == null) return sb.toString().trim() + ".\n";

            rand = ThreadLocalRandom.current().nextInt(0, nextStates.size());
            currentWord = nextStates.get(rand);
        }

        sb.append(currentWord);

        return sb.toString() + "\n";


    }



}

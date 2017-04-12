package chain;

import model.MarkovModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Jesse Bartola on 4/11/17.
 */
public class MarkovChain {

    private MarkovModel markovModel;

    public MarkovChain() {
        markovModel = new MarkovModel();
    }

    /** Trains a Markov Chain model using frequencies of words from
     * a given text file
     *
     * @param f filename of the text used to train the Markov Chain model
     */

    public void feed(File f) {
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace(",","").replace("-", "");
                if (line.equals("") || line.equals(" ")) continue;
                markovModel.trainModel(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Generates a paragraph of sentences put together by state transitions
     *  based on the word frequencies contained in the Markov Chain model
     *
     * @param length The number of sentences to generate from the model
     */

    public String makeText(int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(markovModel.generateSentence());
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        File f = new File("src/main/java/trumpwin.txt");
        MarkovChain mc = new MarkovChain();
        mc.feed(f);
        System.out.println(mc.makeText(5));
    }

}

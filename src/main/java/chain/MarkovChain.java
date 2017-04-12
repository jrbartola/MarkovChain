package chain;

import model.MarkovModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Jesse on 4/11/17.
 */
public class MarkovChain {

    private MarkovModel markovModel;

    public MarkovChain() {
        markovModel = new MarkovModel();
    }

    public void feed(File f) {
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace(",","").replace("-", "");
                if (line.equals("")) continue;
                markovModel.trainModel(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String makeText(int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(markovModel.generateSentence());
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        File f = new File("src/main/java/sample.txt");
        MarkovChain mc = new MarkovChain();
        mc.feed(f);
        System.out.println(mc.makeText(5));
    }

}

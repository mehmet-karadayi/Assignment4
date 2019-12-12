/*
 MEHMET KARADAYI
 CISC 3130 - TY2
 ASSIGNMENT 4
 12/11/2019
 */
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyFileLoader;
import com.kennycason.kumo.palette.ColorPalette;

import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.List;


public class EnhancedAssignment3 {
    public static void main(String[] args) throws Exception {

        // Initial HashMap is created to be used store words (key) and their frequency (value)
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        Scanner in = new Scanner(new File("lyrics.txt"));
        PrintStream out = new PrintStream(new File("output.txt"));

        // The first word is retrieved from the file, converted to lowercase, and removed of any punctutation(if there are any)
        String word = in.next().toLowerCase().replaceAll("[^a-zA-Z ]", "");

        // The word is placed in the HashMap with the word itself being the key and its apperance (frequency) so far as having the value "1"
        map.put(word, 1);

        // Loop parses through the file and retrieves each word seperated by whitescape
        while (in.hasNext()) {
            word = in.next().toLowerCase().replaceAll("[^a-zA-Z ]", "");

            // The retrieved word is compared with the previous ones that are already in the map
            if (map.containsKey(word)) {
                int number = map.get(word) + 1; // If the HashMap already contains the word, then the frequency is increased by 1
                map.put(word, number); // The new value of the frequency is updated with the corresponding key
            } else
                map.put(word, 1); // This is the first occurrence of the word, it is added to the map and its value is 1
        }

        // A list object is created accepting Entry as its content, the previous hash-map is sent in as an Entry set
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());


        //Comparator class is implemented through Collections
        Comparator<Map.Entry<String, Integer>> comparator = new Comparator<Map.Entry<String, Integer>>() {

            // Compare function that provides a descending order
            public int compare(Map.Entry<String, Integer> value1, Map.Entry<String, Integer> value2) {
                return (value2.getValue()).compareTo(value1.getValue());
            }
        };

        //Sorts the list according to the order induced by the comparator above
        Collections.sort(list, comparator);
        //System.out.println(list);

        //For each item in the list, the loop prints out, to a text file, the ordered set of values and their key
        for (Map.Entry<String, Integer> entry : list) {
            out.print(entry.getValue() + ": " + entry.getKey() + "\n");
        }


        FrequencyFileLoader frequencyFileLoader = new FrequencyFileLoader();

        List<WordFrequency> wordFrequencies = frequencyFileLoader.load(new File ("output.txt"));

        Dimension dimension = new Dimension(340, 340);
        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        wordCloud.setBackground(new CircleBackground(170));
        wordCloud.setColorPalette(new ColorPalette(new Color(0xFF0707), new Color(0xFF8407), new Color(0xFFFF07), new Color(0x94FF07), new Color(0xEF07FF), new Color(0x07FADA)));
        wordCloud.setFontScalar(new SqrtFontScalar(5,60));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile("WordCloud.png");

    }
}



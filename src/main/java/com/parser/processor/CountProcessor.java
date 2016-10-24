package com.parser.processor;

import com.parser.counter.Counter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CSV com.parser.processor.
 *
 * @author vknysh
 */
public class CountProcessor {

    private Counter counter = Counter.getInstance();

    public void process(List<String> csvRecord) {
        /* Here, of course, should be used some basic cleanup of the comments to achieve more precise words count */
        Map<String, Integer> wordsCounter = new HashMap<>();
        String[] words = csvRecord.get(9).split(" ");
        for (String word : words) {
            wordsCounter.merge(word, 1, Integer::sum);
        }
        counter.addWords(wordsCounter);

        counter.addUser(csvRecord.get(2), csvRecord.get(3));
        counter.addProduct(csvRecord.get(1));
    }
}

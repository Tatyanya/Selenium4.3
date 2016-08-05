package com.epam.tam.module4.task3.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tatyana_Korobitsina on 8/2/2016.
 */
public class SentanceSeparator {

    private static final String SENTENCE_SEPARATOR = "(?<=(\\.|\\?|\\!))";

    private List<String> sentences = new ArrayList<String>();

    public SentanceSeparator(String value) {
        String[] elements = value.split(SENTENCE_SEPARATOR);
        for (String element : elements) {
            sentences.add(element);
        }
    }

    public List<String> getSentences() {
        return sentences;
    }

    public String getSentencesByID(int id) {
        return sentences.get(id);
    }

    public int getLegthOfSentenceByID(int id) {
        return getSentencesByID(id).length();
    }
}

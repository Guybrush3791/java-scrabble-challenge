package com.booleanuk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scrabble {

    String word = null;
    Map<Integer, List<Character>> letterScores = null;

    public Scrabble(String word) {

        this.word = word;

        letterScores = new HashMap<>();

        letterScores.put(0, List.of('{', '}', '[', ']'));
        letterScores.put(1, List.of('a', 'e', 'i', 'o', 'u', 'l', 'n', 'r', 's', 't'));
        letterScores.put(2, List.of('d', 'g'));
        letterScores.put(3, List.of('b', 'c', 'm', 'p'));
        letterScores.put(4, List.of('f', 'h', 'v', 'w', 'y'));
        letterScores.put(5, List.of('k'));
        letterScores.put(8, List.of('j' , 'x'));
        letterScores.put(10, List.of('q', 'z'));
    }

    public int score() {

        this.word = word.trim().toLowerCase();

        int wordMul = 1;

        int tempWordMul = 2;
        while (tempWordMul > 1) {

            tempWordMul = word.startsWith("{") && word.endsWith("}") && !(word.length() > 2 && word.charAt(2) == '}')
                ? 2
                : (word.startsWith("[") && word.endsWith("]") && !(word.length() > 2 && word.charAt(2) == ']') ? 3 : -1);

            if (tempWordMul > 1) {
                word = word.substring(1, word.length() - 1);
                wordMul *= tempWordMul;
            }
        }

        int score = 0;
        boolean inEscape = false;
        for (int x=0;x<word.length();x++) {

            char prevC = x > 0 ? word.charAt(x - 1) : ' ';
            char c = word.charAt(x);
            char nextC = x < word.length() - 1 ? word.charAt(x + 1) : ' ';

            if (!inEscape && (c == '}' || c == ']'))
                return 0;

            boolean found = false;
            for (int ls : letterScores.keySet()) {

                List<Character> lcs = letterScores.get(ls);

                if (lcs.contains(c)) {

                    if (prevC == '{')
                        if (nextC == '}') score += ls * 2;
                        else return 0;
                    else if (prevC == '[')
                        if (nextC == ']') score += ls * 3;
                        else return 0;
                    else
                        score += ls;

                    found = true;
                    inEscape = (ls == 0) != inEscape;

                    break;
                }

            }

            if (!found) return 0;
        }

        return score * wordMul;
    }

}

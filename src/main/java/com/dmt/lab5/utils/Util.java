package com.dmt.lab5.utils;

import com.dmt.lab5.models.Column;

import java.util.LinkedList;

public class Util {

    public static void printTable(LinkedList<Column> inputTable) {
        int maxCharacters = 0;
        for (Column item : inputTable) {
            maxCharacters = Math.max(maxCharacters, item.getCandidatesOrder().size());
        }

        for (Column item : inputTable) {
            System.out.print(item.getVotesNum() + "\t\t");
        }
        System.out.println();

        for (int i = 0; i < maxCharacters; i++) {
            for (Column item : inputTable) {
                if (i < item.getCandidatesOrder().size()) {
                    System.out.print(item.getCandidatesOrder().get(i) + "\t\t");
                } else {
                    System.out.print("\t\t");
                }
            }
            System.out.println();
        }
    }

    public static LinkedList<Column> deepCopy(LinkedList<Column> originalList) {
        LinkedList<Column> copiedList = new LinkedList<>();

        for (Column item : originalList) {
            Integer copiedValue = item.getVotesNum();

            LinkedList<Character> copiedSetOfCharacters = new LinkedList<>(item.getCandidatesOrder());

            Column copiedItem = new Column(copiedValue, copiedSetOfCharacters);
            copiedList.add(copiedItem);
        }
        return copiedList;
    }
}

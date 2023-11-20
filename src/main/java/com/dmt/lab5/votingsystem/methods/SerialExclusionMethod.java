package com.dmt.lab5.votingsystem.methods;

import com.dmt.lab5.models.Column;
import com.dmt.lab5.utils.Util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SerialExclusionMethod {

    private final LinkedList<Column> inputTable;

    public SerialExclusionMethod(LinkedList<Column> inputTable) {
        this.inputTable = inputTable;
        calculate();
    }

    public void calculate() {
        System.out.println("\n\n-----------метод послідовного виключення\n\n");

        for (int i = inputTable.size(); i >= 0; i--) {
            if(i == 0) {
                System.out.println("переможець: " + inputTable.getFirst().getCandidatesOrder().getFirst());
                return;
            }
            Util.printTable(inputTable);
            removeCharacterWithMinScore();
            System.out.println();
        }
    }

    private void removeCharacterWithMinScore() {
        Map<Character, Integer> scores = calculateScores();

        System.out.println("обчислені значення: " + scores);

        scores.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .ifPresent(this::removeCharacterFromTable);
    }

    private Map<Character, Integer> calculateScores() {
        Map<Character, Integer> scores = new HashMap<>();
        Set<Character> uniqueCharacters = getUniqueCharacters();

        uniqueCharacters.forEach(character -> scores.put(character, 0));

        inputTable.forEach(item -> item.getCandidatesOrder().stream()
                .findFirst()
                .ifPresent(firstChar -> scores.merge(firstChar, item.getVotesNum(), Integer::sum)));

        return scores;
    }

    private Set<Character> getUniqueCharacters() {
        return inputTable.stream()
                .flatMap(item -> item.getCandidatesOrder().stream())
                .collect(Collectors.toSet());
    }

    private void removeCharacterFromTable(Character character) {
        inputTable.forEach(item -> item.getCandidatesOrder().removeIf(character::equals));
        System.out.println("видалений кандидат: " + character);
    }

}

package com.dmt.lab5.votingsystem.methods;

import com.dmt.lab5.models.Column;
import com.dmt.lab5.utils.Util;

import java.util.*;
import java.util.stream.Collectors;

public class TwoToursMethod {

    private final LinkedList<Column> inputTable;

    public TwoToursMethod(LinkedList<Column> inputTable) {
        this.inputTable = inputTable;
        calculate();
    }

    private Map<Character, Integer> calculateScores() {
        Map<Character, Integer> scores = new HashMap<>();

        Set<Character> uniqueCharacters = inputTable.stream()
                .flatMap(item -> item.getCandidatesOrder().stream())
                .collect(Collectors.toSet());

        uniqueCharacters.forEach(character -> scores.put(character, 0));

        inputTable.forEach(item -> {
            if (!item.getCandidatesOrder().isEmpty()) {
                scores.merge(item.getCandidatesOrder().getFirst(), item.getVotesNum(), Integer::sum);
            }
        });

        return scores;
    }

    private Optional<Character> getTopCandidate(Map<Character, Integer> scores) {
        return scores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    private Set<Character> getTopTwoCandidates(Map<Character, Integer> scores) {
        return scores.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .limit(2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private boolean isMajority(Character candidate, int totalWeight) {
        int count = inputTable.stream()
                .filter(item -> !item.getCandidatesOrder().isEmpty() && item.getCandidatesOrder().getFirst().equals(candidate))
                .mapToInt(Column::getVotesNum)
                .sum();

        return count > totalWeight / 2;
    }

    public void calculate() {

        System.out.println("\n\n-----------метод два тури\n");

        Map<Character, Integer> scores = calculateScores();

        System.out.println("перший тур\n");
        Util.printTable(inputTable);
        System.out.println("\nобчислені значення: " + scores);

        Optional<Character> topCandidate = getTopCandidate(scores);
        int totalWeight = inputTable.stream().mapToInt(Column::getVotesNum).sum();

        if (topCandidate.isPresent() && isMajority(topCandidate.get(), totalWeight)) {
            System.out.println("\nдля визначення переможця достатньо одного туру. переможець: " + topCandidate.get());
        } else {

            System.out.println("\nдругий тур");

            Set<Character> topTwoCandidates = getTopTwoCandidates(scores);

            System.out.println("\nкандидати, що пройшли до другого туру: " + topTwoCandidates);
            System.out.println();

            inputTable.forEach(item -> item.getCandidatesOrder().removeIf(candidate -> !topTwoCandidates.contains(candidate)));

            Util.printTable(inputTable);

            scores = calculateScores();
            topCandidate = getTopCandidate(scores);

            System.out.println("\nобчислені значення: " + scores);

            topCandidate.ifPresent(character -> System.out.println("\nпереможець: " + character));

        }
    }

}

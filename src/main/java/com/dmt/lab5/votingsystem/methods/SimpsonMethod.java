package com.dmt.lab5.votingsystem.methods;

import com.dmt.lab5.models.Column;

import java.util.*;
import java.util.stream.Collectors;

public class SimpsonMethod {

    private final LinkedList<Column> inputTable;

    public SimpsonMethod(LinkedList<Column> inputTable) {
        this.inputTable = inputTable;
        calculate();
    }

    public void calculate() {
        System.out.println("\n\n-----------метод сімпсона\n\n");

        Map<Character, LinkedList<Integer>> advantageValue = new HashMap<>();

        Set<Character> uniqueCharacters = getSetOfUniqueCharacters();

        Set<AbstractMap.SimpleEntry<Character, Character>> uniquePairs = getAllUniquePairs(uniqueCharacters);

        uniquePairs.forEach(pair -> pairComparison(pair.getKey(), pair.getValue(), advantageValue));

        System.out.println("значення кандидатів, які переважають в парі при порівнянні: "+ advantageValue);

        AbstractMap.SimpleEntry<Character, Integer> largestOfSmallest = findLargestOfSmallestValues(advantageValue);

        System.out.println("\nпереможець: " + largestOfSmallest.getKey());
    }

    public void pairComparison(Character a, Character b, Map<Character, LinkedList<Integer>> advantageValue) {
        int aValue = 0, bValue = 0, i = 0;

        for (Column item : inputTable) {
            LinkedList<Character> value = item.getCandidatesOrder();
            Integer key = item.getVotesNum();

            int indexA = value.indexOf(a);
            int indexB = value.indexOf(b);

            if (indexA < indexB) {
                aValue += key;
            } else if (indexB < indexA) {
                bValue += key;
            }
            i++;
        }

        updateAdvantageValue(aValue, bValue, a, b, advantageValue);
    }

    private void updateAdvantageValue(int aValue, int bValue, Character a, Character b,
                                      Map<Character, LinkedList<Integer>> advantageValue) {
        if (aValue > bValue) {
            advantageValue.computeIfAbsent(a, k -> new LinkedList<>()).add(aValue);
        } else if (aValue < bValue) {
            advantageValue.computeIfAbsent(b, k -> new LinkedList<>()).add(bValue);
        }
    }

    private Set<Character> getSetOfUniqueCharacters() {
        return inputTable.stream()
                .flatMap(item -> item.getCandidatesOrder().stream())
                .collect(Collectors.toSet());
    }

    private Set<AbstractMap.SimpleEntry<Character, Character>> getAllUniquePairs(Set<Character> characters) {
        return characters.stream()
                .flatMap(a -> characters.stream()
                        .filter(b -> !a.equals(b))
                        .map(b -> a < b ? new AbstractMap.SimpleEntry<>(a, b) : new AbstractMap.SimpleEntry<>(b, a)))
                .collect(Collectors.toSet());
    }

    private static AbstractMap.SimpleEntry<Character, Integer> findLargestOfSmallestValues(Map<Character, LinkedList<Integer>> advantageValue) {
        System.out.println();

        AbstractMap.SimpleEntry<Character, Integer> result = null;
        int maxOfMin = Integer.MIN_VALUE;

        for (Map.Entry<Character, LinkedList<Integer>> entry : advantageValue.entrySet()) {
            int min = Integer.MAX_VALUE;
            for (Integer value : entry.getValue()) {
                if (value < min) {
                    min = value;
                }
            }

            System.out.println("S(" + entry.getKey() + ") = " + min);

            if (min > maxOfMin || result == null) {
                maxOfMin = min;
                result = new AbstractMap.SimpleEntry<>(entry.getKey(), min);
            }
        }

        return result;
    }

}

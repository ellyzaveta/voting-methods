package com.dmt.lab5.models;

import lombok.Data;

import java.util.LinkedList;

@Data
public class Column {
    private Integer votesNum;
    private LinkedList<Character> candidatesOrder;

    public Column(Integer value, LinkedList<Character> setOfCharacters) {
        this.votesNum = value;
        this.candidatesOrder = setOfCharacters;
    }

    @Override
    public String toString() {
        return votesNum + " " + candidatesOrder;
    }
}

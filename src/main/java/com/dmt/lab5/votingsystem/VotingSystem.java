package com.dmt.lab5.votingsystem;

import com.dmt.lab5.votingsystem.methods.SerialExclusionMethod;
import com.dmt.lab5.votingsystem.methods.SimpsonMethod;
import com.dmt.lab5.votingsystem.methods.TwoToursMethod;
import com.dmt.lab5.models.Column;
import com.dmt.lab5.utils.Util;

import java.util.LinkedList;

public class VotingSystem {
    private final LinkedList<Column> inputTable = new LinkedList<>();

    public void addColumn(Integer weight, LinkedList<Character> candidates) {
        inputTable.add(new Column(weight, candidates));
    }

    public void calculate() {
        new SerialExclusionMethod(Util.deepCopy(inputTable));
        new SimpsonMethod(Util.deepCopy(inputTable));
        new TwoToursMethod(Util.deepCopy(inputTable));
    }
}

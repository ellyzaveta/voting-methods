package com.dmt.lab5;

import com.dmt.lab5.votingsystem.VotingSystem;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.LinkedList;

@SpringBootApplication
public class Lab5Application {

	public static void main(String[] args) {

		VotingSystem votingSystem = new VotingSystem();

		votingSystem.addColumn(5, new LinkedList<>(Arrays.asList('a', 'b', 'c', 'd')));
		votingSystem.addColumn(4, new LinkedList<>(Arrays.asList('c', 'a', 'b', 'd')));
		votingSystem.addColumn(2, new LinkedList<>(Arrays.asList('b', 'a', 'c', 'd')));

		votingSystem.calculate();

	}

}

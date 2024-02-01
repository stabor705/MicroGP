package xyz.stabor.microgp.learning.fitness;

import java.util.List;
import java.util.stream.IntStream;

public class SquareDistance implements FitnessCalculator {
    @Override
    public double calculateFitness(List<Double> expectedOutputs, List<Double> realOutputs) {
            return -IntStream
                .range(0, expectedOutputs.size())
                .mapToDouble(i -> Math.pow(expectedOutputs.get(i) - realOutputs.get(i), 2))
                .sum();
    }
}

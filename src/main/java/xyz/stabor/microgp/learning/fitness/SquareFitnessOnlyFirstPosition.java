package xyz.stabor.microgp.learning.fitness;

import java.util.List;

public class SquareFitnessOnlyFirstPosition implements FitnessCalculator {
    @Override
    public double calculateFitness(List<Double> expectedOutputs, List<Double> realOutputs) {
        if (expectedOutputs.isEmpty() || realOutputs.isEmpty() || expectedOutputs.size() > 1) {
            return -9999;
        }
        double expectedValue = expectedOutputs.get(0);
        double realValue = realOutputs.get(0);

        return -Math.pow(expectedValue - realValue, 2);
    }
}

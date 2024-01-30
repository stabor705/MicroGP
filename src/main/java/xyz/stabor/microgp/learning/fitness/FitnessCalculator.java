package xyz.stabor.microgp.learning.fitness;

import java.util.List;

public interface FitnessCalculator {
    double calculateFitness(List<Double> expectedOutputs, List<Double> realOutputs);
}

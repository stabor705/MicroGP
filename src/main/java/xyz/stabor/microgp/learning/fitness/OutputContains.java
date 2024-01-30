package xyz.stabor.microgp.learning.fitness;

import java.util.List;

public class OutputContains implements FitnessCalculator {
    private Double num;

    public OutputContains(Double num) {
        this.num = num;
    }


    @Override
    public double calculateFitness(List<Double> expectedOutputs, List<Double> realOutputs) {
        return -realOutputs
                .stream()
                .filter(output -> !Double.isNaN(output) && !Double.isInfinite(output))
                .mapToDouble(output -> Math.sqrt(Math.pow(num - output, 2)))
                .min()
                .orElse(9999);
    }
}

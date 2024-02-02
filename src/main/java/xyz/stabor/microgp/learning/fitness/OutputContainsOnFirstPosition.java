package xyz.stabor.microgp.learning.fitness;

import java.util.List;

public class OutputContainsOnFirstPosition implements FitnessCalculator {
    private Double num;

    public OutputContainsOnFirstPosition(Double num) {
        this.num = num;
    }


    @Override
    public double calculateFitness(List<Double> expectedOutputs, List<Double> realOutputs) {
        if (realOutputs.isEmpty()) {
            return -9999;
        }

        double firstRealOutput = realOutputs.get(0);
        if (Double.isNaN(firstRealOutput) || Double.isInfinite(firstRealOutput)) {
            return -9999;
        }


        double squaredDifference = Math.sqrt(Math.pow(this.num - firstRealOutput, 2));
        return -squaredDifference;
    }
}

package xyz.stabor.microgp.adaptations.functions;

import xyz.stabor.microgp.adaptations.AdaptationInterface;

import java.util.List;

public class firstFunction implements AdaptationInterface {
    @Override
    public Double execute(List<Double> inputVector) {
        return null;
    }

    @Override
    public double calculateFitness(List<Double> inputVector, List<Double> outputVector) {
        return 0.0;
    }
}

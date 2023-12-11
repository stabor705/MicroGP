package xyz.stabor.microgp.adaptations;

import java.util.List;

public interface AdaptationInterface {

    Double execute(List<Double> inputVector);
    default double calculateFitness(List<Double> inputVector, List<Double> outputVector){
        double result = 0;
        for(int i = 0; i < inputVector.size(); ++i){
            result += Math.pow(inputVector.get(i) - outputVector.get(i), 2);
        }
        return Math.sqrt(result);
    }
}

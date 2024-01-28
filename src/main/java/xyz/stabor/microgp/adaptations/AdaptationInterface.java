package xyz.stabor.microgp.adaptations;

import xyz.stabor.microgp.geneticast.GeneticAST;

import java.util.ArrayList;
import java.util.List;

public interface AdaptationInterface {

    double calculateFitnessForProgram(String program);

    default List<Double> calculateFitness(List<GeneticAST> programs){
        List<Double> fitnessValues = new ArrayList<>();
        for (GeneticAST program : programs) {
            double fitness = this.calculateFitnessForProgram(program.toString());
            fitnessValues.add(fitness);
        }
        return fitnessValues;
    }

}

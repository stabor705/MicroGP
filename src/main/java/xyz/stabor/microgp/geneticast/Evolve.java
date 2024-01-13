package xyz.stabor.microgp.geneticast;

import org.apache.commons.lang3.SerializationUtils;
import xyz.stabor.microgp.adaptations.AdaptationInterface;
import xyz.stabor.microgp.adaptations.Tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Evolve {
    public static GeneticAST evolve(List<GeneticAST> programs, int numOfGenerations, AdaptationInterface adaptationFunction) {
        Random rng = new Random();
        int tournamentSize = 5;
        double crossProb = 0.5;
        double mutProb = 0.5;
        GeneticAST bestProgram = null;
        for (int generation = 0; generation < numOfGenerations; ++generation) {
            System.out.println("Generation: " + generation);
            List<Double> fitnessValues = adaptationFunction.calculateFitness(programs);
            if(fitnessValues.stream().anyMatch(value -> value == 1.0)){
//                System.out.println(fitnessValues);
            }
            bestProgram = programs.get(chooseBestFitness(fitnessValues));
//            System.out.println(fitnessValues);
            List<Integer> selectedIndices = Tournament.selectNewPopulation(fitnessValues, tournamentSize);
            List<GeneticAST> newPopulation = new ArrayList<>();
//            System.out.println(selectedIndices);
            for (Integer index : selectedIndices) {
                GeneticAST individual = SerializationUtils.clone(programs.get(index));
//                System.out.println("Individual: " + individual.toString());

                if (rng.nextDouble() < mutProb) {
                    individual = individual.mutate(new GenerationContext(6, 10, 10)); // adapt this
                }

                if (rng.nextDouble() < crossProb) {
                    int partnerIndex = rng.nextInt(programs.size());
                    GeneticAST partner = SerializationUtils.clone(programs.get(partnerIndex));
                    individual = individual.crossover(partner);
                }

                newPopulation.add(individual);
            }

            programs = newPopulation;
        }
        return bestProgram;
    }

    private static int chooseBestFitness(List<Double> fitness) {
        double bestFitness = Double.NEGATIVE_INFINITY;
        int bestIndex = -1;
        for (int i = 0; i < fitness.size(); ++i) {
            if (fitness.get(i) > bestFitness) {
                bestFitness = fitness.get(i);
                bestIndex = i;
            }
        }
        return bestIndex;
    }


}

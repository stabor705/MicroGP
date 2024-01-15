package xyz.stabor.microgp.geneticast;

import org.apache.commons.lang3.SerializationUtils;
import xyz.stabor.microgp.adaptations.AdaptationInterface;
import xyz.stabor.microgp.adaptations.Tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Evolve {
    public static GeneticAST evolve(List<GeneticAST> programs, int numOfGenerations, AdaptationInterface adaptationFunction, int maxConstVal) {
        Random rng = new Random();
        int tournamentSize = 2;
        double crossProb = 0.4;
        int bestProgramIndex;
        GeneticAST bestProgram = null;
        List<Double> fitnessValues;
        for (int generation = 0; generation < numOfGenerations; ++generation) {
            fitnessValues = adaptationFunction.calculateFitness(programs);
            System.out.println(fitnessValues);
            bestProgramIndex = chooseBestFitness(fitnessValues);
            bestProgram = programs.get(bestProgramIndex);
            System.out.println("Generation: " + generation + " BestFit: " + fitnessValues.get(bestProgramIndex));
            if(fitnessValues.get(bestProgramIndex) >= 1.0){
                break;
            }
            List<Integer> selectedIndices = Tournament.selectNewPopulation(fitnessValues, tournamentSize);
            System.out.println(selectedIndices);
            List<GeneticAST> newPopulation = new ArrayList<>();
            for (Integer index : selectedIndices) {
                GeneticAST individual = SerializationUtils.clone(programs.get(index));
                if (rng.nextDouble() < crossProb) {
                    int partnerIndex = rng.nextInt(programs.size());
                    GeneticAST partner = SerializationUtils.clone(programs.get(partnerIndex));
                    individual = individual.crossover(partner);
                }
                else{
                    individual = individual.mutate(new GenerationContext(5, 10, 5, maxConstVal)); // adapt this context
                }
                newPopulation.add(individual);
            }
            programs = newPopulation;
        }
        return bestProgram;
    }

    private static int chooseBestFitness(List<Double> fitness) {
        Random rand = new Random();
        double bestFitness = Double.NEGATIVE_INFINITY;
        int bestIndex = rand.nextInt(fitness.size());
        for (int i = 0; i < fitness.size(); ++i) {
            if (fitness.get(i) > bestFitness) {
                bestFitness = fitness.get(i);
                bestIndex = i;
            }
        }
        return bestIndex;
    }


}

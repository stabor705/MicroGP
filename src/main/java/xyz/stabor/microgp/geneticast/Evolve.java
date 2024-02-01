package xyz.stabor.microgp.geneticast;

import org.apache.commons.lang3.SerializationUtils;
import xyz.stabor.microgp.adaptations.AdaptationInterface;
import xyz.stabor.microgp.adaptations.Tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Evolve {
    private static final int TOURNAMENT_SIZE = 2;
    private static final double CROSSOVER_PROBABILITY = 0.4;
    private static final double MAX_FITNESS_THRESHOLD = 1.0;
    // Constants for GenerationContext
    private static final int GEN_CONTEXT_DEPTH = 5;
    private static final int GEN_CONTEXT_WIDTH = 10;
    private static final int GEN_CONTEXT_VARS = 5;

    public static GeneticAST evolve(List<GeneticAST> programs, int numOfGenerations, AdaptationInterface adaptationFunction, int maxConstVal) {
        Random rng = new Random();
        GeneticAST bestProgram = null;
        List<Double> fitnessValues;
        for (int generation = 0; generation < numOfGenerations; ++generation) {
            fitnessValues = adaptationFunction.calculateFitness(programs);
            System.out.println(fitnessValues);
            int bestProgramIndex = chooseBestFitness(fitnessValues);
            bestProgram = programs.get(bestProgramIndex);
            System.out.println("Generation: " + generation + " BestFit: " + fitnessValues.get(bestProgramIndex));
            if (fitnessValues.get(bestProgramIndex) >= MAX_FITNESS_THRESHOLD) {
                break;
            }
            List<Integer> selectedIndices = Tournament.selectNewPopulation(fitnessValues, TOURNAMENT_SIZE);
            System.out.println(selectedIndices);
            List<GeneticAST> newPopulation = createNewPopulation(selectedIndices, programs, rng, maxConstVal);
            programs = newPopulation;
        }
        return bestProgram;
    }

    private static List<GeneticAST> createNewPopulation(List<Integer> selectedIndices, List<GeneticAST> programs, Random rng, int maxConstVal) {
        List<GeneticAST> newPopulation = new ArrayList<>();
        for (Integer index : selectedIndices) {
            GeneticAST individual = SerializationUtils.clone(programs.get(index));
            if (rng.nextDouble() < CROSSOVER_PROBABILITY) {
                int partnerIndex = rng.nextInt(programs.size
                        ());
                GeneticAST partner = SerializationUtils.clone(programs.get(partnerIndex));
                individual.crossover(partner, new GenerationContext(GEN_CONTEXT_DEPTH, GEN_CONTEXT_WIDTH, GEN_CONTEXT_VARS, maxConstVal));
            } else {
                individual.mutate(new GenerationContext(GEN_CONTEXT_DEPTH, GEN_CONTEXT_WIDTH, GEN_CONTEXT_VARS, maxConstVal));
            }
            newPopulation.add(individual);
        }
        return newPopulation;
    }

    private static int chooseBestFitness(List<Double> fitness) {
        int bestIndex = 0;
        double bestFitness = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < fitness.size(); ++i) {
            if (fitness.get(i) > bestFitness) {
                bestFitness = fitness.get(i);
                bestIndex = i;
            }
        }
        return bestIndex;
    }
}
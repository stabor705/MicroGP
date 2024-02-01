package xyz.stabor.microgp.learning;

import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import xyz.stabor.microgp.MicroGPLexer;
import xyz.stabor.microgp.MicroGPParser;
import xyz.stabor.microgp.adaptations.Tournament;
import xyz.stabor.microgp.geneticast.GenerationContext;
import xyz.stabor.microgp.geneticast.GeneticAST;
import xyz.stabor.microgp.geneticast.variables.Program;
import xyz.stabor.microgp.interpreter.Interpreter;
import xyz.stabor.microgp.learning.fitness.FitnessCalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class LearningProcess {
    private List<List<Double>> inputs;
    private List<List<Double>> expectedOutputs;
    private FitnessCalculator fitnessCalculator;
    private GenerationContext generationContext;
    private int initialPopSize;
    private int maxGenerations;
    double maxBestFitness;

    private static double CROSSOVER_PROBABILITY = 0.4;
    private static double MUTATION_PROBABILITY = 0.5;
    private static int TOURNAMENT_SIZE = 2;


    public Report run() {
        List<GeneticAST> population = generatePopulation(initialPopSize);
        Report report = new Report();

        for (int i = 0; i < maxGenerations; i++) {
            System.out.println("Generation " + i);
            List<Double> scores = scorePopulation(population);
            List<GeneticAST> selected = select(population, scores);
            appendMetrics(population, report, scores);

            if (report.records.get(report.records.size() - 1).bestFitness() >= maxBestFitness) {
                break;
            }

            modify(selected);
        }
        return report;
    }

    private List<GeneticAST> generatePopulation(int size) {
        List<Program> population = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            population.add(Program.generate(generationContext));
        }
        return population.stream().map(GeneticAST::new).toList();
    }

    private List<GeneticAST> select(List<GeneticAST> population, List<Double> scores) {
        double min = Collections.min(scores);
        List<Double> scoresNormalized = scores.stream().map(score -> (score / min)).toList();
        List<Integer> newPopulationIndices = Tournament.selectNewPopulation(scoresNormalized, TOURNAMENT_SIZE);
        List<GeneticAST> newPopulation = new ArrayList<>();
        for (int idx : newPopulationIndices) {
            newPopulation.add(population.get(idx));
        }
        return newPopulation;
    }

    private void modify(List<GeneticAST> population) {
        Random rng = new Random();

        for (int i = 0; i < population.size(); i++) {
            if (rng.nextFloat() <= MUTATION_PROBABILITY) {
                population.get(i).mutate(generationContext);
            }
            if (rng.nextFloat() <= CROSSOVER_PROBABILITY) {
                int offset = 0;
                if ((rng.nextBoolean() && i < population.size() - 1) || i < 2) {
                    offset = rng.nextInt(1, population.size() - i);
                } else {
                    offset = -rng.nextInt(1, i);
                }
                population.get(i).crossover(population.get(i + offset), generationContext);
            }
        }
    }

    private void appendMetrics(List<GeneticAST> population, Report report, List<Double> scores) {
        double avgFitness = scores.stream().mapToDouble(e -> e).average().getAsDouble();
        double bestFitness = Collections.max(scores);
        GeneticAST bestIndividual = population.get(scores.indexOf(bestFitness));
        System.out.printf("Avg Fitness: %f, BestFitness: %f%n", avgFitness, bestFitness);
        report.appendRecord(avgFitness, bestFitness);
        report.setBestIndividual(bestIndividual);
    }

    private List<Double> scorePopulation(List<GeneticAST> population) {
        return population.stream().map(this::score).toList();
    }

    private double score(GeneticAST program) {
        double sum = 0;
        for (int i = 0; i < inputs.size(); i++) {
            List<Double> output = runProgram(program, inputs.get(i));
            while (output.size() < expectedOutputs.size()) {
                output.add(0.0);
            }
            double fitness = fitnessCalculator.calculateFitness(expectedOutputs.get(i), output);
            sum += fitness;
        }
        return sum;
    }

    private List<Double> runProgram(GeneticAST program, List<Double> input) {
        CharStream stream = CharStreams.fromString(program.toString());
        MicroGPLexer lexer = new MicroGPLexer(stream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        MicroGPParser parser = new MicroGPParser(commonTokenStream);
        MicroGPParser.ProgramContext ctx = parser.program();
        Interpreter interpreter = new Interpreter(input);
        interpreter.visitProgram(ctx);
        return interpreter.readOutput();
    }
}

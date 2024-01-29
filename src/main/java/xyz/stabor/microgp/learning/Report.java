package xyz.stabor.microgp.learning;

import xyz.stabor.microgp.geneticast.GeneticAST;
import xyz.stabor.microgp.geneticast.variables.Program;

import java.util.ArrayList;
import java.util.List;

public class Report {
    record Record(double avgFitness, double bestFitness) {}

    private List<Record> records = new ArrayList<>();
    private GeneticAST bestIndividual = null;

    public void appendRecord(double avgFitness, double bestFitness) {
        this.records.add(new Record(avgFitness, bestFitness));
    }

    public void setBestIndividual(GeneticAST bestIndividual) {
        this.bestIndividual = bestIndividual;
    }
}

package xyz.stabor.microgp.adaptations;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import xyz.stabor.microgp.MicroGPLexer;
import xyz.stabor.microgp.MicroGPParser;
import xyz.stabor.microgp.geneticast.GeneticAST;
import xyz.stabor.microgp.interpreter.Interpreter;

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

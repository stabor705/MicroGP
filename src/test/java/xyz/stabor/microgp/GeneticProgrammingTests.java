package xyz.stabor.microgp;

import org.junit.jupiter.api.Test;
import xyz.stabor.microgp.adaptations.AdaptationInterface;
import xyz.stabor.microgp.adaptations.functions.FirstFunction;
import xyz.stabor.microgp.adaptations.functions.SecondFunction;
import xyz.stabor.microgp.geneticast.Evolve;
import xyz.stabor.microgp.geneticast.GeneticAST;
import xyz.stabor.microgp.interpreter.Interpreter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeneticProgrammingTests {

    List<GeneticAST> initializePrograms(int numOfPrograms, int initalHeight){
        List<GeneticAST> resultList = new ArrayList<>();
        for(int i =0 ; i < numOfPrograms; ++i){
            resultList.add(GeneticAST.generate(initalHeight));
        }
        return resultList;
    }


    @Test
    void test11A() {
        int numOfGenerations = 5;
        double targetValue = 1.0;
        List<GeneticAST> programs = initializePrograms(100, 5);
        AdaptationInterface firstFunction = new FirstFunction();
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, firstFunction);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), List.of());
        System.out.println(output);
        assertTrue(output.contains(targetValue));
    }

    @Test
    void test11B() {
        int numOfGenerations = 20;
        double targetValue = 789.0;
        List<GeneticAST> programs = initializePrograms(10, 5);
        AdaptationInterface secondFunction = new SecondFunction();
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, secondFunction);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), List.of());
        System.out.println(output);
        assertTrue(output.contains(targetValue));
    }
}


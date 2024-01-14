package xyz.stabor.microgp.geneticast;

import org.junit.jupiter.api.Test;
import xyz.stabor.microgp.adaptations.AdaptationInterface;
import xyz.stabor.microgp.adaptations.functions.FirstFunction;
import xyz.stabor.microgp.adaptations.functions.FithFunction;
import xyz.stabor.microgp.adaptations.functions.FourthFunction;
import xyz.stabor.microgp.adaptations.functions.SecondFunction;
import xyz.stabor.microgp.interpreter.Interpreter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EvolveTest {

    List<GeneticAST> initializePrograms(int numOfPrograms, int initalHeight, int maxConstValue){
        List<GeneticAST> resultList = new ArrayList<>();
        for(int i =0 ; i < numOfPrograms; ++i){
            resultList.add(GeneticAST.generate(initalHeight, maxConstValue));
        }
        return resultList;
    }


    @Test
    void test11A() {
        int numOfGenerations = 10;
        double targetValue = 1.0;
        int maxConstValue = 5;
        List<GeneticAST> programs = initializePrograms(100, 5, maxConstValue);
        AdaptationInterface firstFunction = new FirstFunction();
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, firstFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), List.of());
        System.out.println(output);
        assertTrue(output.contains(targetValue));
    }

    @Test
    void test11B() {
        int numOfGenerations = 20;
        double targetValue = 789.0;
        int maxConstValue = 10;
        List<GeneticAST> programs = initializePrograms(10, 5, maxConstValue);
        AdaptationInterface secondFunction = new SecondFunction();
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, secondFunction,maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), List.of());
        System.out.println(output);
        assertTrue(output.contains(targetValue));
    }

    @Test
    void test11D() {
        int numOfGenerations = 10;
        double targetValue = 1.0;
        int maxConstValue = 5;
        List<GeneticAST> programs = initializePrograms(100, 5, maxConstValue);
        AdaptationInterface firstFunction = new FourthFunction();
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, firstFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), List.of());
        System.out.println(output);
        assertTrue(output.contains(targetValue));
    }

    @Test
    void test11E() {
        int numOfGenerations = 10;
        double targetValue = 1.0;
        int maxConstValue = 5;
        List<GeneticAST> programs = initializePrograms(100, 5, maxConstValue);
        AdaptationInterface firstFunction = new FithFunction();
        GeneticAST bestProgram = Evolve.evolve(programs, numOfGenerations, firstFunction, maxConstValue);
        System.out.println(bestProgram.toString());
        List<Double> output = Interpreter.interpret(bestProgram.toString(), List.of());
        System.out.println(output);
        assertTrue(output.contains(targetValue));
    }
}


package xyz.stabor.microgp.adaptations.functions;

import xyz.stabor.microgp.adaptations.AdaptationInterface;
import xyz.stabor.microgp.interpreter.Interpreter;

import java.util.List;

public class SixthFunction implements AdaptationInterface {
    public double calculateFitnessForProgram(String program) {
        List<Double> output = Interpreter.interpret(program, List.of(1.0, 2.0));

        if (output.size() == 1 && output.get(0) == 3.0) {
            return 1.0;
        } else {
            return 0;

        }
    }
}
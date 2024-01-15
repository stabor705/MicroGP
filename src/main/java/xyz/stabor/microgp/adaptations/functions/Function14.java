package xyz.stabor.microgp.adaptations.functions;

import xyz.stabor.microgp.adaptations.AdaptationInterface;
import xyz.stabor.microgp.interpreter.Interpreter;

import java.util.ArrayList;
import java.util.List;

public class Function14 implements AdaptationInterface {
    public double calculateFitnessForProgram(String program) {
        List<Double> output = Interpreter.interpret(program, input);
        if (output.size() == 1 && Math.abs(output.get(0) - targetValue) < 0.5) {
            return 1.0;
        } else {
            return 0;

        }
    }
    public void readInput(List<Integer> _input, double _targetValue){
        this.input = new ArrayList<>();
        for(Integer value : _input) {
            this.input.add(value.doubleValue());
        }
        targetValue = _targetValue;
    }

    Double targetValue;
    List<Double> input;
}
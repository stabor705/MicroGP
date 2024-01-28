package xyz.stabor.microgp.adaptations.functions;

import xyz.stabor.microgp.adaptations.AdaptationInterface;
import xyz.stabor.microgp.interpreter.Interpreter;

import java.util.ArrayList;
import java.util.List;

public class Function12_13_14 implements AdaptationInterface {
    public double calculateFitnessForProgram(String program) {
        List<Double> output = Interpreter.interpret(program, input);
        if (output.isEmpty() || output.contains(Double.NaN) || output.contains(Double.NEGATIVE_INFINITY) || output.contains(Double.POSITIVE_INFINITY)){
            return 0;
        }
        double difference = Math.abs(output.get(0) - targetValue);
        if (difference < 1.0 && output.size() == 1) {
            return 1.0;
        }
        else if(difference < 1.0 && output.size() > 1){
            return 0.99;
        }
        else {
            return 1.0 / (1.0 + difference + 0.01);
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
package xyz.stabor.microgp.adaptations.functions;

import xyz.stabor.microgp.adaptations.AdaptationInterface;
import xyz.stabor.microgp.interpreter.Interpreter;

import java.util.List;

public class Benchmark1 implements AdaptationInterface {
    public double calculateFitnessForProgram(String program) {
        List<Double> output = Interpreter.interpret(program, List.of(this.inputD, this.inputI.doubleValue()));
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

    public void readInput(Double _input1, Integer _input2, double _targetValue){
        targetValue = _input1 + _input2;
        this.inputD = _input1;
        this.inputI = _input2;
    }

    Double targetValue;
    Double inputD;
    Integer inputI;
}
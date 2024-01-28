package xyz.stabor.microgp.adaptations.functions;

import xyz.stabor.microgp.adaptations.AdaptationInterface;
import xyz.stabor.microgp.interpreter.Interpreter;

import java.util.ArrayList;
import java.util.List;

public class FunctionBoolean implements AdaptationInterface {
    private List<List<Boolean>> truthTable;
    private List<Boolean> expectedOutputs;

    public List<Boolean> getExpectedOutputs(){
        return expectedOutputs;
    }
    public FunctionBoolean(int k) {
        generateTruthTable(k);
    }

    private void generateTruthTable(int k) {
        int rows = (int) Math.pow(2, k);
        truthTable = new ArrayList<>(rows);
        expectedOutputs = new ArrayList<>(rows);

        for (int i = 0; i < rows; i++) {
            List<Boolean> row = new ArrayList<>(k);
            for (int j = 0; j < k; j++) {
                row.add((i & (1 << j)) != 0);
            }
            truthTable.add(row);
            expectedOutputs.add(generateExpectedOutput(row, k));
        }
    }

    private boolean generateExpectedOutput(List<Boolean> inputs, int k) {
        switch (k){
            case 1: return inputs.get(0);
            case 2: return inputs.get(0) && inputs.get(1);
            case 3: return inputs.get(0) && inputs.get(1) || inputs.get(2);
            case 4: return inputs.get(0) && inputs.get(1) || inputs.get(2) ^ inputs.get(3);
            case 5: return inputs.get(0) && inputs.get(1) || inputs.get(2) ^ inputs.get(3) || inputs.get(4);
            // ....etc
        }

        return false;
    }

    public double calculateFitnessForProgram(String program) {
        double fitness = 0;
        List<Double> output = Interpreter.interpret(program, List.of());
        for (int i = 0; i < expectedOutputs.size() && i < output.size(); i++) {
            if (output.contains(Double.NaN) || output.size() > expectedOutputs.size()) {
                continue;
            }
            boolean result = output.get(i) == 1.0;
            if (result == expectedOutputs.get(i)) {
                fitness += 1;
            }
        }
        return fitness / truthTable.size();
    }
}

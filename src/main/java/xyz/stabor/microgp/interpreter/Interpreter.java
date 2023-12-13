package xyz.stabor.microgp.interpreter;

import org.apache.commons.lang3.SerializationUtils;
import xyz.stabor.microgp.MicroGPBaseVisitor;

import java.util.*;

public class Interpreter extends MicroGPBaseVisitor<Void> {
    private List<Double> output = new ArrayList<>();
    private Queue<Double> input;
    private Map<String, Double> symbols = new HashMap<>();
    private int maxTime = 100;
    private int timer = 0;

    public Interpreter(List<Double> input) {
        this.input = new LinkedList<>(input);
    }

    public Interpreter(List<Double> input, int maxTime) {
        this.input = new LinkedList<>(input);
        this.maxTime = maxTime;
    }

    public List<Double> readOutput() {
        List<Double> out = new ArrayList<>(output);
        output.clear();
        return out;
    }
}

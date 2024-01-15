package xyz.stabor.microgp.geneticast.variables;

import xyz.stabor.microgp.geneticast.GenerationContext;
import xyz.stabor.microgp.geneticast.GeneticNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Expression extends GeneticNode {
    public static final int minHeight = 2;
    protected static List<Character> allowedOperationTypes = List.of('+', '-');
    private List<Character> operations = new ArrayList<>();
    protected Expression(List<GeneticNode> children, List<Character> operations) {
        super(children);
        this.operations = operations;
    }

    public static Expression generate(GenerationContext ctx) {
        List<GeneticNode> operands = new ArrayList<>();
        List<Character> operations = new ArrayList<>();
        Term lvalue = Term.generate(ctx.deeper());
        operands.add(lvalue);
        int numOfOperations = rng.nextInt(ctx.maxWidth());
        for (int i = 0; i < numOfOperations; i++) {
            operations.add(allowedOperationTypes.get(rng.nextInt(allowedOperationTypes.size())));
            operands.add(Term.generate(ctx.deeper()));
        }
        return new Expression(operands, operations);
    }

    @Override
    public String getTemplate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("%s");
        for (int i = 1; i < children.size(); i++) {
            stringBuilder.append(operations.get(i - 1));
            stringBuilder.append("%s");
        }
        return stringBuilder.toString();
    }
}

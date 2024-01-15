package xyz.stabor.microgp.geneticast.variables;

import xyz.stabor.microgp.geneticast.unions.Factors;
import xyz.stabor.microgp.geneticast.GenerationContext;
import xyz.stabor.microgp.geneticast.GeneticNode;

import java.util.ArrayList;
import java.util.List;

public class Term extends GeneticNode {
    public static final int minHeight = 1;
    protected static List<Character> allowedOperationTypes = List.of('*', '/');
    private List<Character> operations = new ArrayList<>();


    protected Term(List<GeneticNode> children, List<Character> operations) {
        super(children);
        this.operations = operations;
    }

    public static Term generate(GenerationContext ctx) {
        List<GeneticNode> operands = new ArrayList<>();
        List<Character> operations = new ArrayList<>();
        GeneticNode lvalue = Factors.getInstance().generate(ctx);
        operands.add(lvalue);
        int numOfOperations = rng.nextInt(ctx.maxWidth());
        for (int i = 0; i < numOfOperations; i++) {
            operations.add(allowedOperationTypes.get(rng.nextInt(allowedOperationTypes.size())));
            operands.add(Factors.getInstance().generate(ctx.deeper()));
        }
        return new Term(operands, operations);
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

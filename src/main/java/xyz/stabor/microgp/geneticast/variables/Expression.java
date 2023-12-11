package xyz.stabor.microgp.geneticast.variables;

import xyz.stabor.microgp.geneticast.GenerationContext;
import xyz.stabor.microgp.geneticast.GeneticNode;

import java.util.List;

public class Expression extends Arithmetic<Term> {
    public static final int minHeight = 2;

    protected Expression(List<GeneticNode> children) {
        super(children);
    }

    public static Expression generate(GenerationContext ctx) {
        return new Expression(List.of(Term.generate(ctx.deeper())));
    }

    @Override
    protected char[] getAllowedOperationTypes() {
        return new char[]{'+', '-'};
    }
}

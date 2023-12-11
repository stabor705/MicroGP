package xyz.stabor.microgp.geneticast.variables;

import xyz.stabor.microgp.geneticast.unions.Factors;
import xyz.stabor.microgp.geneticast.GenerationContext;
import xyz.stabor.microgp.geneticast.GeneticNode;

import java.util.List;

public class Term extends Arithmetic<GeneticNode> {
    public static final int minHeight = 1;

    protected Term(List<GeneticNode> children) {
        super(children);
    }

    public static Term generate(GenerationContext ctx) {
        return new Term(List.of(Factors.getInstance().generate(ctx.deeper())));
    }


    @Override
    protected char[] getAllowedOperationTypes() {
        return new char[]{'*', '/'};
    }
}

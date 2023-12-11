package xyz.stabor.microgp.geneticast.variables;

import xyz.stabor.microgp.geneticast.unions.Factors;
import xyz.stabor.microgp.geneticast.GenerationContext;
import xyz.stabor.microgp.geneticast.GeneticNode;

import java.util.List;

public class Assignment extends GeneticNode {
    public static final int minHeight = 3;

    protected Assignment(List<GeneticNode> children) {
        super(children);
    }

    public static Assignment generate(GenerationContext ctx) {
        return new Assignment(List.of(Factors.Identifier.generate(ctx.deeper()), Expression.generate(ctx.deeper())));
    }

    @Override
    protected String getTemplate() {
        return "%s = %s;";
    }
}

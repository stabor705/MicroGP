package xyz.stabor.microgp.geneticast.variables;

import xyz.stabor.microgp.geneticast.unions.Conditions;
import xyz.stabor.microgp.geneticast.GenerationContext;
import xyz.stabor.microgp.geneticast.GeneticNode;

import java.util.List;

public class While extends GeneticNode {
    static public final int minHeight = 4;

    protected While(List<GeneticNode> children) {
        super(children);
    }

    public static While generate(GenerationContext ctx) {
        return new While(List.of(Conditions.getInstance().generate(ctx.deeper()), Block.generate(ctx.deeper())));
    }

    @Override
    protected String getTemplate() {
        return "while %s %s";
    }
}

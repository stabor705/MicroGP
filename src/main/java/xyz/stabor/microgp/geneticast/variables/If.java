package xyz.stabor.microgp.geneticast.variables;

import xyz.stabor.microgp.geneticast.unions.Conditions;
import xyz.stabor.microgp.geneticast.GenerationContext;
import xyz.stabor.microgp.geneticast.GeneticNode;

import java.util.List;
import java.util.Random;

public class If extends GeneticNode {
    public static final int minHeight = 4;
    protected If(List<GeneticNode> children) {
        super(children);
    }

    public static If generate(GenerationContext ctx) {
        Random rng = new Random();
        if (rng.nextBoolean()) {
            return new If(List.of(Conditions.getInstance().generate(ctx.deeper()), Block.generate(ctx.deeper()), Block.generate(ctx.deeper())));
        } else {
            return new If(List.of(Conditions.getInstance().generate(ctx.deeper()), Block.generate(ctx.deeper())));
        }
    }

    @Override
    protected String getTemplate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("if %s %s");
        if (children.size() > 2) {
            stringBuilder.append("else %s");
        }
        return stringBuilder.toString();
    }
}

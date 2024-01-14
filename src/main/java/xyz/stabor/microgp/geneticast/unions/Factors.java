package xyz.stabor.microgp.geneticast.unions;

import xyz.stabor.microgp.geneticast.GenerationContext;
import xyz.stabor.microgp.geneticast.GeneticNode;
import xyz.stabor.microgp.geneticast.variables.Expression;
import xyz.stabor.microgp.geneticast.variables.LiteralGeneticNode;

import java.util.List;

public class Factors extends NodeTypeUnion {
    private static Factors instance;
    private Factors() {
        super(List.of(Number.class, Bool.class, Identifier.class, NestedExpression.class));
    }

    public static Factors getInstance() {
        if (instance == null) {
            instance = new Factors();
        }
        return instance;
    }

    static public class Number extends LiteralGeneticNode<Integer> {
        public static Number generate(GenerationContext ctx) {
            return new Number(rng.nextInt(ctx.maxConstValue()));
        }

        public Number(Integer num) {
            super(num);
        }
    }
    static public class Bool extends LiteralGeneticNode<Boolean> {
        public static Bool generate(GenerationContext ctx) {
            return new Bool(rng.nextBoolean());
        }
        public Bool(Boolean data) {
            super(data);
        }
    }
    static public class Identifier extends LiteralGeneticNode<String> {
        public static Identifier generate(GenerationContext ctx) {
            return new Identifier(String.format("$%d", rng.nextInt(ctx.maxVars())));
        }
        public Identifier(String data) {
            super(data);
        }
    }
    static public class NestedExpression extends GeneticNode {
        public static final int minHeight = 4;

        protected NestedExpression(List<GeneticNode> children) {
            super(children);
        }

        public static NestedExpression generate(GenerationContext ctx) {
            return new NestedExpression(List.of(Expression.generate(ctx.deeper())));
        }

        @Override
        protected String getTemplate() {
            return "(%s)";
        }
    }
}

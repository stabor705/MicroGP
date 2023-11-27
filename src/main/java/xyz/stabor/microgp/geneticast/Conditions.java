package xyz.stabor.microgp.geneticast;

import java.util.List;
import java.util.Random;

public class Conditions {
    static private NodeTypeUnion conditionsUnion = new NodeTypeUnion(List.of(
            Equality.class, JoinCondition.class, NegatedCondition.class, NestedCondition.class
    ));

    static class Equality extends GeneticNode {
        public static final int minHeight = 3;
        private String operand;

        static private final String[] operands = new String[]{
            "==", "!=", ">", "<", ">=", "<="
        };

        protected Equality(List<GeneticNode> children, String operand) {
            super(children);
            this.operand = operand;
        }

        static public Equality generate(GenerationContext ctx) {
            Random rng = new Random();
            String operand = operands[rng.nextInt(operands.length)];
            return new Equality(List.of(Expression.generate(ctx.deeper()), Expression.generate(ctx.deeper())), operand);
        }

        @Override
        protected String getTemplate() {
            return "%s " + operand + " %s";
        }
    }

    static class JoinCondition extends GeneticNode {
        public static final int minHeight = 3;
        private String operand;

        private static final String[] operands = new String[]{
            "&&", "||"
        };

        protected JoinCondition(List<GeneticNode> children, String operand) {
            super(children);
            this.operand = operand;
        }

        static public JoinCondition generate(GenerationContext ctx) {
            Random rng = new Random();
            String operand = operands[rng.nextInt(operands.length)];
            return new JoinCondition(List.of(Expression.generate(ctx.deeper()), Expression.generate(ctx.deeper())), operand);
        }

        @Override
        protected String getTemplate() {
            return "%s " + operand + " %s";
        }
    }

    static class NegatedCondition extends GeneticNode {
        public static final int minHeight = 4;
        protected NegatedCondition(List<GeneticNode> children) {
            super(children);
        }

        public static NegatedCondition generate(GenerationContext ctx) {
            return new NegatedCondition(List.of(Conditions.generate(ctx.deeper())));
        }

        @Override
        protected String getTemplate() {
            return "!%s";
        }
    }

    static class NestedCondition extends GeneticNode {
        public static final int minHeight = 4;

        protected NestedCondition(List<GeneticNode> children) {
            super(children);
        }

        public static NestedCondition generate(GenerationContext ctx) {
            return new NestedCondition(List.of(Conditions.generate(ctx.deeper())));
        }

        @Override
        protected String getTemplate() {
            return "(%s)";
        }
    }

    static public GeneticNode generate(GenerationContext ctx) {
        return conditionsUnion.generate(ctx);
    }
}

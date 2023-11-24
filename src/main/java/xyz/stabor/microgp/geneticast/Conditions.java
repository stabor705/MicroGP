package xyz.stabor.microgp.geneticast;

import java.util.List;
import java.util.Random;

public class Conditions {
    static private NodeTypeUnion conditionsUnion = new NodeTypeUnion(List.of(
            Equality.class, JoinCondition.class, NegatedCondition.class, NestedCondition.class
    ));

    static class Equality extends GeneticNode {
        private Expression left;
        private String operand;
        private Expression right;
        private Random rng = new Random();

        static private final String[] operands = new String[]{
            "==", "!=", ">", "<", ">=", "<="
        };

        public Equality() {
            this.operand = operands[rng.nextInt(operands.length)];
            this.left = new Expression();
            this.right = new Expression();
        }

        @Override
        public GeneticNode generateChild() {
            if (rng.nextBoolean()) {
                return this.left.generateChild();
            } else {
                return this.right.generateChild();
            }
        }

        @Override
        public String getText() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.left.getText());
            stringBuilder.append(this.operand);
            stringBuilder.append(this.right.getText());
            return stringBuilder.toString();
        }
    }

    static class JoinCondition extends GeneticNode {
        private Expression left;
        private String operand;
        private Expression right;

        private Random rng = new Random();

        private static final String[] operands = new String[]{
            "&&", "||"
        };

        public JoinCondition() {
            this.operand = operands[rng.nextInt(operands.length)];
            this.left = new Expression();
            this.right = new Expression();
        }

        @Override
        public GeneticNode generateChild() {
            if (rng.nextBoolean()) {
                return this.left.generateChild();
            } else {
                return this.right.generateChild();
            }
        }

        @Override
        public String getText() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.left.getText());
            stringBuilder.append(this.operand);
            stringBuilder.append(this.right.getText());
            return stringBuilder.toString();
        }
    }

    static class NegatedCondition extends GeneticNode {
        private GeneticNode condition;

        public NegatedCondition() {
            this.condition = generateRandom();
        }

        @Override
        public GeneticNode generateChild() {
            return this.condition.generateChild();
        }

        @Override
        public String getText() {
            return "!" + this.condition.getText();
        }
    }

    static class NestedCondition extends GeneticNode {
        private GeneticNode condition;

        public NestedCondition() {
            this.condition = generateRandom();
        }

        @Override
        public GeneticNode generateChild() {
            return this.condition.generateChild();
        }

        @Override
        public String getText() {
            return "(" + this.condition.getText() + ")";
        }
    }

    static public GeneticNode generateRandom() { return conditionsUnion.generateRandom(); }
}

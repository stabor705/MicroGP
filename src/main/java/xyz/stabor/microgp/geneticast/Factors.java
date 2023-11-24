package xyz.stabor.microgp.geneticast;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Factors {
    static private NodeTypeUnion factorsUnion = new NodeTypeUnion(
            List.of(Number.class, Bool.class, Identifier.class, NestedExpression.class)
    );

    static private Random rng = new Random();

    static public class Number extends LiteralGeneticNode<Integer> {
        public Number() {
            super(rng.nextInt());
        }

        public Number(Integer num) {
            super(num);
        }
    }
    static public class Bool extends LiteralGeneticNode<Boolean> {
        public Bool() {
            super(rng.nextBoolean());
        }
        public Bool(Boolean data) {
            super(data);
        }
    }
    static public class Identifier extends LiteralGeneticNode<String> {
        public Identifier() {
            super(UUID.randomUUID().toString().substring(0, 8));
        }
        public Identifier(String data) {
            super(data);
        }
    }
    static public class NestedExpression extends GeneticNode {
        private Expression expression;

        public NestedExpression() {
            this.expression = new Expression();
        }

        @Override
        public GeneticNode generateChild() {
            return expression.generateChild();
        }

        @Override
        public String getText() {
            return String.format("(%s)", expression.getText());
        }
    }

    static public GeneticNode generateRandom() {
        return factorsUnion.generateRandom();
    }
}

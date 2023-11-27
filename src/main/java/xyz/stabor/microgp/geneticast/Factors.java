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
        public static Number generate(GenerationContext ctx) {
            return new Number(rng.nextInt());
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
        private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        private static final int identifierLength = 8;
        public static Identifier generate(GenerationContext ctx) {
            return new Identifier(generateRandomName(identifierLength));
        }
        public Identifier(String data) {
            super(data);
        }

        private static String generateRandomName(int length) {
            StringBuilder stringBuilder = new StringBuilder();
            Random rng = new Random();
            for (int i = 0; i < length; i++) {
                stringBuilder.append(characters.charAt(rng.nextInt(characters.length())));
            }
            return stringBuilder.toString();
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

    static public GeneticNode generate(GenerationContext ctx) {
        return factorsUnion.generate(ctx);
    }
}

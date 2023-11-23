package xyz.stabor.microgp.geneticast;

import java.util.List;
import java.util.StringJoiner;

class Program extends Node {
    private List<Node> statements;
    private static NodeTypeUnion statementUnion = new NodeTypeUnion(List.of(Assignment.class, If.class));

    @Override
    public Node generateChild() {
        Node node = statementUnion.generateRandom();
        statements.add(node);
        return node;
    }

    @Override
    public String getText() {
        StringJoiner joiner = new StringJoiner("");
        for (Node statement : statements) {
            joiner.add(statement.getText());
        }
        return joiner.toString();
    }
}

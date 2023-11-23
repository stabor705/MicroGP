package xyz.stabor.microgp.geneticast;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class Assignment extends Node {
    private String identifier;
    private Expression expression;

    @Override
    public Node generateChild() {
        return expression.generateChild();
    }

    @Override
    public String getText() {
        return String.format("%s = %s;", identifier, expression.getText());
    }
}

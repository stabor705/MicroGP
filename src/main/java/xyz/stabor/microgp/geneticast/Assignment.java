package xyz.stabor.microgp.geneticast;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class Assignment extends GeneticNode {
    private Factors.Identifier identifier;
    private Expression expression;

    public Assignment() {
        this.identifier = new Factors.Identifier();
        this.expression = new Expression();
    }

    @Override
    public GeneticNode generateChild() {
        return expression.generateChild();
    }

    @Override
    public String getText() {
        return String.format("%s = %s;", identifier, expression.getText());
    }
}

package xyz.stabor.microgp.geneticast;

public class Print extends GeneticNode {
    private Expression expression;

    public Print() {
        this.expression = new Expression();
    }

    @Override
    public GeneticNode generateChild() {
        return this.expression.generateChild();
    }

    @Override
    public String getText() {
        return "print " + this.expression.getText() + " ;";
    }
}

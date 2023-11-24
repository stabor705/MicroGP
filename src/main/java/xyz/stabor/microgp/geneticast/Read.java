package xyz.stabor.microgp.geneticast;

public class Read extends GeneticNode {
    private Factors.Identifier targetIdentifier;
    public Read() {
        this.targetIdentifier = new Factors.Identifier();
    }

    @Override
    public GeneticNode generateChild() {
        return null;
    }

    @Override
    public String getText() {
        return "read " + targetIdentifier.getText() + " ;";
    }
}

package xyz.stabor.microgp.geneticast;

public class Expression extends Arithmetic<Term> {
    public Expression() {
        super(new Term());
    }

    public Expression(Term left) {
        super(left);
    }

    @Override
    protected char[] getAllowedOperationTypes() {
        return new char[]{'+', '-'};
    }

    @Override
    protected Term getGenerated() {
        return new Term();
    }
}

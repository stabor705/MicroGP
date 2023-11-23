package xyz.stabor.microgp;

import xyz.stabor.microgp.geneticast.Expression;

public class Main {
    public static void main(String[] args) {
        Expression expression = new Expression();
        expression.generateChild();
        expression.generateChild();
        expression.generateChild();
        expression.generateChild();
        System.out.println(expression.getText());
    }
}

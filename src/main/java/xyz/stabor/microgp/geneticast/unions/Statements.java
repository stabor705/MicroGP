package xyz.stabor.microgp.geneticast.unions;

import xyz.stabor.microgp.geneticast.variables.*;

import java.util.List;

public class Statements extends NodeTypeUnion {
    private static Statements instance;

    private Statements() {
        super(List.of(Assignment.class, If.class, While.class, Print.class, Read.class, Block.class));
    }

    public static Statements getInstance() {
        if (instance == null) {
            instance = new Statements();
        }
        return instance;
    }
}

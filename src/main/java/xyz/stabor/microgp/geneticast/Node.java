package xyz.stabor.microgp.geneticast;

import java.lang.reflect.Constructor;
import java.util.*;

public abstract class Node {
    private static final List<Class<?>> statementClasses = Arrays.asList(Assignment.class, If.class);

    public abstract Node generateChild();
    public abstract String getText();
}


package xyz.stabor.microgp.geneticast;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LiteralNode<T> extends Node {
    private T data;

    @Override
    public Node generateChild() {
        return null;
    }

    @Override
    public String getText() {
        return data.toString();
    }
}

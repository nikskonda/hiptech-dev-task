package by.nikskonda.devtask.model;

import java.util.ArrayList;
import java.util.List;

public class GraphNode<K, T> {
    private final K key;
    private final T object;

    private List<GraphNode<K, T>> neighbours = new ArrayList<>();

    public GraphNode(K key, T object) {
        this.key = key;
        this.object = object;
    }

    public K getKey() {
        return key;
    }

    public T getObject() {
        return object;
    }

    public List<GraphNode<K, T>> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<GraphNode<K, T>> neighbours) {
        this.neighbours = neighbours;
    }
}

package by.nikskonda.devtask.model;

import java.util.ArrayList;
import java.util.List;

public class GraphNode<T> {
    private final String key;
    private final T object;

    private List<GraphNode<T>> neighbours = new ArrayList<>();
    private boolean visited = false;
    private List<GraphNode<T>> path = new ArrayList<>();

    public GraphNode(String key, T object) {
        this.key = key;
        this.object = object;
    }

    public String getKey() {
        return key;
    }

    public T getObject() {
        return object;
    }

    public List<GraphNode<T>> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<GraphNode<T>> neighbours) {
        this.neighbours = neighbours;
    }

    public boolean isVisited() {
        return visited;
    }

    public void markVisited() {
        this.visited = true;
    }

    public List<GraphNode<T>> getPath() {
        return path;
    }

    public void addToPath(List<GraphNode<T>> previous, GraphNode<T> country) {
        path.addAll(previous);
        path.add(country);
    }

    public void reset() {
        path = new ArrayList<>();
        visited = false;
    }
}

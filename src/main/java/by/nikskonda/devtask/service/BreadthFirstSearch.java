package by.nikskonda.devtask.service;

import by.nikskonda.devtask.model.GraphNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

@Component
public class BreadthFirstSearch {

    public <T> List<T> search(Map<String, GraphNode<T>> mapOfNodes, String source, String destination) {
        mapOfNodes.values().forEach(GraphNode::reset);

        Queue<GraphNode<T>> queue = new LinkedList<>();

        GraphNode<T> start = mapOfNodes.get(source);
        start.markVisited();
        queue.add(start);

        while (!queue.isEmpty()) {
            GraphNode<T> head = queue.remove();

            if (head.getKey().equals(destination)) {
                List<T> result = head.getPath().stream().map(GraphNode::getObject).collect(Collectors.toList());
                result.add(head.getObject());
                return result;
            }
            for (GraphNode<T> borderCountry : head.getNeighbours()) {
                if (!borderCountry.isVisited()) {
                    borderCountry.markVisited();
                    queue.add(borderCountry);
                    borderCountry.addToPath(head.getPath(), head);
                }
            }
        }
        return new ArrayList<>();
    }
}
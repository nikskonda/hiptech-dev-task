package by.nikskonda.devtask.service;

import by.nikskonda.devtask.model.GraphNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@Component
public class BreadthFirstSearch {

    public <K, T> List<T> search(Map<K, ? extends GraphNode<K, T>> mapOfNodes, K source, K destination) {
        Queue<GraphNode<K, T>> queue = new LinkedList<>();
        Map<K, K> path = new HashMap<>(); //node key and key of node from which we came

        GraphNode<K, T> start = mapOfNodes.get(source);
        path.put(start.getKey(), null);
        queue.add(start);

        while (!queue.isEmpty()) {
            GraphNode<K, T> head = queue.remove();

            if (head.getKey().equals(destination)) {
                return findRoute(mapOfNodes, path, source, destination);
            }
            for (GraphNode<K, T> borderCountry : head.getNeighbours()) {
                if (!path.containsKey(borderCountry.getKey())) {
                    queue.add(borderCountry);
                    path.put(borderCountry.getKey(), head.getKey());
                }
            }
        }
        return new ArrayList<>();
    }

    private <K, T> List<T> findRoute(Map<K, ? extends GraphNode<K, T>> mapOfNodes, Map<K, K> path, K source, K destination) {
        Deque<GraphNode<K, T>> result = new LinkedList<>();
        result.add(mapOfNodes.get(destination));
        while (!source.equals(result.getFirst().getKey())) {
            K topKey = result.getFirst().getKey();
            K previousKey = path.get(topKey);
            result.addFirst(mapOfNodes.get(previousKey));
        }
        return result.stream().map(GraphNode::getObject).toList();
    }
}
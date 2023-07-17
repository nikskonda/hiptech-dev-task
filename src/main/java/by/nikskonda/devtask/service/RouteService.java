package by.nikskonda.devtask.service;

import by.nikskonda.devtask.exception.CountryNotFoundException;
import by.nikskonda.devtask.exception.NoLandCrossingException;
import by.nikskonda.devtask.model.Country;
import by.nikskonda.devtask.model.GraphNode;
import by.nikskonda.devtask.model.RouteResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private Map<String, GraphNode<Country>> mapOfCountryGraph;

    @Autowired
    private BreadthFirstSearch breadthFirstSearch;

    @PostConstruct
    private void loadDataFromJsonFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Country> mapOfCountries = mapper.readValue(
                            new File("src/main/resources/data.json"),
                            new TypeReference<List<Country>>() {
                            })
                    .stream()
                    .collect(Collectors.toMap(Country::getName, Function.identity()));

            mapOfCountryGraph = new HashMap<>();
            for (Country country : mapOfCountries.values()) {
                GraphNode<Country> graphNode = createNewGraphNodeOrReturnExisting(country);
                for (String borderCountry : country.getBorders()) {
                    GraphNode<Country> neighbourNode = createNewGraphNodeOrReturnExisting(mapOfCountries.get(borderCountry));
                    graphNode.getNeighbours().add(neighbourNode);
                }
            }
        } catch (IOException ex) {
            //logger
            ex.printStackTrace();
        }
    }

    private GraphNode<Country> createNewGraphNodeOrReturnExisting(Country country) {
        String key = country.getName();
        if (!mapOfCountryGraph.containsKey(key)) {
            mapOfCountryGraph.put(key, new GraphNode<>(key, country));
        }
        return mapOfCountryGraph.get(key);
    }

    public RouteResponse findMinCountryRoute(String source, String destination) {
        if (!StringUtils.hasLength(source) || !StringUtils.hasLength(destination)
                || !mapOfCountryGraph.containsKey(source) || !mapOfCountryGraph.containsKey(destination)) {
            throw new CountryNotFoundException("Please enter correct 'origin' or 'destination' country.");
        }
        List<Country> result = breadthFirstSearch.search(mapOfCountryGraph, source, destination);
        if (result.isEmpty()) {
            throw new NoLandCrossingException("There is no land crossing.");
        }
        RouteResponse routeResponse = new RouteResponse();
        routeResponse.setRoute(result.stream().map(Country::getName).toArray(String[]::new));
        return routeResponse;
    }
}
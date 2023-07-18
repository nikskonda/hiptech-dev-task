package by.nikskonda.devtask.service;

import by.nikskonda.devtask.exception.CountryNotFoundException;
import by.nikskonda.devtask.exception.NoLandCrossingException;
import by.nikskonda.devtask.model.Country;
import by.nikskonda.devtask.model.GraphNode;
import by.nikskonda.devtask.model.RouteResponse;
import by.nikskonda.devtask.repository.CountryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class RouteService {

    @Autowired
    private BreadthFirstSearch breadthFirstSearch;

    @Autowired
    private CountryData countryData;

    public RouteResponse findMinCountryRoute(String source, String destination) {
        Map<String, GraphNode<Country>> mapOfCountryGraph = countryData.getMapOfCountryGraph();

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
package by.nikskonda.devtask.service;

import by.nikskonda.devtask.exception.CountryNotFoundException;
import by.nikskonda.devtask.exception.NoLandCrossingException;
import by.nikskonda.devtask.model.Country;
import by.nikskonda.devtask.model.CountryGraphNode;
import by.nikskonda.devtask.model.RouteResponse;
import by.nikskonda.devtask.repository.CountryData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RouteService {

    private final BreadthFirstSearch breadthFirstSearch;
    private final CountryData countryData;

    public RouteService(BreadthFirstSearch breadthFirstSearch, CountryData countryData) {
        this.breadthFirstSearch = breadthFirstSearch;
        this.countryData = countryData;
    }

    public RouteResponse findMinCountryRoute(String source, String destination) {
        Map<String, CountryGraphNode> mapOfCountryGraph = countryData.getMapOfCountryGraph();

        if (!mapOfCountryGraph.containsKey(source) || !mapOfCountryGraph.containsKey(destination)) {
            throw new CountryNotFoundException("Please enter correct 'origin' or 'destination' country.");
        }

        List<Country> result = breadthFirstSearch.search(mapOfCountryGraph, source, destination);
        if (result.isEmpty()) {
            throw new NoLandCrossingException("There is no land crossing.");
        }

        RouteResponse routeResponse = new RouteResponse();
        routeResponse.setRoute(result.stream().map(Country::getName).toList());
        return routeResponse;
    }
}
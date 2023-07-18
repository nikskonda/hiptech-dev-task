package by.nikskonda.devtask.repository;

import by.nikskonda.devtask.model.Country;
import by.nikskonda.devtask.model.GraphNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class CountryData {

    private Map<String, GraphNode<Country>> mapOfCountryGraph;

    @PostConstruct
    private void loadDataFromJsonFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Country> mapOfCountries = mapper.readValue(
                            ResourceUtils.getFile("classpath:data.json"),
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

    public Map<String, GraphNode<Country>> getMapOfCountryGraph() {
        return mapOfCountryGraph;
    }
}

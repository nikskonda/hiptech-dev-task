package by.nikskonda.devtask.repository;

import by.nikskonda.devtask.model.Country;
import by.nikskonda.devtask.model.CountryGraphNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final Map<String, CountryGraphNode> mapOfCountryGraph;

    public CountryData() {
        mapOfCountryGraph = new HashMap<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Country> mapOfCountries = mapper.readValue(
                            ResourceUtils.getFile("classpath:data.json"),
                            new TypeReference<List<Country>>() {})
                    .stream()
                    .collect(Collectors.toMap(Country::getName, Function.identity()));

            for (Country country : mapOfCountries.values()) {
                CountryGraphNode graphNode = createNewGraphNodeOrReturnExisting(country);
                for (String borderCountry : country.getBorders()) {
                    CountryGraphNode neighbourNode = createNewGraphNodeOrReturnExisting(mapOfCountries.get(borderCountry));
                    graphNode.getNeighbours().add(neighbourNode);
                }
            }
        } catch (IOException ex) {
            //logger
            ex.printStackTrace();
        }
    }

    private CountryGraphNode createNewGraphNodeOrReturnExisting(Country country) {
        String key = country.getName();
        if (!mapOfCountryGraph.containsKey(key)) {
            mapOfCountryGraph.put(key, new CountryGraphNode(key, country));
        }
        return mapOfCountryGraph.get(key);
    }

    public Map<String, CountryGraphNode> getMapOfCountryGraph() {
        return mapOfCountryGraph;
    }
}

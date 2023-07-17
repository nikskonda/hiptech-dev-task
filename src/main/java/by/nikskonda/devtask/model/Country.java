package by.nikskonda.devtask.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
    private String name;
    private List<String> borders = new ArrayList<>();

    public String getName() {
        return name;
    }

    @JsonProperty("cca3")
    public void setName(String name) {
        this.name = name;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }
}

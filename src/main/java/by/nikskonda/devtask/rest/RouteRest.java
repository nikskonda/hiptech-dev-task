package by.nikskonda.devtask.rest;

import by.nikskonda.devtask.config.Constant;
import by.nikskonda.devtask.model.RouteResponse;
import by.nikskonda.devtask.service.RouteService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(value = Constant.DOMAIN_REST + "/routing")
public class RouteRest {

    private final RouteService service;

    public RouteRest(RouteService service) {
        this.service = service;
    }

    @GetMapping("/{origin}/{destination}")
    public RouteResponse findRoute(@PathVariable("origin") @NotBlank(message = "Please enter a non blank origin country.") String origin,
                                   @PathVariable("destination") @NotBlank(message = "Please enter a non blank destination country.") String destination) {
        return service.findMinCountryRoute(origin, destination);
    }

}

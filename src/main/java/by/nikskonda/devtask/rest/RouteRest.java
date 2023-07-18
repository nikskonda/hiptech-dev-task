package by.nikskonda.devtask.rest;

import by.nikskonda.devtask.config.Constant;
import by.nikskonda.devtask.model.RouteResponse;
import by.nikskonda.devtask.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constant.DOMAIN_REST + "/routing")
public class RouteRest {

    @Autowired
    private RouteService service;

    @GetMapping("/{origin}/{destination}")
    public RouteResponse findRoute(@PathVariable("origin") String origin,
                                   @PathVariable("destination") String destination) {
        return service.findMinCountryRoute(origin, destination);
    }

}

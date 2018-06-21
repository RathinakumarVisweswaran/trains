package com.challenge.tw.trains.controller;

import com.challenge.tw.trains.model.Route;
import com.challenge.tw.trains.service.SelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class AdminController {

    @Autowired
    SelfService service;

    public static Route parse(String routeEncoding) {
        return Route.between(routeEncoding.substring(0, 1),
                routeEncoding.substring(1, 2),
                Integer.parseInt(routeEncoding.substring(2)));
    }

    //making all the methods as get for convenience
    @GetMapping("/admin/add-route/{routeEncoding:[A-E][A-E][0-9]+}")
    public boolean addNewRoute(@PathVariable String routeEncoding) {
        return service.addRoute(parse(routeEncoding));
    }

    @GetMapping("/admin/add-routes/{encodedRoutes:[[A-E][A-E][0-9]+,]*[A-E][A-E][0-9]+}")
    public List<String> addRoutes(@PathVariable String encodedRoutes) {
        return Arrays.stream(encodedRoutes.split(","))
                .filter(routeEncoding -> service.addRoute(parse(routeEncoding)))
                .collect(toList());
    }

}

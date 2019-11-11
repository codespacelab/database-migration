package com.codespacelab.demo.controller;

import com.codespacelab.demo.model.CoffeeDto;
import com.codespacelab.demo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/coffee")
public class CoffeeController {

    private CoffeeService coffeeService;

    public CoffeeController(final CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping("/all")
    public List<CoffeeDto> getCoffees() {
        return coffeeService.getCoffees();
    }

    @GetMapping
    public CoffeeDto getCoffee(
            @RequestParam(name = "name") String name
    ) {
        return coffeeService.getCoffee(name);
    }

    @PostMapping
    public CoffeeDto addCoffee(
            @Validated @RequestBody CoffeeDto coffeeDto
    ) {
        return coffeeService.addCoffee(coffeeDto);
    }

    @PutMapping
    public CoffeeDto updateCoffee(
            @Validated @RequestBody CoffeeDto coffeeDto
    ) {
        return coffeeService.updateCoffee(coffeeDto);
    }

    @DeleteMapping
    public boolean deleteCoffee(
            @RequestParam(name = "name") String name
    ) {
        return coffeeService.deleteCoffee(name);
    }
}

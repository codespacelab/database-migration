package com.codespacelab.demo.service;

import com.codespacelab.demo.model.Coffee;
import com.codespacelab.demo.model.CoffeeDto;
import com.codespacelab.demo.model.CoffeeMapper;
import com.codespacelab.demo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class CoffeeService {

    private CoffeeRepository coffeeRepository;
    private CoffeeMapper coffeeMapper;

    public CoffeeService(final CoffeeRepository coffeeRepository,
                             final CoffeeMapper coffeeMapper) {
        this.coffeeRepository = coffeeRepository;
        this.coffeeMapper = coffeeMapper;
    }

    public List<CoffeeDto> getCoffees() {
        return coffeeRepository
                .findAll()
                .stream()
                .map(coffee -> coffeeMapper.domainToDto(coffee)).collect(toList());
    }

    public CoffeeDto getCoffee(String name) {
        Optional<Coffee> coffeeOptional = coffeeRepository.findByName(name);

        if(!coffeeOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }

        log.debug("Retrieving coffee object: " + name);

        return coffeeMapper.domainToDto(coffeeOptional.get());
    }

    @Transactional
    public CoffeeDto addCoffee(CoffeeDto coffeeDto) {
        Optional<Coffee> coffeeOptional = coffeeRepository.findByName(coffeeDto.getName());

        if(coffeeOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Create Request");
        }

        log.debug("Creating coffee object: " + coffeeDto.getName());

        return save(coffeeDto);
    }

    @Transactional
    public CoffeeDto updateCoffee(CoffeeDto coffeeDto) {
        Optional<Coffee> coffeeOptional = coffeeRepository.findByName(coffeeDto.getName());

        if(!coffeeOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Update Request");
        }

        log.debug("Updating coffee object: " + coffeeDto.getName());

        return save(coffeeDto);
    }

    @Transactional
    public boolean deleteCoffee(String name) {
        Optional<Coffee> coffeeOptional = coffeeRepository.findByName(name);

        if(!coffeeOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Delete Request");
        }

        log.debug("Deleting coffee object: " + name);

        return coffeeRepository.deleteByName(name).intValue() == 1;
    }

    private CoffeeDto save(CoffeeDto coffeeDto) {
        Coffee coffee = coffeeRepository.saveAndFlush(coffeeMapper.dtoToDomain(coffeeDto));

        log.debug("Saving coffee object: " + coffeeDto.getName());

        return coffeeMapper.domainToDto(coffee);
    }
}

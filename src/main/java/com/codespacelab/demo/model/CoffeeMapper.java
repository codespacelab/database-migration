package com.codespacelab.demo.model;

import org.springframework.stereotype.Component;
import org.dozer.DozerBeanMapper;

@Component
public class CoffeeMapper {

    final static DozerBeanMapper mapper = new DozerBeanMapper();

    public Coffee dtoToDomain(CoffeeDto coffeeDto) {
        if (coffeeDto == null) {
            return null;
        }

        return mapper.map(coffeeDto, Coffee.class);
    }

    public CoffeeDto domainToDto(Coffee coffee) {
        return mapper.map(coffee, CoffeeDto.class);
    }
}

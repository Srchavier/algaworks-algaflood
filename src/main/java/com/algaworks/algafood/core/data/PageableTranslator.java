package com.algaworks.algafood.core.data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class PageableTranslator {

    private PageableTranslator() {
        throw new IllegalStateException("Utility class");
    }

    public static Pageable translate(Pageable pageable, Map<String, String> fields){
        List<Order> orders = pageable.getSort().stream()
            .filter(order -> fields.containsKey(order.getProperty()))
            .map(order -> new Sort.Order(order.getDirection(), fields.get(order.getProperty())))
            .collect(Collectors.toList());

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }
    
}

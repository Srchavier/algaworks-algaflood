package com.algaworks.algafood.api;

import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourceUriHelper {

    public static void addUriResponseHeader(Object resourceId) {
        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .build(resourceId);

        HttpServletResponse httpServletResponse = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getResponse();

        if (Objects.nonNull(httpServletResponse)) {
            httpServletResponse.setHeader(HttpHeaders.LOCATION, uri.toString());
        }
    }
}

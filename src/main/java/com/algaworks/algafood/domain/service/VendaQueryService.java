package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {

    /**
     * @param filtro
     * @param timeOffset
     * @return
     */
    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
    
}

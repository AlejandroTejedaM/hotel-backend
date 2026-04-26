package com.hotelreservation.commons.services;

import java.util.List;

public interface CrudService<RQ, RS> {
    List<RS> list();

    RS encontrarPorId(Long id);

    RS registrar(RQ request);

    RS actualizar(RQ request, Long id);

    void eliminar(Long id);
}

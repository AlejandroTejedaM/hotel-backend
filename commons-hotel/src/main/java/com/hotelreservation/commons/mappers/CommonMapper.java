package com.hotelreservation.commons.mappers;

public interface CommonMapper<RQ, RS, E> {
    E requestAEntidad(RQ request);

    RS entidadARespuesta(E entity);
}

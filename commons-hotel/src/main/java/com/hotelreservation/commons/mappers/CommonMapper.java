package com.hotelreservation.commons.mappers;

public interface CommonMapper<RQ, RS, E> {
    E requestToEntity(RQ request);

    RS entityToResponse(E entity);
}

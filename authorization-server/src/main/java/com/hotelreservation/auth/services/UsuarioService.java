package com.hotelreservation.auth.services;

import com.hotelreservation.auth.dto.UsuarioRequest;
import com.hotelreservation.auth.dto.UsuarioResponse;

import java.util.Set;

public interface UsuarioService {

    Set<UsuarioResponse> listar();

    UsuarioResponse registrar(UsuarioRequest request);

    UsuarioResponse eliminar(String username);
}



















































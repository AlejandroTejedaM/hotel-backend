package com.hotelreservation.auth.services;

import com.hotelreservation.auth.dto.LoginRequest;
import com.hotelreservation.auth.dto.TokenResponse;

public interface AuthService {

    TokenResponse autenticar(LoginRequest request) throws Exception;
}

package com.hotelreservation.auth.repositories;

import com.hotelreservation.auth.dto.UsuarioRequest;
import com.hotelreservation.auth.dto.UsuarioResponse;
import com.hotelreservation.auth.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);

}

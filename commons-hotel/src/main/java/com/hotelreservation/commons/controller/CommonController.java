package com.hotelreservation.commons.controller;

import com.hotelreservation.commons.services.CrudService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@AllArgsConstructor
public class CommonController<RQ, RS, S extends CrudService<RQ, RS>> {
    protected final S service;

    @GetMapping
    public ResponseEntity<List<RS>> findAll() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RS> encontrarPorId(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        return ResponseEntity.ok(service.encontrarPorId(id));
    }

    @PostMapping()
    public ResponseEntity<RS> register(@Valid @RequestBody RQ request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RS> update(@PathVariable @Positive(message = "El id debe ser positivo") Long id, @Valid @RequestBody RQ request) {
        return ResponseEntity.ok(service.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}

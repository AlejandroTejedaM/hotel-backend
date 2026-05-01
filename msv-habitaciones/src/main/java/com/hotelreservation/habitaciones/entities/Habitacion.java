package com.hotelreservation.habitaciones.entities;

import com.hotelreservation.commons.enums.EstadoHabitacion;
import com.hotelreservation.commons.enums.EstadoRegistro;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HABITACIONES")
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HABITACION", nullable = false)
    private Long id;

    @Column(name = "NUMERO", nullable = false)
    private Integer numero;

    @Column(name = "TIPO", nullable = false, length = 50)
    private String tipo;

    @Column(name = "PRECIO", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "CAPACIDAD", nullable = false)
    private Short capacidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_HABITACION", nullable = false, length = 20)
    private EstadoHabitacion estadoHabitacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", nullable = false, length = 20)
    private EstadoRegistro estadoRegistro;

    public void actualizar(Integer numero, String tipo, BigDecimal precio, Short capacidad) {
        this.numero = numero;
        this.tipo = tipo;
        this.precio = precio;
        this.capacidad = capacidad;
    }

    public void eliminar() {
        this.estadoRegistro = EstadoRegistro.ELIMINADO;
    }

    public void cambiarEstadoHabitacion(EstadoHabitacion estadoHabitacion) {
        //validarCambioEstado(estadoHabitacion);
        this.estadoHabitacion = estadoHabitacion;
    }

    private void validarCambioEstado(EstadoHabitacion estadoHabitacion) {
        if (estadoHabitacion == EstadoHabitacion.DISPONIBLE && this.estadoHabitacion == EstadoHabitacion.OCUPADA) {
            throw new IllegalArgumentException("No se puede cambiar una habitación del estado: " + EstadoHabitacion.OCUPADA + " a: " + estadoHabitacion);
        }
    }
}
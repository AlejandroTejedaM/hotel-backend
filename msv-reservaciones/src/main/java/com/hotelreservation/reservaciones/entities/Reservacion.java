package com.hotelreservation.reservaciones.entities;

import com.hotelreservation.commons.enums.EstadoRegistro;
import com.hotelreservation.commons.enums.EstadoReserva;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RESERVACIONES")
public class Reservacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVACION", nullable = false)
    private Long id;

    @Column(name = "ID_HUESPED", nullable = false)
    private Long idHuesped;

    @Column(name = "ID_HABITACION", nullable = false)
    private Long idHabitacion;

    @Column(name = "FECHA_ENTRADA", nullable = false)
    private LocalDate fechaEntrada;

    @Column(name = "FECHA_SALIDA", nullable = false)
    private LocalDate fechaSalida;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_RESERVA", nullable = false, length = 20)
    private EstadoReserva estadoReserva;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", nullable = false, length = 20)
    private EstadoRegistro estadoRegistro;

    public void changeEstado(EstadoReserva estado) {
        this.estadoReserva = estado;
    }

    public void changeFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public void changeFechaEntradaYSalida(LocalDate fechaEntrada, LocalDate fechaSalida) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
    }

    public void delete() {
        validateDeletionEligibility();
        this.estadoRegistro = EstadoRegistro.ELIMINADO;
    }

    private void validateDeletionEligibility() {
        switch (this.getEstadoReserva()) {
            case EN_CURSO, CONFIRMADA -> throw new IllegalStateException("No se puede eliminar una reservación en curso o confirmada");
        }
    }
}
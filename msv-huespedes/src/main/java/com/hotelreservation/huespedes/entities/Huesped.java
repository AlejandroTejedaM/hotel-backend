package com.hotelreservation.huespedes.entities;


import com.hotelreservation.commons.enums.EstadoRegistro;
import jakarta.persistence.*;
import lombok.*;




@Entity
@Table(name= "HUESPEDES")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class Huesped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HUESPED")
    private Long idHuesped;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(length = 50)
    private String apellidoMaterno;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 10)
    private String telefono;

    @Column(nullable = false, length = 20)
    private String documento;

    @Column(nullable = false, length = 50)
    private String nacionalidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoRegistro estadoRegistro;

}

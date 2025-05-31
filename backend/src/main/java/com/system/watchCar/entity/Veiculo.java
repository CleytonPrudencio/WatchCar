package com.system.watchCar.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_VEICULO")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_VEICULO")
    private TipoVeiculo tipoVeiculo;

    private String placa;
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}


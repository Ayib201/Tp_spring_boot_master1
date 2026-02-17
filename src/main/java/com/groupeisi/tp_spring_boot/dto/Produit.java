package com.groupeisi.tp_spring_boot.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class Produit {
    private Integer id;
    @NotNull(message = "Le nom ne doit pas etre null")
    private String nom;
    @PositiveOrZero(message = "La quantité en stock doit être positive ou nulle")
    private double qtStock;
}

package com.groupeisi.tp_spring_boot.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor

public class AppRoles {
    private int id;
    @NotNull(message = "Le nom ne doit pas etre null")
    private String nom;
}

package com.groupeisi.tp_spring_boot.mapping;

import com.groupeisi.tp_spring_boot.dto.Produit;
import com.groupeisi.tp_spring_boot.entities.ProduitEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ProduitMapper {
    Produit toProduit(ProduitEntity produitEntity);
    ProduitEntity fromProduit(Produit produit);
}

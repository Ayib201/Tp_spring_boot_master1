package com.groupeisi.tp_spring_boot.dao;

import com.groupeisi.tp_spring_boot.entities.ProduitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProduitRepository extends JpaRepository<ProduitEntity, Integer> {
}

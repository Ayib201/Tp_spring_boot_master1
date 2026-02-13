package com.groupeisi.tp_spring_boot.dao;

import com.groupeisi.tp_spring_boot.entities.AppRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppRolesRepository extends JpaRepository<AppRolesEntity, Integer> {
}

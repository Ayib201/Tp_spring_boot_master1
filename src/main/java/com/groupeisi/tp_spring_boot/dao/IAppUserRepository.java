package com.groupeisi.tp_spring_boot.dao;

import com.groupeisi.tp_spring_boot.entities.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppUserRepository extends JpaRepository<AppUserEntity, Integer> {
    AppUserEntity findByEmail(String email);
}

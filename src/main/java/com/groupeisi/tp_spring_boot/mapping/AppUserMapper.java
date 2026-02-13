package com.groupeisi.tp_spring_boot.mapping;


import com.groupeisi.tp_spring_boot.dto.AppUser;
import com.groupeisi.tp_spring_boot.entities.AppUserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface AppUserMapper {
    AppUser toAppUser(AppUserEntity appUserEntity);
    AppUserEntity fromAppUser(AppUser appUser);
}

package com.groupeisi.tp_spring_boot.mapping;

import com.groupeisi.tp_spring_boot.dto.AppRoles;
import com.groupeisi.tp_spring_boot.entities.AppRolesEntity;
import org.mapstruct.Mapper;

@Mapper
public interface AppRolesMapper {
    AppRoles toAppRoles(AppRolesEntity appRolesEntity);
    AppRolesEntity fromAppRoles(AppRoles appRoles);
}

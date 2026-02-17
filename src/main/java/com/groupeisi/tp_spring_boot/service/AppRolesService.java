package com.groupeisi.tp_spring_boot.service;

import com.groupeisi.tp_spring_boot.dao.IAppRolesRepository;
import com.groupeisi.tp_spring_boot.dto.AppRoles;
import com.groupeisi.tp_spring_boot.exception.EntityNotFoundException;
import com.groupeisi.tp_spring_boot.exception.RequestException;
import com.groupeisi.tp_spring_boot.mapping.AppRolesMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "roles")
public class AppRolesService {
    private final IAppRolesRepository iAppRolesRepository;
    private final AppRolesMapper appRolesMapper;
    MessageSource messageSource;

    public AppRolesService(IAppRolesRepository iAppRolesRepository, AppRolesMapper appRolesMapper, MessageSource messageSource) {
        this.iAppRolesRepository = iAppRolesRepository;
        this.appRolesMapper = appRolesMapper;
        this.messageSource = messageSource;
    }

    @Transactional(readOnly = true)
    public List<AppRoles>  getAppRoles() {
        return iAppRolesRepository.findAll().stream()
                .map(appRolesMapper::toAppRoles)
                .collect(Collectors.toList());
    }

    @Cacheable(key = "#id")
    @Transactional(readOnly = true)
    public AppRoles getAppRole(int id) {
        return appRolesMapper.toAppRoles(iAppRolesRepository.findById(id)
                .orElseThrow(() ->
                new EntityNotFoundException(messageSource.getMessage("role.notfound", new Object[]{id},
                        Locale.getDefault()))));
    }

    @Transactional
    public AppRoles createAppRoles(AppRoles appRoles) {
        return appRolesMapper.toAppRoles(iAppRolesRepository.save(appRolesMapper.fromAppRoles(appRoles)));
    }

    @CachePut(key = "#id")
    @Transactional
    public AppRoles updateAppRoles(int id, AppRoles appRoles) {
        return iAppRolesRepository.findById(id)
                .map(entity -> {
                    appRoles.setId(id);
                    return appRolesMapper.toAppRoles(
                            iAppRolesRepository.save(appRolesMapper.fromAppRoles(appRoles)));
                }).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("role.notfound", new Object[]{id},
                        Locale.getDefault())));
    }

    @CacheEvict(key = "#id")
    @Transactional
    public void deleteAppRoles(int id) {
        try {
            iAppRolesRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(messageSource.getMessage("role.errordeletion", new Object[]{id},
                    Locale.getDefault()),
                    HttpStatus.CONFLICT);
        }
    }
}

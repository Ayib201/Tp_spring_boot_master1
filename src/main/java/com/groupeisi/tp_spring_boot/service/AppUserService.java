package com.groupeisi.tp_spring_boot.service;

import com.groupeisi.tp_spring_boot.dao.IAppUserRepository;
import com.groupeisi.tp_spring_boot.dto.AppUser;
import com.groupeisi.tp_spring_boot.exception.EntityNotFoundException;
import com.groupeisi.tp_spring_boot.exception.RequestException;
import com.groupeisi.tp_spring_boot.mapping.AppUserMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
@CacheConfig(cacheNames = "users")
public class AppUserService {
    private final IAppUserRepository iAppUserRepository;
    private final AppUserMapper appUserMapper;
    MessageSource messageSource;

    public AppUserService(IAppUserRepository iAppUserRepository, AppUserMapper appUserMapper, MessageSource messageSource) {
        this.iAppUserRepository = iAppUserRepository;
        this.appUserMapper = appUserMapper;
        this.messageSource = messageSource;
    }
    @Transactional(readOnly = true)
    public List<AppUser> getAppUser() {
        return iAppUserRepository.findAll().stream()
                .map(appUserMapper::toAppUser)
                .collect(Collectors.toList());
    }

    @Cacheable(key = "#id")
    @Transactional(readOnly = true)
    public AppUser getAppRole(int id) {
        return appUserMapper.toAppUser(iAppUserRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("user.notfound", new Object[]{id},
                                Locale.getDefault()))));
    }

    @Transactional
    public AppUser createAppUser(AppUser AppUser) {
        return appUserMapper.toAppUser(iAppUserRepository.save(appUserMapper.fromAppUser(AppUser)));
    }

    @CachePut(key = "#id")
    @Transactional
    public AppUser updateAppUser(int id, AppUser AppUser) {
        return iAppUserRepository.findById(id)
                .map(entity -> {
                    AppUser.setId(id);
                    return appUserMapper.toAppUser(
                            iAppUserRepository.save(appUserMapper.fromAppUser(AppUser)));
                }).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("role.notfound", new Object[]{id},
                        Locale.getDefault())));
    }

    @CacheEvict(key = "#id")
    @Transactional
    public void deleteAppUser(int id) {
        try {
            iAppUserRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(messageSource.getMessage("role.errordeletion", new Object[]{id},
                    Locale.getDefault()),
                    HttpStatus.CONFLICT);
        }
    }
}

package com.groupeisi.tp_spring_boot.service;

import com.groupeisi.tp_spring_boot.dao.IAppUserRepository;
import com.groupeisi.tp_spring_boot.dto.AppUser;
import com.groupeisi.tp_spring_boot.entities.AppUserEntity;
import com.groupeisi.tp_spring_boot.exception.EntityNotFoundException;
import com.groupeisi.tp_spring_boot.exception.RequestException;
import com.groupeisi.tp_spring_boot.mapping.AppUserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    private IAppUserRepository repository;

    @Mock
    private AppUserMapper mapper;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private AppUserService service;

    private AppUserEntity entity;
    private AppUser dto;

    @BeforeEach
    void setUp() {
        entity = new AppUserEntity();
        entity.setId(1);
        entity.setNom("Diallo");
        entity.setPrenom("Mamadou");
        entity.setEmail("test@mail.com");
        entity.setPassword("1234");
        entity.setEtat(1);

        dto = new AppUser();
        dto.setId(1);
        dto.setNom("Diallo");
        dto.setPrenom("Mamadou");
        dto.setEmail("test@mail.com");
        dto.setPassword("1234");
        dto.setEtat(1);
    }

    @Test
    void shouldReturnAllUsers() {

        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toAppUser(entity)).thenReturn(dto);

        List<AppUser> result = service.getAppUser();

        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldReturnUserWhenIdExists() {

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toAppUser(entity)).thenReturn(dto);

        AppUser result = service.getAppRole(1);

        assertNotNull(result);
        assertEquals("Diallo", result.getNom());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(repository.findById(1)).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
                .thenReturn("User not found");

        assertThrows(EntityNotFoundException.class,
                () -> service.getAppRole(1));
    }

    @Test
    void shouldCreateUser() {

        when(mapper.fromAppUser(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toAppUser(entity)).thenReturn(dto);

        AppUser result = service.createAppUser(dto);

        assertNotNull(result);
        assertEquals("Diallo", result.getNom());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void shouldUpdateUser() {

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.fromAppUser(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toAppUser(entity)).thenReturn(dto);

        AppUser result = service.updateAppUser(1, dto);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void shouldDeleteUser() {

        doNothing().when(repository).deleteById(1);

        service.deleteAppUser(1);

        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void shouldThrowRequestExceptionWhenDeleteFails() {

        doThrow(new RuntimeException()).when(repository).deleteById(1);
        when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
                .thenReturn("Deletion error");

        assertThrows(RequestException.class,
                () -> service.deleteAppUser(1));
    }
}
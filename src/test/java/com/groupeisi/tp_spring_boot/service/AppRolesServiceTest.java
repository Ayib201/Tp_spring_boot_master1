package com.groupeisi.tp_spring_boot.service;

import com.groupeisi.tp_spring_boot.dao.IAppRolesRepository;
import com.groupeisi.tp_spring_boot.dto.AppRoles;
import com.groupeisi.tp_spring_boot.entities.AppRolesEntity;
import com.groupeisi.tp_spring_boot.exception.EntityNotFoundException;
import com.groupeisi.tp_spring_boot.mapping.AppRolesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppRolesServiceTest {

    @Mock
    private IAppRolesRepository repository;

    @Mock
    private AppRolesMapper mapper;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private AppRolesService service;

    private AppRoles roleDto;
    private AppRolesEntity entity;

    @BeforeEach
    void setUp() {
        roleDto = new AppRoles(1, "ADMIN");

        entity = new AppRolesEntity();
        entity.setId(1);
        entity.setNom("ADMIN");
    }

    @Test
    void shouldReturnRoleWhenIdExists() {

        when(repository.findById(1))
                .thenReturn(Optional.of(entity));

        when(mapper.toAppRoles(entity))
                .thenReturn(roleDto);

        AppRoles result = service.getAppRole(1);

        assertNotNull(result);
        assertEquals("ADMIN", result.getNom());
        verify(repository, times(1)).findById(1);
    }

    @Test
    void shouldThrowExceptionWhenRoleNotFound() {

        when(repository.findById(1)).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
                .thenReturn("Role not found");

        assertThrows(EntityNotFoundException.class,
                () -> service.getAppRole(1));
    }

    @Test
    void shouldCreateRole() {

        when(mapper.fromAppRoles(roleDto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toAppRoles(entity)).thenReturn(roleDto);

        AppRoles result = service.createAppRoles(roleDto);

        assertNotNull(result);
        assertEquals("ADMIN", result.getNom());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void shouldDeleteRole() {

        doNothing().when(repository).deleteById(1);

        service.deleteAppRoles(1);

        verify(repository, times(1)).deleteById(1);
    }
}

package com.groupeisi.tp_spring_boot.service;

import com.groupeisi.tp_spring_boot.dao.IProduitRepository;
import com.groupeisi.tp_spring_boot.dto.Produit;
import com.groupeisi.tp_spring_boot.entities.ProduitEntity;
import com.groupeisi.tp_spring_boot.exception.EntityNotFoundException;
import com.groupeisi.tp_spring_boot.exception.RequestException;
import com.groupeisi.tp_spring_boot.mapping.ProduitMapper;
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
class ProduitServiceTest {

    @Mock
    private IProduitRepository repository;

    @Mock
    private ProduitMapper mapper;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private ProduitService service;

    private Produit produitDto;
    private ProduitEntity produitEntity;

    @BeforeEach
    void setUp() {
        // Ajout de qtStock
        produitDto = new Produit(1, "ProduitTest", 10.5);
        produitEntity = new ProduitEntity();
        produitEntity.setId(1);
        produitEntity.setNom("ProduitTest");
        produitEntity.setQtStock(10.5);
    }

    @Test
    void shouldReturnProduitWhenIdExists() {
        when(repository.findById(1)).thenReturn(Optional.of(produitEntity));
        when(mapper.toProduit(produitEntity)).thenReturn(produitDto);

        Produit result = service.getProduit(1);

        assertNotNull(result);
        assertEquals("ProduitTest", result.getNom());
        assertEquals(10.5, result.getQtStock());
        verify(repository, times(1)).findById(1);
    }

    @Test
    void shouldThrowExceptionWhenProduitNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
                .thenReturn("Produit non trouvé");

        assertThrows(EntityNotFoundException.class, () -> service.getProduit(1));
    }

    @Test
    void shouldCreateProduit() {
        when(mapper.fromProduit(produitDto)).thenReturn(produitEntity);
        when(repository.save(produitEntity)).thenReturn(produitEntity);
        when(mapper.toProduit(produitEntity)).thenReturn(produitDto);

        Produit result = service.createProduit(produitDto);

        assertNotNull(result);
        assertEquals("ProduitTest", result.getNom());
        assertEquals(10.5, result.getQtStock());
        verify(repository, times(1)).save(produitEntity);
    }

    @Test
    void shouldUpdateProduit() {
        Produit updatedDto = new Produit(1, "ProduitUpdated", 20.0);
        when(repository.findById(1)).thenReturn(Optional.of(produitEntity));
        when(mapper.fromProduit(updatedDto)).thenReturn(produitEntity);
        when(repository.save(produitEntity)).thenReturn(produitEntity);
        when(mapper.toProduit(produitEntity)).thenReturn(updatedDto);

        Produit result = service.updateProduit(1, updatedDto);

        assertNotNull(result);
        assertEquals("ProduitUpdated", result.getNom());
        assertEquals(20.0, result.getQtStock());
        verify(repository, times(1)).save(produitEntity);
    }

    @Test
    void shouldThrowExceptionWhenUpdateProduitNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
                .thenReturn("Produit non trouvé");

        Produit updatedDto = new Produit(1, "ProduitUpdated", 20.0);

        assertThrows(EntityNotFoundException.class, () -> service.updateProduit(1, updatedDto));
    }

    @Test
    void shouldDeleteProduit() {
        doNothing().when(repository).deleteById(1);

        service.deleteProduit(1);

        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void shouldThrowRequestExceptionWhenDeleteFails() {
        doThrow(new RuntimeException()).when(repository).deleteById(1);
        when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
                .thenReturn("Erreur suppression");

        assertThrows(RequestException.class, () -> service.deleteProduit(1));
    }

    @Test
    void shouldReturnAllProduits() {
        List<ProduitEntity> entities = List.of(produitEntity);
        when(repository.findAll()).thenReturn(entities);
        when(mapper.toProduit(produitEntity)).thenReturn(produitDto);

        List<Produit> result = service.getProduit();

        assertEquals(1, result.size());
        assertEquals("ProduitTest", result.get(0).getNom());
        assertEquals(10.5, result.get(0).getQtStock());
        verify(repository, times(1)).findAll();
    }
}
package com.groupeisi.tp_spring_boot.service;

import com.groupeisi.tp_spring_boot.dao.IProduitRepository;
import com.groupeisi.tp_spring_boot.dto.Produit;
import com.groupeisi.tp_spring_boot.exception.EntityNotFoundException;
import com.groupeisi.tp_spring_boot.exception.RequestException;
import com.groupeisi.tp_spring_boot.mapping.ProduitMapper;
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

@Service
@CacheConfig(cacheNames = "users")

public class ProduitService {
    private final IProduitRepository iProduitRepository;
    private final ProduitMapper produitMapper;
    MessageSource messageSource;

    public ProduitService(IProduitRepository iProduitRepository, ProduitMapper produitMapper, MessageSource messageSource) {
        this.iProduitRepository = iProduitRepository;
        this.produitMapper = produitMapper;
        this.messageSource = messageSource;
    }

    @Transactional(readOnly = true)
    public List<Produit> getProduit() {
        return iProduitRepository.findAll().stream()
                .map(produitMapper::toProduit)
                .toList();
    }

    @Cacheable(key = "#id")
    @Transactional(readOnly = true)
    public Produit getProduit(int id) {
        return produitMapper.toProduit(iProduitRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("role.notfound", new Object[]{id},
                                Locale.getDefault()))));
    }

    @Transactional
    public Produit createProduit(Produit produit) {
        return produitMapper.toProduit(iProduitRepository.save(produitMapper.fromProduit(produit)));
    }

    @CachePut(key = "#id")
    @Transactional
    public Produit updateProduit(int id, Produit produit) {
        return iProduitRepository.findById(id)
                .map(entity -> {
                    produit.setId(id);
                    return produitMapper.toProduit(
                            iProduitRepository.save(produitMapper.fromProduit(produit)));
                }).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("role.notfound", new Object[]{id},
                        Locale.getDefault())));
    }

    @CacheEvict(key = "#id")
    @Transactional
    public void deleteProduit(int id) {
        try {
            iProduitRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(messageSource.getMessage("produit.errordeletion", new Object[]{id},
                    Locale.getDefault()),
                    HttpStatus.CONFLICT);
        }
    }
}

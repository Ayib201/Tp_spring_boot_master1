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
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "users")

public class ProduitService {
    private final IProduitRepository iProduitRepository;
    private final ProduitMapper ProduitMapper;
    MessageSource messageSource;

    public ProduitService(IProduitRepository iProduitRepository, ProduitMapper ProduitMapper, MessageSource messageSource) {
        this.iProduitRepository = iProduitRepository;
        this.ProduitMapper = ProduitMapper;
        this.messageSource = messageSource;
    }

    @Transactional(readOnly = true)
    public List<Produit> getProduit() {
        return iProduitRepository.findAll().stream()
                .map(ProduitMapper::toProduit)
                .collect(Collectors.toList());
    }

    @Cacheable(key = "#id")
    @Transactional(readOnly = true)
    public Produit getProduit(int id) {
        return ProduitMapper.toProduit(iProduitRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("role.notfound", new Object[]{id},
                                Locale.getDefault()))));
    }

    @Transactional
    public Produit createProduit(Produit Produit) {
        return ProduitMapper.toProduit(iProduitRepository.save(ProduitMapper.fromProduit(Produit)));
    }

    @CachePut(key = "#id")
    @Transactional
    public Produit updateProduit(int id, Produit Produit) {
        return iProduitRepository.findById(id)
                .map(entity -> {
                    Produit.setId(id);
                    return ProduitMapper.toProduit(
                            iProduitRepository.save(ProduitMapper.fromProduit(Produit)));
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

package com.groupeisi.tp_spring_boot.controller;

import com.groupeisi.tp_spring_boot.dto.Produit;
import com.groupeisi.tp_spring_boot.service.ProduitService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {
    private ProduitService produitService;

    @GetMapping
    public List<Produit> getProduit() {
        return produitService.getProduit();
    }

    @GetMapping("/{id}")
    public Produit getProduit(@PathVariable("id") int id) {
        return produitService.getProduit(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Produit createProduit(@Valid @RequestBody Produit produit) {
        return produitService.createProduit(produit);
    }

    @PutMapping("/{id}")
    public Produit updateProduit(@PathVariable("id") int id, @Valid @RequestBody Produit produit) {
        return produitService.updateProduit(id, produit);
    }

    @DeleteMapping("/{id}")
    public void deleteProduit(@PathVariable("id") int id) {
        produitService.deleteProduit(id);
    }
}

package com.groupeisi.tp_spring_boot.controller;

import com.groupeisi.tp_spring_boot.dto.AppUser;
import com.groupeisi.tp_spring_boot.service.AppUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class AppUsersController {
    private AppUserService appUserService;

    @GetMapping
    public List<AppUser> getAppUser() {
        return appUserService.getAppUser();
    }

    @GetMapping("/{id}")
    public AppUser getAppUser(@PathVariable("id") int id) {
        return appUserService.getAppRole(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AppUser createAppUser(@Valid @RequestBody AppUser appUser) {
        return appUserService.createAppUser(appUser);
    }

    @PutMapping("/{id}")
    public AppUser updateAppUser(@PathVariable("id") int id, @Valid @RequestBody AppUser appUser) {
        return appUserService.updateAppUser(id, appUser);
    }

    @DeleteMapping("/{id}")
    public void deleteAppUser(@PathVariable("id") int id) {
        appUserService.deleteAppUser(id);
    }
}

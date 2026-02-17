package com.groupeisi.tp_spring_boot.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class EntityNotFoundException extends RuntimeException {
     private final String message;
}

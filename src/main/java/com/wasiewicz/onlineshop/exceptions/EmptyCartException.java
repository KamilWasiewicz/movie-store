package com.wasiewicz.onlineshop.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmptyCartException extends RuntimeException {
    private final Set<String> errorMessages;

    public EmptyCartException(Set<String> errorMessages) {
        super();
        this.errorMessages = errorMessages;
    }
}
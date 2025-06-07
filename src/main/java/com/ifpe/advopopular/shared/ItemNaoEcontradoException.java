package com.ifpe.advopopular.shared;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Item não encontrado")
public class ItemNaoEcontradoException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -1757707491618792479L;
}

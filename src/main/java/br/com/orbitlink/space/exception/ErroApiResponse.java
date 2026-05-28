package br.com.orbitlink.space.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErroApiResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<String> details
) {}

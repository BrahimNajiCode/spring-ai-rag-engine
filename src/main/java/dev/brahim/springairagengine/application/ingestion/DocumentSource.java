package dev.brahim.springairagengine.application.ingestion;

import org.springframework.core.io.Resource;

import java.util.Objects;

public record DocumentSource(
    Resource resource,
    String filename
) {
    public DocumentSource {
        Objects.requireNonNull(resource, "Resource must not be null");

        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException(
                    "Filename must not be blank"
            );
        }
    }
}

package dev.brahim.springairagengine.application.rag;

import java.util.Objects;

public record RagRequest(
   String query
) {
    public RagRequest {
        Objects.requireNonNull(query, "Query must not be null");

        if (query.isBlank()) {
            throw new IllegalArgumentException(
                    "Query must not be blank"
            );
        }
    }
}

package dev.brahim.springairagengine.domain.query;

import java.util.Objects;

public record ProcessedQuery(
   String query
) {

    public ProcessedQuery {
        Objects.requireNonNull(query, "Query must not be null");

        if (query.isBlank()) {
            throw new IllegalArgumentException(
                    "Query must not be blank"
            );
        }
    }
}

package dev.brahim.springairagengine.domain.retrieval;

import java.util.Map;
import java.util.Objects;

public record RetrievedDocument(
   String content,
   Map<String, Object> metadata,
   double score
) {
    public RetrievedDocument {
        Objects.requireNonNull(content, "Content must not be null");
        Objects.requireNonNull(metadata, "Metadata must not be null");

        if (content.isBlank()) {
            throw new IllegalArgumentException(
                    "Content must not be blank"
            );
        }

        if (score < 0.0 || score > 1.0) {
            throw new IllegalArgumentException(
                    "Score must be between 0.0 and 1.0"
            );
        }

        metadata = Map.copyOf(metadata);
    }

}

package dev.brahim.springairagengine.application.rag;

import dev.brahim.springairagengine.domain.retrieval.RetrievedDocument;

import java.util.List;
import java.util.Objects;

public record RagResponse(
    String answer,
    List<RetrievedDocument> sources
) {
    public RagResponse {
        Objects.requireNonNull(answer, "Answer must not be null");
        Objects.requireNonNull(sources, "Sources must not be null");

        if (answer.isBlank()) {
            throw new IllegalArgumentException(
                    "Answer must not be blank"
            );
        }
        sources = List.copyOf(sources);
    }
}

package dev.brahim.springairagengine.api.rag;

import jakarta.validation.constraints.NotBlank;

public record RagQueryRequest(
        @NotBlank(message = "Query must not be blank")
        String query
) {
}

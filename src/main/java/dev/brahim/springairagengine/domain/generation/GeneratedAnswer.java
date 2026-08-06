package dev.brahim.springairagengine.domain.generation;

import java.util.Objects;

public record GeneratedAnswer(
   String content
) {
    public GeneratedAnswer {
        Objects.requireNonNull(content, "Content must not be null");

        if (content.isBlank()) {
            throw new IllegalArgumentException(
                    "Content must not be blank"
            );
        }
    }
}

package dev.brahim.springairagengine.infrastructure.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rag")
public record RagProperties(
    RagStrategy strategy
) {
    public RagProperties {
        if (strategy == null) {
            strategy = RagStrategy.NAIVE;
        }
    }
}

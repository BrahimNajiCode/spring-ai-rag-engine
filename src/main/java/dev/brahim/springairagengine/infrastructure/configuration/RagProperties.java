package dev.brahim.springairagengine.infrastructure.configuration;

public class RagProperties {
    private final RagStrategy strategy;

    public RagProperties(RagStrategy strategy) {
        this.strategy = strategy;
    }
}

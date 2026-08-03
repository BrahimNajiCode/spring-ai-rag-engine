package dev.brahim.springairagengine.infrastracture.configuration;

public class RagProperties {
    private final RagStrategy strategy;

    public RagProperties(RagStrategy strategy) {
        this.strategy = strategy;
    }
}

package dev.brahim.springairagengine.infrastructure.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rag.retrieval")
public record RetrievalProperties(
    int topK,
    double similarityThreshold
) {}

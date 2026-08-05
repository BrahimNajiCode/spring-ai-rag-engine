package dev.brahim.springairagengine.infrastracture.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rag.retrieval")
public record RetrievalProperties(
    int topK,
    double similarityThreshold
) {}

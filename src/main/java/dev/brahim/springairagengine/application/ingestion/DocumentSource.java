package dev.brahim.springairagengine.application.ingestion;

import org.springframework.core.io.Resource;

public record DocumentSource(
    Resource resource,
    String filename
) {}

package dev.brahim.springairagengine.application.ingestion;

import org.springframework.ai.document.Document;

import java.util.List;

public interface DocumentWriter {
    void write(List<Document> documents);
}

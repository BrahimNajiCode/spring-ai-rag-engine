package dev.brahim.springairagengine.application.ingestion;

import org.springframework.ai.document.Document;

import java.util.List;

public interface DocumentReader {
    List<Document> read(DocumentSource source);
}

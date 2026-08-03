package dev.brahim.springairagengine.infrastracture.springai;

import dev.brahim.springairagengine.application.ingestion.DocumentReader;
import dev.brahim.springairagengine.application.ingestion.DocumentSource;
import org.springframework.ai.document.Document;

import java.util.List;

public class SpringAiDocumentReader implements DocumentReader {
    @Override
    public List<Document> read(DocumentSource source) {
        return List.of();
    }
}

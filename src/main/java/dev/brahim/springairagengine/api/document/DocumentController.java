package dev.brahim.springairagengine.api.document;

import dev.brahim.springairagengine.application.ingestion.DocumentIngestionService;

public class DocumentController {
    private final DocumentIngestionService ingestionService;

    public DocumentController(DocumentIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }
}

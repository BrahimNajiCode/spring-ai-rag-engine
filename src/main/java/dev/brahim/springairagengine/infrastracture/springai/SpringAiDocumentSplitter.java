package dev.brahim.springairagengine.infrastracture.springai;


import dev.brahim.springairagengine.application.ingestion.DocumentSplitter;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;

import java.util.List;

public class SpringAiDocumentSplitter implements DocumentSplitter {

    private final TextSplitter textSplitter;
    public SpringAiDocumentSplitter(TextSplitter textSplitter) {
        this.textSplitter = textSplitter;
    }

    @Override
    public List<Document> split(List<Document> documents) {
        return List.of();
    }
}

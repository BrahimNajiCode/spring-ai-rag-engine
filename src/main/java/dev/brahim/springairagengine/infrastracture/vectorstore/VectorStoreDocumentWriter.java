package dev.brahim.springairagengine.infrastracture.vectorstore;

import dev.brahim.springairagengine.application.ingestion.DocumentWriter;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;

public class VectorStoreDocumentWriter implements DocumentWriter {
    private final VectorStore vectorStore;

    public VectorStoreDocumentWriter(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void write(List<Document> documents) {

    }
}

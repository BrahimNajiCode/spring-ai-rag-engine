package dev.brahim.springairagengine.infrastracture.vectorstore;

import dev.brahim.springairagengine.application.retrieval.DocumentRetriever;
import dev.brahim.springairagengine.domain.query.ProcessedQuery;
import dev.brahim.springairagengine.domain.retrieval.RetrievedDocument;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;

public class VectorStoreDocumentRetriever implements DocumentRetriever {
    private final VectorStore vectorStore;

    public VectorStoreDocumentRetriever(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public List<RetrievedDocument> retrieve(ProcessedQuery query) {
        return List.of();
    }
}

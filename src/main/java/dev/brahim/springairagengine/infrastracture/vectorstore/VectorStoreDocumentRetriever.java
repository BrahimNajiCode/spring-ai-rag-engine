package dev.brahim.springairagengine.infrastracture.vectorstore;

import dev.brahim.springairagengine.application.retrieval.DocumentRetriever;
import dev.brahim.springairagengine.domain.query.ProcessedQuery;
import dev.brahim.springairagengine.domain.retrieval.RetrievedDocument;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;
import java.util.Objects;

public class VectorStoreDocumentRetriever implements DocumentRetriever {
    private final VectorStore vectorStore;
    private final int topK;
    private final double similarityThreshold;

    public VectorStoreDocumentRetriever(VectorStore vectorStore, int topK, double similarityThreshold) {
        this.vectorStore = Objects.requireNonNull(vectorStore);

        if (topK <= 0) {
            throw new IllegalArgumentException(
                    "TopK must be greater than zero"
            );
        }

        if (similarityThreshold < 0.0 || similarityThreshold > 1.0) {
            throw new IllegalArgumentException(
                    "SimilarityThreshold must be between 0.0 and 1.0"
            );
        }

        this.topK = topK;
        this.similarityThreshold = similarityThreshold;
    }


    @Override
    public List<RetrievedDocument> retrieve(ProcessedQuery query) {
        Objects.requireNonNull(query, "Query must not be null");

        SearchRequest searchRequest = SearchRequest.builder()
                .query(query.query())
                .topK(this.topK)
                .similarityThreshold(this.similarityThreshold)
                .build();
        return vectorStore.similaritySearch(searchRequest)
                .stream()
                .map(this::toRetrievedDocument)
                .toList();
    }

    private RetrievedDocument toRetrievedDocument(Document document) {
        return new RetrievedDocument(
                document.getText(),
                document.getMetadata(),
                document.getScore()
        );
    }
}

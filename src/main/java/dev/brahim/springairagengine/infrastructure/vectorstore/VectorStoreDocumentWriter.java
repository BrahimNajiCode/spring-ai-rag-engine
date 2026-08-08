package dev.brahim.springairagengine.infrastructure.vectorstore;

import dev.brahim.springairagengine.application.ingestion.DocumentWriter;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


@Component
public class VectorStoreDocumentWriter implements DocumentWriter {
    private final VectorStore vectorStore;

    public VectorStoreDocumentWriter(VectorStore vectorStore) {
        this.vectorStore = Objects.requireNonNull(vectorStore);
    }

    @Override
    public void write(List<Document> documents) {

        Objects.requireNonNull(documents, "Documents must be not null.");
        if(documents.isEmpty()){
            return;
        }

        /**
         * Spring AI's VectorStore.add(...) handles the vector-store persistence flow,
         * and PGVector uses the configured EmbeddingModel to generate embeddings.
         * */

        vectorStore.add(documents);
    }
}

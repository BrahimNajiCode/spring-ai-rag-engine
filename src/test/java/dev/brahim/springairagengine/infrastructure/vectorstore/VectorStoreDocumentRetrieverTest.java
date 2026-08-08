package dev.brahim.springairagengine.infrastructure.vectorstore;


import dev.brahim.springairagengine.domain.query.ProcessedQuery;
import dev.brahim.springairagengine.domain.retrieval.RetrievedDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VectorStoreDocumentRetrieverTest {
    @Mock
    private VectorStore vectorStore;

    @Test
    void shouldRetrievedMapDocuments(){
        // Arrange
        var retriever = new VectorStoreDocumentRetriever(
            vectorStore,
            5,
            0.7
        );
        var query = new ProcessedQuery("What is RAG?");
        var document = Document.builder()
                .text("RAG combines retrieval and generation.")
                .metadata(Map.of("filename", "rag.pdf"))
                .score(0.91d)
                .build();

        when(vectorStore.similaritySearch(any(SearchRequest.class)))
                .thenReturn(List.of(document));

        // Act
        List<RetrievedDocument> result =
                retriever.retrieve(query);

        // Assert
        assertEquals(1, result.size());

        RetrievedDocument retrieved = result.getFirst();

        assertEquals(
                "RAG combines retrieval and generation.",
                retrieved.content()
        );

        assertEquals(
                "rag.pdf",
                retrieved.metadata().get("filename")
        );

        assertEquals(
                0.91,
                retrieved.score()
        );

        verify(vectorStore).similaritySearch(any(SearchRequest.class));
    }





    @Test
    void shouldUseConfiguredRetrievalParameters() {
        // Arrange
        var retriever = new VectorStoreDocumentRetriever(
                vectorStore,
                5,
                0.7
        );

        var query = new ProcessedQuery("What is RAG?");

        when(vectorStore.similaritySearch(any(SearchRequest.class)))
                .thenReturn(List.of());

        // Act
        retriever.retrieve(query);

        // Assert
        ArgumentCaptor<SearchRequest> captor =
                ArgumentCaptor.forClass(SearchRequest.class);

        verify(vectorStore).similaritySearch(captor.capture());

        SearchRequest request = captor.getValue();

        assertEquals("What is RAG?", request.getQuery());
        assertEquals(5, request.getTopK());
        assertEquals(0.7, request.getSimilarityThreshold());
    }


    @Test
    void shouldReturnEmptyListWhenNoDocumentsMatch() {
        // Arrange
        var retriever = new VectorStoreDocumentRetriever(
                vectorStore,
                5,
                0.7
        );

        when(vectorStore.similaritySearch(any(SearchRequest.class)))
                .thenReturn(List.of());

        // Act
        List<RetrievedDocument> result =
                retriever.retrieve(
                        new ProcessedQuery("unknown topic")
                );

        // Assert
        assertTrue(result.isEmpty());
    }


}

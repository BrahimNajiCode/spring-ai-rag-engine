package dev.brahim.springairagengine.application.rag.advanced;

import dev.brahim.springairagengine.application.rag.RagRequest;
import dev.brahim.springairagengine.application.rag.RagResponse;
import dev.brahim.springairagengine.application.retrieval.DocumentRetriever;
import dev.brahim.springairagengine.domain.generation.AnswerGenerator;
import dev.brahim.springairagengine.domain.generation.GeneratedAnswer;
import dev.brahim.springairagengine.domain.query.ProcessedQuery;
import dev.brahim.springairagengine.domain.query.QueryProcessor;
import dev.brahim.springairagengine.domain.retrieval.RetrievedDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdvancedRagEngineTest {
    @Mock
    private QueryProcessor queryProcessor;
    @Mock
    private DocumentRetriever documentRetriever;
    @Mock
    private AnswerGenerator answerGenerator;

    private AdvancedRagEngine ragEngine;

    @BeforeEach
    void setUp() {
        ragEngine = new AdvancedRagEngine(
                queryProcessor,
                documentRetriever,
                answerGenerator
        );
    }

    @Test
    void shouldImplementRagEngineContract() {
        // Arrange
        RagRequest request =
                new RagRequest("What is advanced RAG?");

        ProcessedQuery processedQuery =
                new ProcessedQuery("What is advanced RAG?");

        RetrievedDocument document = new RetrievedDocument(
                "Advanced RAG improves retrieval with processing steps.",
                Map.of("source", "advanced-rag.pdf"),
                0.94
        );

        List<RetrievedDocument> documents = List.of(document);

        GeneratedAnswer generatedAnswer =
                new GeneratedAnswer(
                        "Advanced RAG improves retrieval with processing steps."
                );

        when(queryProcessor.process(request.query()))
                .thenReturn(processedQuery);

        when(documentRetriever.retrieve(processedQuery))
                .thenReturn(documents);

        when(answerGenerator.generate(processedQuery, documents))
                .thenReturn(generatedAnswer);

        // Act
        RagResponse ragResponse = ragEngine.answer(request);

        // Assert
        assertEquals(
                generatedAnswer.content(),
                ragResponse.answer()
        );
        assertEquals(
                documents,
                ragResponse.sources()
        );
    }

    @Test
    void shouldExecuteComponentsInCorrectOrder() {
        // Arrange
        RagRequest request =
                new RagRequest("How does advanced RAG work?");

        ProcessedQuery processedQuery =
                new ProcessedQuery("How does advanced RAG work?");

        List<RetrievedDocument> documents =
                List.of(
                        new RetrievedDocument(
                                "Advanced RAG orchestrates its own pipeline.",
                                Map.of("source", "pipeline.pdf"),
                                0.90
                        )
                );

        GeneratedAnswer answer =
                new GeneratedAnswer(
                        "Advanced RAG orchestrates its own pipeline."
                );

        when(queryProcessor.process(request.query()))
                .thenReturn(processedQuery);

        when(documentRetriever.retrieve(processedQuery))
                .thenReturn(documents);

        when(answerGenerator.generate(
                processedQuery,
                documents
        )).thenReturn(answer);

        // Act
        ragEngine.answer(request);

        // Assert
        InOrder inOrder = inOrder(
                queryProcessor,
                documentRetriever,
                answerGenerator
        );

        inOrder.verify(queryProcessor)
                .process(request.query());

        inOrder.verify(documentRetriever)
                .retrieve(processedQuery);

        inOrder.verify(answerGenerator)
                .generate(processedQuery, documents);
    }

    @Test
    void shouldRejectNullRequest() {
        // Act + Assert
        try {
            ragEngine.answer(null);
        } catch (NullPointerException expected) {
            assertEquals("Request must not be null", expected.getMessage());
            return;
        }
        throw new AssertionError(
                "Expected NullPointerException for null request"
        );
    }
}
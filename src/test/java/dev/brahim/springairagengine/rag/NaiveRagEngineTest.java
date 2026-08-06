package dev.brahim.springairagengine.rag;


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
public class NaiveRagEngineTest {
    @Mock
    private QueryProcessor queryProcessor;
    @Mock
    private DocumentRetriever documentRetriever;
    @Mock
    private AnswerGenerator answerGenerator;

    private NaiveRagEngine ragEngine;

    @BeforeEach
    void setUp(){
        ragEngine = new NaiveRagEngine(
            queryProcessor,
            documentRetriever,
            answerGenerator
        );
    }
    @Test
    void shouldOrchestrateNaiveRagFlow(){
        // Arrange
        RagRequest request =
                new RagRequest("What is Spring AI?");

        ProcessedQuery processedQuery =
                new ProcessedQuery("What is Spring AI?");

        RetrievedDocument document = new RetrievedDocument(
                "Spring AI provides abstractions for AI applications.",
                Map.of("source", "spring-ai.pdf"),
                0.92
        );

        List<RetrievedDocument> documents = List.of(document);
        GeneratedAnswer generatedAnswer =
                new GeneratedAnswer(
                        "Spring AI provides abstractions for building AI applications."
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
                new RagRequest("What is RAG?");

        ProcessedQuery processedQuery =
                new ProcessedQuery("What is RAG?");

        List<RetrievedDocument> documents =
                List.of(
                        new RetrievedDocument(
                                "RAG retrieves relevant context.",
                                Map.of("source", "rag.pdf"),
                                0.91
                        )
                );

        GeneratedAnswer answer =
                new GeneratedAnswer("RAG retrieves relevant context.");

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
    void shouldReturnRetrievedDocumentsAsSources() {
        // Arrange
        RagRequest request =
                new RagRequest("What is RAG?");

        ProcessedQuery processedQuery =
                new ProcessedQuery("What is RAG?");

        RetrievedDocument source =
                new RetrievedDocument(
                        "RAG retrieves relevant documents.",
                        Map.of("source", "rag.pdf"),
                        0.95
                );

        List<RetrievedDocument> sources =
                List.of(source);

        when(queryProcessor.process(request.query()))
                .thenReturn(processedQuery);

        when(documentRetriever.retrieve(processedQuery))
                .thenReturn(sources);

        when(answerGenerator.generate(
                processedQuery,
                sources
        )).thenReturn(
                new GeneratedAnswer("RAG retrieves documents.")
        );

        // Act
        RagResponse response =
                ragEngine.answer(request);

        // Assert
        assertEquals(sources, response.sources());
    }
}

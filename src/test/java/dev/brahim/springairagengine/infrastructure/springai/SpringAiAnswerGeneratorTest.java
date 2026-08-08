package dev.brahim.springairagengine.infrastructure.springai;

import dev.brahim.springairagengine.domain.generation.GeneratedAnswer;
import dev.brahim.springairagengine.domain.query.ProcessedQuery;
import dev.brahim.springairagengine.domain.retrieval.RetrievedDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpringAiAnswerGeneratorTest {

    @Mock
    private ChatClient chatClient;

    @Mock
    private ChatClient.ChatClientRequestSpec requestSpec;

    @Mock
    private ChatClient.CallResponseSpec responseSpec;

    private SpringAiAnswerGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new SpringAiAnswerGenerator(chatClient);
    }

    @Test
    void shouldGenerateAnswerFromRetrievedContext() {
        // Arrange
        var query = new ProcessedQuery(
                "What is Spring AI?"
        );

        var documents = List.of(
                new RetrievedDocument(
                        "Spring AI provides abstractions for AI applications.",
                        Map.of("source", "spring-ai.pdf"),
                        0.92
                ),
                new RetrievedDocument(
                        "Spring AI supports vector stores.",
                        Map.of("source", "rag.pdf"),
                        0.87
                )
        );

        when(chatClient.prompt())
                .thenReturn(requestSpec);

        when(requestSpec.system(any(Consumer.class)))
                .thenReturn(requestSpec);

        when(requestSpec.user(query.query()))
                .thenReturn(requestSpec);

        when(requestSpec.call())
                .thenReturn(responseSpec);

        when(responseSpec.content())
                .thenReturn(
                        "Spring AI provides abstractions for building AI applications."
                );

        // Act
        GeneratedAnswer result =
                generator.generate(query, documents);

        // Assert
        assertEquals(
                "Spring AI provides abstractions for building AI applications.",
                result.content()
        );
    }

    @Test
    void shouldGenerateAnswerWhenNoDocumentsAreRetrieved() {
        // Arrange
        var query = new ProcessedQuery("What is Spring AI?");

        when(chatClient.prompt())
                .thenReturn(requestSpec);

        when(requestSpec.system(any(Consumer.class)))
                .thenReturn(requestSpec);

        when(requestSpec.user(query.query()))
                .thenReturn(requestSpec);

        when(requestSpec.call())
                .thenReturn(responseSpec);

        when(responseSpec.content())
                .thenReturn("I do not have enough information.");

        // Act
        GeneratedAnswer result =
                generator.generate(query, List.of());

        // Assert
        assertEquals(
                "I do not have enough information.",
                result.content()
        );
    }
}

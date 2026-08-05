package dev.brahim.springairagengine.domain.query;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DefaultQueryProcessorTest {
    private final QueryProcessor queryProcessor = new DefaultQueryProcessor();

    @Test
    void shouldProcessQuery(){
        // Arrange
        String rawQuery = "What is Spring AI?";

        // Act
        ProcessedQuery processedQuery = queryProcessor.process(rawQuery);


        // Assert

        assertEquals(
                "What is Spring AI?",
                processedQuery.query()
        );
    }

    @Test
    void shouldTrimQueryWhitespace(){
        // Arrange
        String rawQuery = "   What is Spring AI?";

        // Act
        ProcessedQuery processedQuery = queryProcessor.process(rawQuery);

        // Assert

        assertEquals(
                "What is Spring AI?",
                processedQuery.query()
        );
    }

    @Test
    void shouldRejectBlankQuery() {
        assertThrows(
                IllegalArgumentException.class,
                () -> queryProcessor.process("   ")
        );
    }

    @Test
    void shouldRejectNullQuery() {
        assertThrows(
                IllegalArgumentException.class,
                () -> queryProcessor.process(null)
        );
    }
}

package dev.brahim.springairagengine.api.rag;

import dev.brahim.springairagengine.application.rag.RagEngine;
import dev.brahim.springairagengine.application.rag.RagRequest;
import dev.brahim.springairagengine.application.rag.RagResponse;
import dev.brahim.springairagengine.domain.retrieval.RetrievedDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RagController.class)
class RagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RagEngine ragEngine;

    @Test
    void shouldReturnAnswerAndSourcesForValidQuery() throws Exception {
        // Arrange
        var source = new RetrievedDocument(
                "Spring AI provides abstractions for AI applications.",
                Map.of("source", "spring-ai.pdf", "page", 4),
                0.92
        );

        var ragResponse = new RagResponse(
                "Spring AI provides abstractions for building AI applications.",
                List.of(source)
        );

        when(ragEngine.answer(any(RagRequest.class)))
                .thenReturn(ragResponse);

        // Act + Assert
        mockMvc.perform(post("/api/v1/rag/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "query": "What is Spring AI?"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer")
                        .value("Spring AI provides abstractions for building AI applications."))
                .andExpect(jsonPath("$.sources[0].content")
                        .value("Spring AI provides abstractions for AI applications."))
                .andExpect(jsonPath("$.sources[0].metadata.source")
                        .value("spring-ai.pdf"))
                .andExpect(jsonPath("$.sources[0].metadata.page")
                        .value(4))
                .andExpect(jsonPath("$.sources[0].score")
                        .value(0.92));

        verify(ragEngine).answer(argThat(request ->
                request.query().equals("What is Spring AI?")));
    }

    @Test
    void shouldRejectBlankQuery() throws Exception {
        // Act + Assert
        mockMvc.perform(post("/api/v1/rag/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "query": "   "
                                }
                                """))
                .andExpect(status().isBadRequest());

        verify(ragEngine, never()).answer(any(RagRequest.class));
    }

    @Test
    void shouldRejectMissingQuery() throws Exception {
        // Act + Assert
        mockMvc.perform(post("/api/v1/rag/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        verify(ragEngine, never()).answer(any(RagRequest.class));
    }
}

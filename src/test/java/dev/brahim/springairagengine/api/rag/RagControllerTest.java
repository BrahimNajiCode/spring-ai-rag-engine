package dev.brahim.springairagengine.api.rag;

import dev.brahim.springairagengine.application.rag.RagEngine;
import dev.brahim.springairagengine.application.rag.RagRequest;
import dev.brahim.springairagengine.application.rag.RagResponse;
import dev.brahim.springairagengine.domain.retrieval.RetrievedDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(RagController.class)
class RagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RagEngine ragEngine;

    @Test
    void shouldReturnGeneratedAnswerAndSources() throws Exception {
        var source = new RetrievedDocument(
                "Spring AI provides abstractions for AI applications.",
                Map.of("source", "spring-ai.pdf"),
                0.92
        );

        when(ragEngine.answer(any(RagRequest.class)))
                .thenReturn(new RagResponse(
                        "Spring AI provides abstractions for building AI applications.",
                        List.of(source)
                ));

        mockMvc.perform(post("/api/v1/rag/query")
                        .contentType(APPLICATION_JSON)
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
                .andExpect(jsonPath("$.sources[0].score")
                        .value(0.92));

        verify(ragEngine).answer(any(RagRequest.class));
    }

    @Test
    void shouldRejectBlankQuery() throws Exception {
        mockMvc.perform(post("/api/v1/rag/query")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "query": "   "
                                }
                                """))
                .andExpect(status().isBadRequest());

        verify(ragEngine, never()).answer(any(RagRequest.class));
    }
}

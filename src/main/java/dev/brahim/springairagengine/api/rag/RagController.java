package dev.brahim.springairagengine.api.rag;

import dev.brahim.springairagengine.application.rag.RagEngine;
import dev.brahim.springairagengine.application.rag.RagRequest;
import dev.brahim.springairagengine.application.rag.RagResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rag")
public class RagController {

    private final RagEngine ragEngine;

    public RagController(RagEngine ragEngine) {
        this.ragEngine = ragEngine;
    }

    @PostMapping("/query")
    public ResponseEntity<RagResponse> query(
            @Valid @RequestBody RagQueryRequest request
    ) {
        RagRequest ragRequest = new RagRequest(request.query());
        RagResponse response = ragEngine.answer(ragRequest);

        return ResponseEntity.ok(response);
    }
}

package dev.brahim.springairagengine.api.rag;

import dev.brahim.springairagengine.application.rag.RagEngine;

public class RagController {
    private final RagEngine ragEngine;

    public RagController(RagEngine ragEngine) {
        this.ragEngine = ragEngine;
    }
}

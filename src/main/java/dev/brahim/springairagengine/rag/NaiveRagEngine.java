package dev.brahim.springairagengine.rag;

import dev.brahim.springairagengine.application.retrieval.DocumentRetriever;
import dev.brahim.springairagengine.domain.generation.AnswerGenerator;
import dev.brahim.springairagengine.domain.query.QueryProcessor;

public class NaiveRagEngine {
    private final QueryProcessor queryProcessor;
    private final DocumentRetriever documentRetriever;
    private final AnswerGenerator answerGenerator;

    public NaiveRagEngine(QueryProcessor queryProcessor, DocumentRetriever documentRetriever, AnswerGenerator answerGenerator) {
        this.queryProcessor = queryProcessor;
        this.documentRetriever = documentRetriever;
        this.answerGenerator = answerGenerator;
    }
}

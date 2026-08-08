package dev.brahim.springairagengine.application.rag.naive;

import dev.brahim.springairagengine.application.rag.RagEngine;
import dev.brahim.springairagengine.application.rag.RagRequest;
import dev.brahim.springairagengine.application.rag.RagResponse;
import dev.brahim.springairagengine.application.retrieval.DocumentRetriever;
import dev.brahim.springairagengine.domain.generation.AnswerGenerator;
import dev.brahim.springairagengine.domain.generation.GeneratedAnswer;
import dev.brahim.springairagengine.domain.query.ProcessedQuery;
import dev.brahim.springairagengine.domain.query.QueryProcessor;
import dev.brahim.springairagengine.domain.retrieval.RetrievedDocument;

import java.util.List;
import java.util.Objects;

public class NaiveRagEngine implements RagEngine {
    private final QueryProcessor queryProcessor;
    private final DocumentRetriever documentRetriever;
    private final AnswerGenerator answerGenerator;

    public NaiveRagEngine(
            QueryProcessor queryProcessor,
            DocumentRetriever documentRetriever,
            AnswerGenerator answerGenerator
    ) {
        this.queryProcessor =
                Objects.requireNonNull(queryProcessor);
        this.documentRetriever =
                Objects.requireNonNull(documentRetriever);
        this.answerGenerator =
                Objects.requireNonNull(answerGenerator);
    }

    @Override
    public RagResponse answer(RagRequest request) {
        Objects.requireNonNull(request, "Request must not be null");

        ProcessedQuery processedQuery =
                queryProcessor.process(request.query());

        List<RetrievedDocument> retrievedDocuments =
                documentRetriever.retrieve(processedQuery);

        GeneratedAnswer generatedAnswer =
                answerGenerator.generate(processedQuery, retrievedDocuments);

        return new RagResponse(
                generatedAnswer.content(),
                retrievedDocuments
        );
    }

}

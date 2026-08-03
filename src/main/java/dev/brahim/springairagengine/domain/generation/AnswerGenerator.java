package dev.brahim.springairagengine.domain.generation;

import dev.brahim.springairagengine.domain.query.ProcessedQuery;
import dev.brahim.springairagengine.domain.retrieval.RetrievedDocument;

import java.util.List;

public interface AnswerGenerator {
    GeneratedAnswer generate(ProcessedQuery query, List<RetrievedDocument> documents);
}

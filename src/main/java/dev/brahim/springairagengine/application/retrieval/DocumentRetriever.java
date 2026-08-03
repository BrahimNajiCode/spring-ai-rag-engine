package dev.brahim.springairagengine.application.retrieval;

import dev.brahim.springairagengine.domain.query.ProcessedQuery;
import dev.brahim.springairagengine.domain.retrieval.RetrievedDocument;

import java.util.List;

public interface DocumentRetriever {
    List<RetrievedDocument> retrieve(ProcessedQuery query);
}

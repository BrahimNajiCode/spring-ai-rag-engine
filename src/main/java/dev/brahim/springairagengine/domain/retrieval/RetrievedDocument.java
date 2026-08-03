package dev.brahim.springairagengine.domain.retrieval;

import java.util.Map;

public record RetrievedDocument(
   String content,
   Map<Object, String> metadata,
   double score
) {}

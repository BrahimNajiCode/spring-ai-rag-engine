package dev.brahim.springairagengine.domain.query;

public class DefaultQueryProcessor implements QueryProcessor{
    @Override
    public ProcessedQuery process(String query) {
        if (query == null) {
            throw new IllegalArgumentException(
                    "Query must not be null"
            );
        }

        String normalizedQuery = query.trim();

        return new ProcessedQuery(normalizedQuery);
    }
}

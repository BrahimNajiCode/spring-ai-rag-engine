package dev.brahim.springairagengine.infrastructure.configuration;

import dev.brahim.springairagengine.application.rag.RagEngine;
import dev.brahim.springairagengine.application.rag.naive.NaiveRagEngine;
import dev.brahim.springairagengine.application.retrieval.DocumentRetriever;
import dev.brahim.springairagengine.domain.generation.AnswerGenerator;
import dev.brahim.springairagengine.domain.query.DefaultQueryProcessor;
import dev.brahim.springairagengine.domain.query.QueryProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RagConfiguration {
    @Bean
    QueryProcessor queryProcessor(){
        return new DefaultQueryProcessor();
    }

    @Bean
    RagEngine naiveRagEngine(
            QueryProcessor queryProcessor,
            DocumentRetriever documentRetriever,
            AnswerGenerator answerGenerator
    ) {
        return new NaiveRagEngine(
                queryProcessor,
                documentRetriever,
                answerGenerator
        );
    }
}

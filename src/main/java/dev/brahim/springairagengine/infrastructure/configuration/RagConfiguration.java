package dev.brahim.springairagengine.infrastructure.configuration;

import dev.brahim.springairagengine.application.rag.RagEngine;
import dev.brahim.springairagengine.application.rag.advanced.AdvancedRagEngine;
import dev.brahim.springairagengine.application.rag.naive.NaiveRagEngine;
import dev.brahim.springairagengine.application.retrieval.DocumentRetriever;
import dev.brahim.springairagengine.domain.generation.AnswerGenerator;
import dev.brahim.springairagengine.domain.query.DefaultQueryProcessor;
import dev.brahim.springairagengine.domain.query.QueryProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RagProperties.class)
public class RagConfiguration {
    @Bean
    QueryProcessor queryProcessor(){
        return new DefaultQueryProcessor();
    }

    @Bean
    NaiveRagEngine naiveRagEngine(
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

    @Bean
    AdvancedRagEngine advancedRagEngine(
            QueryProcessor queryProcessor,
            DocumentRetriever documentRetriever,
            AnswerGenerator answerGenerator
    ) {
        return new AdvancedRagEngine(
                queryProcessor,
                documentRetriever,
                answerGenerator
        );
    }

    @Bean
    RagEngine ragEngine(
            RagProperties properties,
            NaiveRagEngine naiveRagEngine,
            AdvancedRagEngine advancedRagEngine
    ) {
        return switch (properties.strategy()) {
            case ADVANCED -> advancedRagEngine;
            case NAIVE, MODULAR, PRODUCTION -> naiveRagEngine;
        };
    }
}

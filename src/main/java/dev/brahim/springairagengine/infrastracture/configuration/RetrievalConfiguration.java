package dev.brahim.springairagengine.infrastracture.configuration;


import dev.brahim.springairagengine.application.retrieval.DocumentRetriever;
import dev.brahim.springairagengine.infrastracture.vectorstore.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RetrievalProperties.class)
public class RetrievalConfiguration {
    @Bean
    DocumentRetriever documentRetriever(
            VectorStore vectorStore,
            RetrievalProperties properties
    ){
        return new VectorStoreDocumentRetriever(
                vectorStore,
                properties.topK(),
                properties.similarityThreshold()
        );
    }
}

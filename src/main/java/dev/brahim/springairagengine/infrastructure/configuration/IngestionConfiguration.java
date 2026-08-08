package dev.brahim.springairagengine.infrastructure.configuration;


import dev.brahim.springairagengine.application.ingestion.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IngestionConfiguration {

    @Bean
    DocumentIngestionService documentIngestionService(
        DocumentReader documentReader,
        DocumentSplitter documentSplitter,
        DocumentWriter documentWriter
    ){
        return new DefaultDocumentIngestionService(
          documentReader,
          documentSplitter,
          documentWriter
        );
    }
}

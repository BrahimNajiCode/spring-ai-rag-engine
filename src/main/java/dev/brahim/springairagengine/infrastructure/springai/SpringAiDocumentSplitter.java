package dev.brahim.springairagengine.infrastructure.springai;


import dev.brahim.springairagengine.application.ingestion.DocumentSplitter;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


@Component
public class SpringAiDocumentSplitter implements DocumentSplitter {

    private final TextSplitter textSplitter;
    public SpringAiDocumentSplitter() {
        this.textSplitter = TokenTextSplitter.builder()
                .withChunkSize(600)
                .withMinChunkSizeChars(300)
                .withMinChunkLengthToEmbed(100)
                .withMaxNumChunks(10_000)
                .withKeepSeparator(true)
                .build();
    }

    @Override
    public List<Document> split(List<Document> documents) {
        Objects.requireNonNull(documents, "Documents must be not null.");
        if(documents.isEmpty()){
            return List.of();
        }
        return textSplitter.split(documents);
    }
}

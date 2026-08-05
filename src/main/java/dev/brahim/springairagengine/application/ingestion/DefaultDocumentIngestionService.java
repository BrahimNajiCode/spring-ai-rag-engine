package dev.brahim.springairagengine.application.ingestion;

import org.springframework.ai.document.Document;

import java.util.List;
import java.util.Objects;

public class DefaultDocumentIngestionService implements DocumentIngestionService {

    private final DocumentReader documentReader ;
    private final DocumentSplitter documentSplitter;
    private final DocumentWriter documentWriter;

    public DefaultDocumentIngestionService(DocumentReader documentReader, DocumentSplitter documentSplitter, DocumentWriter documentWriter) {
        this.documentReader = Objects.requireNonNull(documentReader);
        this.documentSplitter = Objects.requireNonNull(documentSplitter);
        this.documentWriter = Objects.requireNonNull(documentWriter);
    }


    /**
     * This is the application orchestrator. (Abstract)
     * Its only job is: READ → SPLIT → WRITE
     * That's important because the application layer
     * shouldn't care how infrastructure performs those operations.
     **/
    @Override
    public void ingest(DocumentSource source) {
        Objects.requireNonNull(source, "Source must be not null.");

        List<Document> documents = documentReader.read(source);

        if(documents.isEmpty()){
            return;
        }

        List<Document> chunks = documentSplitter.split(documents);

        if(chunks.isEmpty()){
            return;
        }

        documentWriter.write(chunks);
    }
}

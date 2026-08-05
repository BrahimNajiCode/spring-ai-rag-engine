package dev.brahim.springairagengine.infrastracture.springai;

import dev.brahim.springairagengine.application.ingestion.DocumentReader;
import dev.brahim.springairagengine.application.ingestion.DocumentSource;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


@Component
public class SpringAiDocumentReader implements DocumentReader {

    private static final String METADATA_FILENAME = "filename";


    @Override
    public List<Document> read(DocumentSource source) {
        Objects.requireNonNull(source, "Source must be not null.");

        var reader = new TikaDocumentReader(source.resource());

        return reader.read()
                .stream()
                .map(document -> addFileNameMetadata(document, source.filename()))
                .toList();
    }

    private Document addFileNameMetadata(
            Document document,
            String fileName
    ){
        return document.mutate()
                .metadata(METADATA_FILENAME, fileName)
                .build();
    }
}

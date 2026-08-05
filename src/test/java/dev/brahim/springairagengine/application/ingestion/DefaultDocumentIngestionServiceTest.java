package dev.brahim.springairagengine.application.ingestion;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * It tells JUnit to load MockitoExtension, which initializes Mockito
 * features such as @Mock, @Spy, and @InjectMocks before each test.
 **/

@ExtendWith(MockitoExtension.class)
public class DefaultDocumentIngestionServiceTest {
    @Mock
    private DocumentReader documentReader;

    @Mock
    private DocumentSplitter documentSplitter;

    @Mock
    private DocumentWriter documentWriter;

    @Mock
    private Resource resource;

    private DefaultDocumentIngestionService ingestionService;

    @BeforeEach
    void setUp(){
        ingestionService = new DefaultDocumentIngestionService(
          documentReader,
          documentSplitter,
          documentWriter
        );
    }

    @Test
    void shouldReadSplitAndWriteDocument(){
        // Arrange
        DocumentSource source = new DocumentSource(
                resource,
                "spring.pdf"
        );

        Document document = new Document("Spring Ai Content");
        Document chunk = new Document("Spring Ai Chunk");

        List<Document> documents = List.of(document);
        List<Document> chunks = List.of(chunk);

        when(documentReader.read(source))
                .thenReturn(documents);
        when(documentSplitter.split(documents))
                .thenReturn(chunks);

        // Act

        ingestionService.ingest(source);

        // Assert
        InOrder inOrder = inOrder(
          documentReader,
          documentSplitter,
          documentWriter
        );

        inOrder.verify(documentReader).read(source);
        inOrder.verify(documentSplitter).split(documents);
        inOrder.verify(documentWriter).write(chunks);
    }

    @Test
    void shouldNotSplitOrWriteWhenNoDocumentsAreRead() {
        // Arrange
        DocumentSource source = new DocumentSource(
                resource,
                "empty.pdf"
        );

        when(documentReader.read(source))
                .thenReturn(List.of());

        // Act
        ingestionService.ingest(source);

        // Assert
        verify(documentReader).read(source);
        verifyNoInteractions(documentSplitter, documentWriter);
    }

    @Test
    void shouldNotWriteWhenNoChunksAreProduced() {
        // Arrange
        DocumentSource source = new DocumentSource(
                resource,
                "document.pdf"
        );

        Document document = new Document("content");

        List<Document> documents = List.of(document);

        when(documentReader.read(source))
                .thenReturn(documents);

        when(documentSplitter.split(documents))
                .thenReturn(List.of());

        // Act
        ingestionService.ingest(source);

        // Assert
        verify(documentReader).read(source);
        verify(documentSplitter).split(documents);
        verifyNoInteractions(documentWriter);
    }

}

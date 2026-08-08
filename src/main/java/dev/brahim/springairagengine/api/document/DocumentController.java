package dev.brahim.springairagengine.api.document;

import dev.brahim.springairagengine.application.ingestion.DocumentIngestionService;
import dev.brahim.springairagengine.application.ingestion.DocumentSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {
    private final DocumentIngestionService ingestionService;

    public DocumentController(DocumentIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> ingest(
            @RequestParam("file")MultipartFile file
    ){
        if (file.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        DocumentSource source = new DocumentSource(
                file.getResource(),
                file.getOriginalFilename()
        );
        ingestionService.ingest(source);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

package com.jk.ref_impls.trades.forex.prm.spring_ai.api;

import com.jk.ref_impls.trades.forex.prm.spring_ai.dtos.AIPromptRequest;
import com.jk.ref_impls.trades.forex.prm.spring_ai.dtos.BasicIndexingRequest;
import com.jk.ref_impls.trades.forex.prm.spring_ai.dtos.BasicIndexingResponse;
import com.jk.ref_impls.trades.forex.prm.spring_ai.services.indexing.RAGBasicIndexingService;
import com.jk.ref_impls.trades.forex.prm.spring_ai.services.rag.RAGBasicProcessorSvc;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
@RequestMapping("/api/ai/basic/rag")
@Validated
public class AIBasicRAGApi {

    @Autowired
    private RAGBasicIndexingService ragBasicIndexingService;

    @Autowired
    private RAGBasicProcessorSvc ragBasicProcessorSvc;

    @PostMapping(value = "/indexing/document/filesystem", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BasicIndexingResponse> indexDocumentsFromFileSystem(@RequestBody @Valid BasicIndexingRequest request) {
        var indexDocuments = ragBasicIndexingService.indexDocumentFromFileSystem(request);

        return ResponseEntity.ok(new BasicIndexingResponse(true, "Indexing started successfully for " + request.sourcePath()
        + " with " + indexDocuments.size() + " documents"));
    }

    @PostMapping(value = "/indexing/document/url", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BasicIndexingResponse> indexDocumentsFromUrl(@RequestBody @Valid BasicIndexingRequest request) throws Exception {
        var indexDocuments = ragBasicIndexingService.indexDocumentFromURL(request);

        return ResponseEntity.ok(new BasicIndexingResponse(true, "Indexing started successfully for " + request.url()
                + " with " + indexDocuments.size() + " documents"));
    }

    @PostMapping(value = "/rag/ask/basic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String > basicRAG(@RequestBody @Valid AIPromptRequest request,
                                  @RequestParam(name = "file-name") String fileNameForCustomerContext) {
        var response = ragBasicProcessorSvc.generateRAGResponse(request.systemPrompt(), request.userPrompt(),
                fileNameForCustomerContext);

        return Mono.just(response);
    }

    @PostMapping(value = "/rag/ask/basic-stream", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String > basicStreamRAG(@RequestBody @Valid AIPromptRequest request,
                                        @RequestParam(name = "file-name") String fileNameForCustomerContext) {
        return ragBasicProcessorSvc.generateStreamingRAGResponse(request.systemPrompt(), request.userPrompt(),
                fileNameForCustomerContext);
    }
}

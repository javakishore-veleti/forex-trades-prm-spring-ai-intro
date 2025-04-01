package com.jk.ref_impls.trades.forex.prm.spring_ai.api;

import com.jk.ref_impls.trades.forex.prm.spring_ai.dtos.BasicIndexingRequest;
import com.jk.ref_impls.trades.forex.prm.spring_ai.dtos.BasicIndexingResponse;
import com.jk.ref_impls.trades.forex.prm.spring_ai.services.indexing.RAGBasicIndexingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai/basic/rag")
@Validated
public class AIBasicRAGApi {

    @Autowired
    private RAGBasicIndexingService ragBasicIndexingService;

    @PostMapping(value = "/indexing/document/filesystem", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BasicIndexingResponse> indexDocumentsFromFileSystem(@RequestBody @Valid BasicIndexingRequest request) throws Exception {
        var indexDocuments = ragBasicIndexingService.indexDocumentFromFileSystem(request);

        return ResponseEntity.ok(new BasicIndexingResponse(true, "Indexing started successfully for " + request.sourcePath()
        + " with " + indexDocuments.size() + " documents"));
    }

}

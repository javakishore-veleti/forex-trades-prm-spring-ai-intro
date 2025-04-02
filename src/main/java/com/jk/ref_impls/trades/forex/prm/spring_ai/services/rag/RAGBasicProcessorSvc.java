package com.jk.ref_impls.trades.forex.prm.spring_ai.services.rag;

import reactor.core.publisher.Flux;

public interface RAGBasicProcessorSvc {

    String generateRAGResponse(String systemPrompt, String userPrompt, String customContextFileName);

    Flux<String> generateStreamingRAGResponse(String systemPrompt, String userPrompt, String customContextFileName);
}

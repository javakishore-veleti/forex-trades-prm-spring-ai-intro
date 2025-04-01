package com.jk.ref_impls.trades.forex.prm.spring_ai.services;

import reactor.core.publisher.Flux;

public interface AIService {

    // Blocking Response
    String getBasicResponse(String systemPrompt, String userPrompt);

    // Non-Blocking Response
    Flux<String> streamBasicResponse(String systemPrompt, String userPrompt);
}

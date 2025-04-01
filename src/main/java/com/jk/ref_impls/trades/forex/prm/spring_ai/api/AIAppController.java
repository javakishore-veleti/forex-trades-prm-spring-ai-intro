package com.jk.ref_impls.trades.forex.prm.spring_ai.api;

import com.jk.ref_impls.trades.forex.prm.spring_ai.dtos.AIPromptRequest;
import com.jk.ref_impls.trades.forex.prm.spring_ai.services.AIService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/ai")
@Validated
public class AIAppController {

    @Autowired
    @Qualifier("openAIService")
    private AIService aiService;

    @PostMapping(value = "/v1/basic", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> basicAI(@RequestBody @Valid AIPromptRequest promptRequest) {
        return Mono.just(this.aiService.getBasicResponse(promptRequest.systemPrompt(), promptRequest.userPrompt()));
    }

    @PostMapping(value = "/v1/advaned", consumes = "application/json", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<String> basicStreamAI(@RequestBody @Valid AIPromptRequest promptRequest) {
        return this.aiService.streamBasicResponse(promptRequest.systemPrompt(), promptRequest.userPrompt());
    }

}

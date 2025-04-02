package com.jk.ref_impls.trades.forex.prm.spring_ai.services.adapters;

import com.jk.ref_impls.trades.forex.prm.spring_ai.services.AIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Service("openAIService")
public class OpenAIAdapterSvcImpl implements AIService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenAIAdapterSvcImpl.class);

    private final ChatClient chatClient;

    public OpenAIAdapterSvcImpl(@Qualifier("openAIChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String getBasicResponse(String systemPrompt, String userPrompt) {
        LOGGER.info("Getting basic response for system prompt: {} and user prompt: {}", systemPrompt, userPrompt);
        Assert.hasText(userPrompt, "User prompt must not be empty");

        String result = this.chatClient.prompt().system(Optional.ofNullable(systemPrompt).orElse("")).user(userPrompt).advisors(new SimpleLoggerAdvisor()).call().content();
        LOGGER.info("Got response: {}", result);

        return result;
    }

    @Override
    public Flux<String> streamBasicResponse(String systemPrompt, String userPrompt) {
        LOGGER.info("Streaming basic response for system prompt: {} and user prompt: {}", systemPrompt, userPrompt);
        Assert.hasText(userPrompt, "User prompt must not be empty");

        Flux<String> result = this.chatClient.prompt().system(Optional.ofNullable(systemPrompt).orElse("")).user(userPrompt).advisors(new SimpleLoggerAdvisor()).stream().content();

        LOGGER.info("Got response streamBasicResponse: {}", result);
        return result;
    }


}
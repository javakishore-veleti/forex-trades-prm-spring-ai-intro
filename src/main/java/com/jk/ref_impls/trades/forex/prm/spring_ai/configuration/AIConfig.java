package com.jk.ref_impls.trades.forex.prm.spring_ai.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AIConfig {

    @Bean(name = "defaultRestClientBuilder")
    RestClient.Builder defaultRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean(name = "openAIChatClient")
    ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.build();
    }
}

package com.jk.ref_impls.trades.forex.prm.spring_ai.services.rag;

import com.jk.ref_impls.trades.forex.prm.spring_ai.services.AIService;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

@Service
public class RAGBasicProcessorSvcImpl implements RAGBasicProcessorSvc {

    private static final String KEY_CUSTOM_CONTEXT = "custom_context";
    private static final String KEY_QUESTION = "question";

    private PromptTemplate basicAugmentationTemplate;

    @Autowired
    @Qualifier("openAIService")
    public AIService aiService;

    public RAGBasicProcessorSvcImpl() {
        var ragBasicPromptTemplate = new ClassPathResource("prompts/rag-basic-template.st");
        basicAugmentationTemplate = new PromptTemplate(ragBasicPromptTemplate);
    }

    private String retrieveCustomContext(String fromFileName) {

        try {
            return new String(Files.readAllBytes(Paths.get(fromFileName)));
        } catch(Exception e) {
            return "";
        }
    }

    private String augmentUserPrompt(String originalUserPrompt, String customContext) {
        var templateMap = new HashMap<String, Object>();
        templateMap.put(KEY_QUESTION, originalUserPrompt);
        templateMap.put(KEY_CUSTOM_CONTEXT, customContext);

        return basicAugmentationTemplate.render(templateMap);
    }

    @Override
    public String generateRAGResponse(String systemPrompt, String userPrompt, String customContextFileName) {
        var customContext = retrieveCustomContext(customContextFileName);
        var augmentedPrompt = augmentUserPrompt(userPrompt, customContext);

        return aiService.getBasicResponse(systemPrompt, augmentedPrompt);
    }

    @Override
    public Flux<String> generateStreamingRAGResponse(String systemPrompt, String userPrompt, String customContextFileName) {
        var customContext = retrieveCustomContext(customContextFileName);
        var augmentedPrompt = augmentUserPrompt(userPrompt, customContext);

        return aiService.streamBasicResponse(systemPrompt, augmentedPrompt);
    }
}

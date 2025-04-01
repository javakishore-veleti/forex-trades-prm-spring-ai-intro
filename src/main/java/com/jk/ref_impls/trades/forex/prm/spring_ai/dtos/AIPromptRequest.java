package com.jk.ref_impls.trades.forex.prm.spring_ai.dtos;

import jakarta.validation.constraints.NotBlank;

public record AIPromptRequest(String systemPrompt, @NotBlank String userPrompt) {
}
package com.jk.ref_impls.trades.forex.prm.spring_ai.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record BasicIndexingRequest(
        String sourcePath,
        String outputFileName,
        boolean appendFileExists,
        List<String> keywords,
        @Pattern(regexp = "^(?i)(http|https)://.*$") String url
) {

}

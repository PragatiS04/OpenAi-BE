package com.challang.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatGptRequest {
    private final String prompt;
    private final String spiceLevel;
    private final String cookLevel;
    private final String allergy;
    private final int maxTokens;
}

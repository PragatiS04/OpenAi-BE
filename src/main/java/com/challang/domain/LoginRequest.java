package com.challang.domain;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class LoginRequest {
    private final String mobileNumber;
    private final String rand;
}

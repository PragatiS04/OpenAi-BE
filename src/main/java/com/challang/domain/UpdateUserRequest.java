package com.challang.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class UpdateUserRequest {
    private final String mobileNumber;
    private final String prompt;
    private final String name;
    private final String city;
    private final String instant;
}

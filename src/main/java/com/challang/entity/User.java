package com.challang.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Setter
@Builder
@Document
public class User {
    @Id
    ObjectId id;

    @CreatedDate
    Instant joiningDate;

    String city;
    String name;

    @NonNull
    String mobileNumber;

    @Builder.Default
    List<String> userHistory = new ArrayList<>();

    Instant dateOfBirth;
}
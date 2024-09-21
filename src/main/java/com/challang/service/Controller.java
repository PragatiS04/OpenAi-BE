package com.challang.service;

import com.challang.domain.ChatGptRequest;
import com.challang.domain.LoginRequest;
import com.challang.domain.UpdateUserRequest;
import com.challang.entity.User;
import com.challang.repository.UserRepository;
import lombok.AllArgsConstructor;
import okhttp3.*;

import okhttp3.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Instant;

@CrossOrigin(origins="*")
@RestController
@AllArgsConstructor
public class Controller {
    // Replace with your OpenAI API Key
    UserRepository userRepository;
    // Endpoint to call ChatGPT API
    @PostMapping("/generate-text")
    public String generateText(@org.springframework.web.bind.annotation.RequestBody ChatGptRequest request) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String prompt = generatePrompt(request);
        System.out.println(prompt);
        // Create the JSON body for the request
        String jsonBody = "{\n" +
                "  \"model\": \"gpt-3.5-turbo\",\n" +
                "  \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}],\n" +
                "  \"max_tokens\": " + request.getMaxTokens() + "\n" +
                "}";

        // Build the HTTP request to OpenAI's API
        RequestBody body = RequestBody.create(
                jsonBody, MediaType.parse("application/json; charset=utf-8"));

        Request httpRequest = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + "API_KEY")
                .post(body)
                .build();

        System.out.println(httpRequest);
        // Execute the request and capture the response
        try (Response response = client.newCall(httpRequest).execute()) {
            System.out.println(response);
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response.message());

            return response.body().string();
        }
    }

    @PostMapping("/register")
    public User registerUser(@org.springframework.web.bind.annotation.RequestBody LoginRequest request) {
        System.out.println("Entering");
        User user = User.builder()
                .joiningDate(Instant.now())
                .mobileNumber(request.getMobileNumber())
                .build();

        userRepository.save(user);

        return user;
    }

    @PutMapping("/update")
    public User UpdateUser(@org.springframework.web.bind.annotation.RequestBody UpdateUserRequest request) {
        User user = userRepository.findByMobileNumber(request.getMobileNumber()).orElseThrow();
        user.getUserHistory().add(request.getPrompt());
        user.setCity(request.getCity());
        user.setName(request.getName());
        if(request.getInstant() != null)
            user.setDateOfBirth(Instant.parse(request.getInstant()));
        userRepository.save(user);
        return user;
    }

    @GetMapping("{mobileNumber}")
    public User getUser(@org.springframework.web.bind.annotation.PathVariable String mobileNumber) {
        User user = userRepository.findByMobileNumber(mobileNumber).orElseThrow();

        return user;
    }

    private String generatePrompt(ChatGptRequest request) {
        String prompt = request.getPrompt();

        if(request.getSpiceLevel() != null) {
            prompt += " which is " + request.getSpiceLevel();
        }

        if(request.getCookLevel() != null) {
            prompt += " for a " + request.getCookLevel();
        }

        if(request.getAllergy() != null) {
            prompt += " and avoid " + request.getAllergy() + " ingredients since I am allergic to them";
        }

        return prompt;

    }
}

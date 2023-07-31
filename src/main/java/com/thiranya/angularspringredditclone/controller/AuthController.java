package com.thiranya.angularspringredditclone.controller;

import com.thiranya.angularspringredditclone.dto.AuthenticationResponse;
import com.thiranya.angularspringredditclone.dto.LoginRequest;
import com.thiranya.angularspringredditclone.dto.RegisterRequest;
import com.thiranya.angularspringredditclone.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/auth")
@Tag(name = "Auth", description = "The Auth API. Contains all the operations related to registering, activating and login into accounts.")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("register")
    @Operation(summary = "Register", description = "Sign up with Reddit by providing username, password & email")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return status(OK).body("User Registration Successful");
    }

    @PostMapping("accountVerification/{token}")
    @Operation(summary = "Verify Token", description = "Use the token sent to the user's email to verify the email and activate the account")
    public ResponseEntity<String> verifyToken(@PathVariable String token) {
        authService.verifyToken(token);
        return status(OK).body("Account Activation Successful");
    }

    @PostMapping("login")
    @Operation(summary = "Login", description = "Login to Reddit")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        return status(OK).body(authService.login(loginRequest));
    }
}
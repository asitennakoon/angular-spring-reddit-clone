package com.thiranya.angularspringredditclone.controller;

import com.thiranya.angularspringredditclone.dto.RegisterRequest;
import com.thiranya.angularspringredditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful", OK);
    }

    @PostMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyToken(@PathVariable String token) {
        authService.verifyToken(token);
        return new ResponseEntity<>("Account Activation Successful", OK);
    }
}
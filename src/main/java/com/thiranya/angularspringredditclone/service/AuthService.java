package com.thiranya.angularspringredditclone.service;

import com.thiranya.angularspringredditclone.dto.AuthenticationResponse;
import com.thiranya.angularspringredditclone.dto.LoginRequest;
import com.thiranya.angularspringredditclone.dto.RegisterRequest;
import com.thiranya.angularspringredditclone.exception.RedditException;
import com.thiranya.angularspringredditclone.model.NotificationEmail;
import com.thiranya.angularspringredditclone.model.User;
import com.thiranya.angularspringredditclone.model.VerificationToken;
import com.thiranya.angularspringredditclone.repository.UserRepository;
import com.thiranya.angularspringredditclone.repository.VerificationTokenRepository;
import com.thiranya.angularspringredditclone.util.JwtProvider;
import com.thiranya.angularspringredditclone.util.MailContentBuilder;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

import static java.time.Instant.now;

@Service
@AllArgsConstructor
public class AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenRepository verificationTokenRepository;
    private MailContentBuilder mailContentBuilder;
    private MessageSource messageSource;
    private MailService mailService;
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;

    @Transactional
    public void register(RegisterRequest registerRequest) {
        User user = new User();

        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setCreatedDate(now());
        user.setEnabled(false);

        userRepository.save(user);

        sendActivationEmail(user);
    }

    private void sendActivationEmail(User user) {
        String activationLink = messageSource.getMessage("reddit.activation.email.link", null, Locale.getDefault());
        String token = generateToken(user);

        String message = messageSource.getMessage("reddit.activation.email.msg", new String[]{activationLink, token}, Locale.getDefault());
        String emailBody = mailContentBuilder.build(message);
        String emailSubject = messageSource.getMessage("reddit.activation.email.subject", null, Locale.getDefault());

        mailService.sendMail(new NotificationEmail(emailSubject, user.getEmail(), emailBody));
    }

    private String generateToken(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);

        return token;
    }

    @Transactional
    public void verifyToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RedditException("Invalid token"));
        enableUser(verificationToken);
    }

    private void enableUser(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RedditException("User Not Found with username - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authenticationToken = jwtProvider.generateToken(authentication);

        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }
}
package com.mika.mikabackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mika.mikabackend.dto.AuthenticationRequest;
import com.mika.mikabackend.dto.AuthenticationResponse;
import com.mika.mikabackend.dto.RegisterRequest;
import com.mika.mikabackend.model.User;
import com.mika.mikabackend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Autowired
  private EmailService emailService;
  private final Map<String, String> otpCache = new ConcurrentHashMap<>();

  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  public String authenticate(AuthenticationRequest request) {
    Authentication authenticate = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authenticate);
    if (authenticate.isAuthenticated()) {
      String otp = generateOtp(request.getEmail());
      return "OTP sent";
    } else {
      throw new RuntimeException("invalid access");
    }
  }

  public String generateOtp(String email){
    Random random = new Random();
    String otp = String.format("%06d", random.nextInt(1000000));
    otpCache.put(email, otp);
    String emailContent = String.format(
            "Your OTP code is: %s\nThank you for using the service!\n\nBest regards,\nCDM - Car Dealership Management",
            otp
    );
    emailService.sendOTPEmail(email, "Your OTP Code", emailContent);
    System.out.println("Email sent");
    return otp;
  }

  public boolean verifyOtp(String email, String otp){
    String cachedOtp = otpCache.get(email);
    return cachedOtp != null && cachedOtp.equals(otp);
  }

  public AuthenticationResponse returnToken(String email) {
    var user = repository.findByEmail(email)
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}

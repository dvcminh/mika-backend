package com.mika.mikabackend.controller;

import com.mika.mikabackend.dto.AuthenticationRequest;
import com.mika.mikabackend.dto.AuthenticationResponse;
import com.mika.mikabackend.dto.OtpRequest;
import com.mika.mikabackend.dto.RegisterRequest;
import com.mika.mikabackend.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/authenticate")
  public String authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return service.authenticate(request);
  }

  @PostMapping("/test")
  public String test(
          @RequestBody AuthenticationRequest request
  ) {
    return service.authenticate(request);
  }

  @PostMapping("/verifyOtp")
  public AuthenticationResponse verifyOtp(@RequestBody OtpRequest otpRequest) throws BadRequestException {
    boolean isValidOtp = service.verifyOtp(otpRequest.getEmail(), otpRequest.getOtp());
    if(isValidOtp){
      return service.returnToken(otpRequest.getEmail());
    } else {
      throw new BadRequestException("Invalid OTP");
    }
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }


}

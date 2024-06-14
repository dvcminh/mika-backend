package com.mika.mikabackend.controller;

import com.mika.mikabackend.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
@Tag(name = "Email")
public class EmailController {
    private final EmailService emailService;

    @Operation(
            description = "Send email to all user",
            summary = "This is a summary for sending email to user",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<String> sendEmail() {
        emailService.sendEmailToAllUsers("Test", "Test");
        return ResponseEntity.status(HttpStatus.OK).body("Email sent to all users!");
    }
}

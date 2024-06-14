package com.mika.mikabackend.dto;

import com.mika.mikabackend.model.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UpdateProfileRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private Role role;
}

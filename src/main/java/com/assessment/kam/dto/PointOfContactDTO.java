package com.assessment.kam.dto;

import com.assessment.kam.enums.POCRole;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointOfContactDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Role cannot be null")
    private POCRole role;

    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number should be 10 digits")
    private String phone;

    public PointOfContactDTO(String name, POCRole role, String email, String phone){
        this.name = name;
        this.role = role;
        this.email = email;
        this.phone = phone;
    }

}


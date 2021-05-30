package com.dms.adproject.dto.request;

import com.dms.adproject.model.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Contact number cannot be blank")
    private String contactNumber;

    @NotBlank(message = "Username number cannot be blank")
    private String username;

    @NotBlank(message = "Email number cannot be blank")
    private String email;

    @NotBlank(message = "password number cannot be blank")
    private String password;

    public User getUser() {
        return new User(
                this.name,
                this.contactNumber,
                this.username,
                this.email,
                null,
                null
        );
    }
}

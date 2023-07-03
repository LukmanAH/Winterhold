package com.basilisk.Basilisk.dto.account;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ComparePassword(message = "Confirmasi password tidak sesuai")
public class RegisterDTO {

    @NotBlank(message = "Username required")
    @Size(max = 20, message = "Max 20 char")
//    @UniqueUsername(message = "Username sudah terpakai, cari yg lain")
    private String userName;

    @NotBlank(message = "Password required")
    @Size(max = 20, min = 6, message = "Password 6 - 20 char")
    private String password;

    @NotBlank(message = "Confirm password required")
    private String passwordConfirmation;

//    @NotBlank(message = "Role required")
//    private String role;
}

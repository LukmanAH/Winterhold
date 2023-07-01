package com.winterhold.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UpdateAuthorDTO {
    @NotBlank(message = "Title author required")
    @Size(max = 10, message = "Title max 10 char")
    private String title;

    @NotBlank(message = "First Name required")
    @Size(max = 10, message = "First name max 50 char")
    private String firstName;

    @Size(max = 10, message = "Last name max 50 char")
    private String lastName;

    @NotNull(message = "Birth date required")
    private LocalDate birthDate;

    private LocalDate deceasedDate;

    @Size(max = 50, message = "Education max 50 char")
    private String education;

    @Size(max = 10, message = "Summary max 500 char")
    private String summary;
}

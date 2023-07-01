package com.winterhold.dto.author;

import com.winterhold.utility.MapperHelper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpsertAuthorDTO {
    private Long id;

    @NotBlank(message = "First Name required")
    @Size(max = 10, message = "Title max 10 char")
    private String title;

    @NotBlank(message = "First Name required")
    @Size(max = 50, message = "First name max 50 char")
    private String firstName;

    @Size(max = 50, message = "Last name max 50 char")
    private String lastName;

    @NotNull(message = "Birth date required")
    private LocalDate birthDate;

    private LocalDate deceasedDate;

    @Size(max = 50, message = "Education max 50 char")
    private String education;

    @Size(max = 500, message = "Summary max 500 char")
    private String summary;

    public UpsertAuthorDTO(Object entity) {
        this.id = MapperHelper.getLongField(entity, "id");
        this.title = MapperHelper.getStringField(entity, "title");;
        this.firstName = MapperHelper.getStringField(entity, "firstName");
        this.lastName = MapperHelper.getStringField(entity, "lastName");
        this.birthDate = MapperHelper.getLocalDateField(entity, "birthDate");
        this.deceasedDate = MapperHelper.getLocalDateField(entity, "deceasedDate");
        this.education = MapperHelper.getStringField(entity, "education");
        this.summary = MapperHelper.getStringField(entity, "summary");
    }
}

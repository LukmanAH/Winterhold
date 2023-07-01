package com.winterhold.dto.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorRowDTO {
    private Long id;
    private String fullName;
    private Integer age;
    private String status;
    private String education;

    AuthorRowDTO(Long id,String title, String firstName, String lastName, LocalDate birthDate, LocalDate deceasedDate, String education){
        this.id = id;
        this.fullName = String.format("%s %s %s", title, firstName, (lastName==null? "" : lastName) );
        this.age = deceasedDate == null? birthDate.until(LocalDate.now()).getYears() : birthDate.until(deceasedDate).getYears();
        this.status = (deceasedDate == null ? "Alive" : "Deceased");
        this.education = education;
    }
}

package com.winterhold.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookByCategoryDTO {
    private String code;
    private String title;
    private String authorName;
    private String isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;

    public BookByCategoryDTO(String code, String title, String authorTitle, String firstName, String lastName, Boolean isBorrowed, LocalDate releaseDate, Integer totalPage) {
        var authorName = String.format("%s %s %s", authorTitle, firstName, (lastName != null? lastName : " "));

        this.code = code;
        this.title = title;
        this.authorName = authorName;
        this.isBorrowed = isBorrowed ? "Borrowed" : "Available";
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
    }
}

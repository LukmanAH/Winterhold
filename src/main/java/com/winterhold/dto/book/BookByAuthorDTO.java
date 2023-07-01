package com.winterhold.dto.book;

import com.winterhold.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookByAuthorDTO {
    private String code;
    private String title;
    private String categoryName;
    private String isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;

    public BookByAuthorDTO(String code, String title, String categoryName, Boolean isBorrowed, LocalDate releaseDate, Integer totalPage) {
        this.code = code;
        this.title = title;
        this.categoryName = categoryName;
        this.isBorrowed = isBorrowed ? "Borrowed" : "Available";
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
    }
}

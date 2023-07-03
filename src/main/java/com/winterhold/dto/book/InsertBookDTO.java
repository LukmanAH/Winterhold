package com.winterhold.dto.book;

import com.winterhold.entity.Author;
import com.winterhold.entity.Category;
import com.winterhold.validation.UniqueBookCode;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class InsertBookDTO {

    @NotBlank(message = "Code required")
    @Size(max = 20, message = "Code max 20 char")
    @UniqueBookCode(message = "Book code has been used")
    private String code;

    @NotBlank(message = "Title required")
    @Size(max = 100, message = "Title max 100 char")
    private String title;

    @NotBlank(message = "Category Name required")
    @Size(max = 100, message = "Category name max 100 char")
    private String categoryName;

    @NotNull(message = "Author id required")
    private Long authorId;

    @Size(max = 500, message = "Summary max 500 char")
    private String summary;

    private LocalDate releaseDate;

    private Integer totalPage;
}

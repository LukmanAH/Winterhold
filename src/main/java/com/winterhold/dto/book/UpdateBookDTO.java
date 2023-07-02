package com.winterhold.dto.book;

import com.winterhold.utility.MapperHelper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.mapper.Mapper;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateBookDTO {
    @NotBlank(message = "Code required")
    @Size(max = 20, message = "Code max 20 char")
    private String code;

    @NotBlank(message = "Title required")
    @Size(max = 100, message = "Title max 100 char")
    private String title;

    @NotBlank(message = "Category Name required")
    @Size(max = 100, message = "Category name max 100 char")
    private String categoryName;

    @NotNull(message = "Author id required")
    private Long authorId;

    private Boolean isBorrowed;

    @Size(max = 500, message = "Summary max 500 char")
    private String summary;

    private LocalDate releaseDate;

    private Integer totalPage;

    public UpdateBookDTO(Object entity) {
        this.code = MapperHelper.getStringField(entity,"code");
        this.title = MapperHelper.getStringField(entity,"title");
        this.categoryName = MapperHelper.getStringField(entity,"categoryName");
        this.authorId = MapperHelper.getLongField(entity, "authorId");
//        this.isBorrowed = MapperHelper.getBooleanField(entity, "isBorrowed");
        this.summary = MapperHelper.getStringField(entity,"summary");
        this.releaseDate = MapperHelper.getLocalDateField(entity, "releaseDate");
        this.totalPage = MapperHelper.getIntegerField(entity, "totalPage");
    }
}

package com.winterhold.dto.category;

import com.winterhold.validation.UniqueCategoryName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InsertCategoryDTO {

    @NotBlank(message = "Name required")
    @Size(max = 100, message = "Name max 100 char")
    @UniqueCategoryName(message = "category has been used")
    private String name;

    @NotNull(message = "Floor required")
    private Integer floor;

    @NotBlank(message = "Isle required")
    @Size(max = 10, message = "Isle max 10 char")
    private String isle;

    @NotBlank(message = "Bay required")
    @Size(max = 10, message = "Bay max 10 char")
    private String bay;
}

package com.winterhold.dto.category;

import com.winterhold.utility.MapperHelper;
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
public class UpdateCategoryDTO {

    @NotBlank(message = "Name required")
    @Size(max = 100, message = "Name max 100 char")
    private String name;

    @NotNull(message = "Floor required")
    private Integer floor;

    @NotBlank(message = "Isle required")
    @Size(max = 10, message = "Isle max 10 char")
    private String isle;

    @NotBlank(message = "Bay required")
    @Size(max = 10, message = "Bay max 10 char")
    private String bay;

    public UpdateCategoryDTO(Object entity) {
        this.name = MapperHelper.getStringField(entity, "name");
        this.floor = MapperHelper.getIntegerField(entity, "floor");
        this.isle = MapperHelper.getStringField(entity, "isle");
        this.bay = MapperHelper.getStringField(entity, "bay");
    }
}

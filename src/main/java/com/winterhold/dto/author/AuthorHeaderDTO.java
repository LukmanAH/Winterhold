package com.winterhold.dto.author;

import com.winterhold.utility.MapperHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorHeaderDTO {
    private Long id;
    private String fullName;
    private String birthDate;
    private String deceasedDate;
    private String education;
    private String summary;

    public AuthorHeaderDTO(Object entity) {
        var fullName = MapperHelper.getStringField(entity, "fullName");

        if(fullName == null){
            fullName = String.format("%s %s %s",
                    MapperHelper.getStringField(entity, "title"),
                    MapperHelper.getStringField(entity, "firstName"),
                    (MapperHelper.getStringField(entity, "lastName") != null? MapperHelper.getStringField(entity, "lastName") : ""));
        }

        var deceasedDate = MapperHelper.getLocalDateField(entity, "deceasedDate");
        var summary = MapperHelper.getStringField(entity, "summary");
        this.id = MapperHelper.getLongField(entity, "id");
        this.fullName = fullName;
        this.birthDate = MapperHelper.getLocalDateField(entity, "birthDate").toString();
        this.deceasedDate = deceasedDate != null ? deceasedDate.toString() : "-";
        this.education = MapperHelper.getStringField(entity, "education");
        this.summary = summary != null && !summary.equals("") ? summary : "-";
    }
}

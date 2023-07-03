package com.winterhold.dto.customer;

import com.winterhold.utility.MapperHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerContextDTO {
    private String membershipNumber;
    private String fullName;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String address;

    public CustomerContextDTO(Object entity) {
        var firstName = MapperHelper.getStringField(entity, "firstName");
        var lastName = MapperHelper.getStringField(entity, "lastName");

        this.membershipNumber = MapperHelper.getStringField(entity, "membershipNumber");
        this.fullName = String.format("%s %s", firstName,(lastName != null? lastName:""));
        this.birthDate = MapperHelper.getLocalDateField(entity, "birthDate");
        this.gender = MapperHelper.getStringField(entity, "gender");
        this.phone = MapperHelper.getStringField(entity, "phone");
        this.address = MapperHelper.getStringField(entity, "address");
    }
}

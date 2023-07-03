package com.winterhold.dto.customer;

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
public class UpdateCustomerDTO {
    @NotBlank(message = "MembershipNumber required")
    @Size(max = 20, message = "MembershipNumber max 20 char")
    private String membershipNumber;

    @NotBlank(message = "First name required")
    @Size(max = 50, message = "FirstName max 50 char")
    private String firstName;

    @Size(max = 50, message = "Last name max 50 char")
    private String lastName;

    @NotNull(message = "Birth date required")
    private LocalDate birthDate;

    @NotBlank(message = "Gender required")
    @Size(max = 10, message = "Gender max 10 char")
    private String gender;

    @Size(max = 20, message = "Phone max 20 char")
    private String phone;

    @Size(max = 500, message = "Address max 500 char")
    private String address;

    @NotNull(message = "MembershipExpiredDate required")
    private LocalDate membershipExpireDate;

    public UpdateCustomerDTO(Object entity) {
        this.membershipNumber = MapperHelper.getStringField(entity, "membershipNumber");
        this.firstName = MapperHelper.getStringField(entity, "firstName");
        this.lastName = MapperHelper.getStringField(entity, "lastName");
        this.birthDate = MapperHelper.getLocalDateField(entity,"birthDate");
        this.gender = MapperHelper.getStringField(entity, "gender");
        this.phone = MapperHelper.getStringField(entity, "phone");
        this.address = MapperHelper.getStringField(entity, "address");
        this.membershipExpireDate = MapperHelper.getLocalDateField(entity,"membershipExpireDate");
    }
}

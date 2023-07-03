package com.winterhold.dto.loan;

import com.winterhold.entity.Book;
import com.winterhold.entity.Customer;
import com.winterhold.utility.MapperHelper;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertLoanDTO {
    private Long id;

    @NotBlank(message = "Customer number required")
    private String customerNumber;

    @NotBlank(message = "Book code required")
    private String bookCode;

    @NotNull(message = "Loan date required")
    private LocalDate loanDate;

    private LocalDate dueDate;

    private String note;

    public UpsertLoanDTO(Object entity) {
        this.id = MapperHelper.getLongField(entity,"id");
        this.customerNumber = MapperHelper.getStringField(entity, "customerNumber");
        this.bookCode = MapperHelper.getStringField(entity, "bookCode");
        this.loanDate = MapperHelper.getLocalDateField(entity, "loanDate");
        this.dueDate = MapperHelper.getLocalDateField(entity, "dueDate");
        this.note = MapperHelper.getStringField(entity, "note");
    }
}
